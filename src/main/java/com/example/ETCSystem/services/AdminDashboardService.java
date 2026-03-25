package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.response.*;
import com.example.ETCSystem.enums.TollStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminDashboardMapper;
import com.example.ETCSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

	private final UserRepository userRepository;
	private final AdminVehicleRepository vehicleRepository;
	private final StationRepository stationRepository;
	private final TollTransactionRepository tollTransactionRepository;
	private final AdminDashboardMapper mapper;

	public AdminDashboardResponse getDashboard(
			Integer year, Integer monthFrom, Integer monthTo,
			Integer page, Integer size, String stationCode) {

		ZoneId zone = ZoneId.systemDefault();
		int currentYear = Year.now(zone).getValue();

		// Xác định khoảng thời gian thống kê
		int y = (year != null) ? year : currentYear - 1;
		int mf = (monthFrom != null) ? monthFrom : 1;
		int mt = (monthTo != null) ? monthTo : 12;

		// Kiểm tra năm (Ví dụ: không cho phép xem năm tương lai hoặc quá khứ quá xa)
		if (year != null && (year < 2000 || year > currentYear)) {
			throw new AppException(ErrorCode.INVALID_YEAR);
		}

		// Kiểm tra giá trị tháng hợp lệ (1-12)
		if (mf < 1 || mf > 12 || mt < 1 || mt > 12) {
			throw new AppException(ErrorCode.INVALID_MONTH_VALUE);
		}

		// KIỂM TRA LỖI LỌC NGƯỢC: monthFrom > monthTo
		if (mf > mt) {
			// Ném ra lỗi 400 hoặc 422 tùy bạn định nghĩa trong ErrorCode
			throw new AppException(ErrorCode.INVALID_MONTH_RANGE);
		}

		// LocalDateTime fromLdt = LocalDateTime.of(y, mf, 1, 0, 0);
		// LocalDateTime toLdt = LocalDateTime.of(y, mt, YearMonth.of(y,
		// mt).lengthOfMonth(), 23, 59, 59);
		LocalDateTime from = LocalDateTime.of(y, mf, 10, 0, 0);
		LocalDateTime to = LocalDateTime.of(y, mt, YearMonth.of(y, mt).lengthOfMonth(), 23, 59, 59);

		// 2. Lấy số lượng tổng hợp
		long totalUsers = userRepository.count();
		long totalVehicles = vehicleRepository.count();
		long totalStations = stationRepository.count();
		long totalTx = tollTransactionRepository.count();
		long failedTx = tollTransactionRepository.countByStatus(TollStatus.ERROR)
				+ tollTransactionRepository.countByStatus(TollStatus.FAILED_BALANCE);

		// 3. Tạo summary DTO
		var summary = AdminDashboardSummaryResponse.builder()
				.totalUsers(totalUsers)
				.totalVehicles(totalVehicles)
				.totalStations(totalStations)
				.totalTransactions(totalTx)
				.failedTransactions(failedTx)
				.build();

		// 4. Biểu đồ doanh thu theo tháng (dữ liệu line chart)
		List<RevenuePoint> revenuePoints = tollTransactionRepository
				.sumRevenueByMonth(from, to)
				.stream()
				.map(r -> mapper.toRevenuePoint(((Number) r[0]).intValue(), ((Number) r[1]).intValue(),
						((Number) r[2]).longValue()))
				.toList();

		// 5. Biểu đồ top 5 trạm doanh thu cao nhất
		List<TopStationRevenueResponse> top5 = tollTransactionRepository
				.topStationsRevenue(from, to, PageRequest.of(0, 5))
				.stream()
				.map(mapper::toTopStationRevenue)
				.toList();

		// 6. Bảng giao dịch thất bại (không phân trang)
		List<Object[]> failedList = tollTransactionRepository.findFailedRows(stationCode, from, to);

		List<FailedTransactionRow> failedRows = failedList.stream()
				.map(mapper::toFailedRow)
				.toList();

		// 7. Kết quả tổng hợp gửi về controller
		return AdminDashboardResponse.builder()
				.summary(summary)
				.revenueByMonth(revenuePoints)
				.top5Stations(top5)
				.failedTransactions(failedRows)
				.totalFailed((long) failedRows.size())
				.build();

	}
}
