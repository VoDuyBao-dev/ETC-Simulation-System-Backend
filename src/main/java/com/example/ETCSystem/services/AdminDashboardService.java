package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.response.*;
import com.example.ETCSystem.enums.TollStatus;
import com.example.ETCSystem.enums.StationStatus;
import com.example.ETCSystem.mapper.AdminDashboardMapper;
import com.example.ETCSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

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
		// 1. Xác định khoảng thời gian thống kê
		ZoneId zone = ZoneId.systemDefault();
		int y = (year != null) ? year : Year.now(zone).getValue();
		int mf = (monthFrom != null) ? monthFrom : 1;
		int mt = (monthTo != null) ? monthTo : 12;

		// LocalDateTime fromLdt = LocalDateTime.of(y, mf, 1, 0, 0);
		// LocalDateTime toLdt = LocalDateTime.of(y, mt, YearMonth.of(y,
		// mt).lengthOfMonth(), 23, 59, 59);
		LocalDateTime from = LocalDateTime.of(y, mf, 1, 0, 0);
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
