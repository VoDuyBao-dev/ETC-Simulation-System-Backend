

UPDATE users SET fullname = 'Nguyen Van A', phone = '0901234567', address = '123 Lê Lợi, TP.HCM', email = 'admin@gmail.com' WHERE user_id = 1;


-- Mật khẩu "admin"
INSERT INTO `users` (`user_id`, `fullname`, `email`, `username`, `password`, `phone`, `address`, `status`, `created_at`) VALUES

(2, 'Tran Thi B', 'tranb@example.com', "name1", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0912345678', '456 Nguyễn Trãi, Hà Nội', '1', NOW()),
(3, 'Le Van C', 'levanc@example.com', "name2", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0932123456', '789 Lý Thường Kiệt, Đà Nẵng', '1', NOW()),
(4, 'Pham Thi D', 'phamd@example.com', "name3", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0987654321', '12 Trần Hưng Đạo, Cần Thơ', '1', NOW()),
(5, 'Bui Van E', 'buie@example.com', "name4", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0909123456', '34 Nguyễn Huệ, Huế', '1', NOW()),
(6, 'Do Thi F', 'dof@example.com', "name5", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0976543210', '56 Phan Chu Trinh, Vũng Tàu', '1', NOW()),
(7, 'Nguyen Van G', 'nguyeng@example.com', "name6", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0911122233', '78 Hai Bà Trưng, Hà Nội', '1', NOW()),
(8, 'Tran Van H', 'tranh@example.com', "name7", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0945678901', '90 Lý Tự Trọng, TP.HCM', '1', NOW()),
(9, 'Vo Thi I', 'voi@example.com', "name8", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0923456789', '101 Bạch Đằng, Đà Nẵng', '1', NOW()),
(10, 'Pham Van J', 'phamj@example.com', "name9", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0906789123', '112 Hùng Vương, Cần Thơ', '1', NOW()),
(11, 'Le Thi K', 'lek@example.com', "name10", '$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO', '0977123456', '23 Điện Biên Phủ, Huế', '1', NOW());

INSERT INTO user_roles (user_id, role)
VALUES
(2, 'CUSTOMER'),
(3, 'CUSTOMER'),
(4, 'CUSTOMER'),
(5, 'CUSTOMER'),
(6, 'ADMIN'),
(7, 'CUSTOMER'),
(8, 'CUSTOMER'),
(9, 'CUSTOMER'),
(10, 'CUSTOMER'),
(11, 'CUSTOMER');

INSERT INTO vehicles (id, brand, model, color, plate_number, vehicle_type, user_id, created_at, updated_at, status)
VALUES
(1, 'Toyota', 'Vios', 'Red', '33A-12345', 'CAR', 11, '2025-10-01 08:30:00', '2025-10-01 08:30:00', 'ACTIVE'),
(2, 'Hyundai', 'Accent', 'White', '43A-67890', 'CAR', 2, '2025-10-02 09:10:00', '2025-10-02 09:10:00', 'ACTIVE'),
(3, 'Ford', 'Ranger', 'Blue', '51C-24680', 'TRUCK', 3, '2025-10-03 10:05:00', '2025-10-03 10:05:00', 'ACTIVE'),
(4, 'Kia', 'Morning', 'Black', '65A-11223', 'CAR', 4, '2025-10-04 11:15:00', '2025-10-04 11:15:00', 'ACTIVE'),
(5, 'Isuzu', 'D-Max', 'Silver', '75C-33445', 'TRUCK', 5, '2025-10-05 12:20:00', '2025-10-05 12:20:00', 'ACTIVE'),
(6, 'Mazda', '3', 'Gray', '29A-55667', 'CAR', 7, '2025-10-06 08:40:00', '2025-10-06 08:40:00', 'ACTIVE'),
(7, 'Honda', 'City', 'White', '60A-77889', 'CAR', 8, '2025-10-07 09:50:00', '2025-10-07 09:50:00', 'ACTIVE'),
(8, 'Suzuki', 'Carry', 'Blue', '36C-99001', 'TRUCK', 9, '2025-10-08 10:30:00', '2025-10-08 10:30:00', 'ACTIVE'),
(9, 'VinFast', 'Lux A2.0', 'Black', '30G-11234', 'CAR', 10, '2025-10-09 11:10:00', '2025-10-09 11:10:00', 'ACTIVE'),
(10, 'Toyota', 'Fortuner', 'Brown', '50A-22334', 'CAR', 6, '2025-10-10 12:10:00', '2025-10-10 12:10:00', 'ACTIVE');



INSERT INTO stations (id, name, code, address, latitude, longitude, status)
VALUES
(1, 'Trạm thu phí Pháp Vân - Cầu Giẽ', 'PV-CG', 'Hà Nội', 20.91234, 105.85567, 'ACTIVE'),
(2, 'Trạm thu phí Long Thành - Dầu Giây', 'LT-DG', 'Đồng Nai', 10.77345, 107.12345, 'ACTIVE'),
(3, 'Trạm thu phí Trung Lương', 'TL', 'Tiền Giang', 10.39765, 106.34567, 'ACTIVE'),
(4, 'Trạm thu phí Cam Lộ - La Sơn', 'CL-LS', 'Thừa Thiên Huế', 16.25012, 107.23001, 'MAINTENANCE'),
(5, 'Trạm thu phí Cầu Rạch Miễu', 'CRM', 'Bến Tre', 10.22567, 106.34555, 'ACTIVE'),
(6, 'Trạm thu phí Cầu Mỹ Thuận', 'CMT', 'Vĩnh Long', 10.21212, 105.65432, 'INACTIVE'),
(7, 'Trạm thu phí Bắc Giang - Lạng Sơn', 'BG-LS', 'Bắc Giang', 21.28567, 106.22555, 'ACTIVE'),
(8, 'Trạm thu phí Cầu Cần Thơ', 'CCT', 'Cần Thơ', 10.04567, 105.77432, 'ACTIVE'),
(9, 'Trạm thu phí Cao tốc Hà Nội - Hải Phòng', 'HN-HP', 'Hải Phòng', 20.87999, 106.67890, 'ACTIVE'),
(10, 'Trạm thu phí Tân Đệ', 'TD', 'Thái Bình', 20.49567, 106.35555, 'INACTIVE');



INSERT INTO rfid_readers (id, reader_uid, description, is_active, created_at, last_heartbeat, station_id)
VALUES
(1, 'RDR001', 'Reader làn 1 PV-CG', 1, '2025-09-25 08:00:00', '2025-10-01 08:00:00', 1),
(2, 'RDR002', 'Reader làn 2 PV-CG', 1, '2025-09-25 08:05:00', '2025-10-01 08:10:00', 1),
(3, 'RDR003', 'Reader làn 1 LT-DG', 1, '2025-09-25 08:10:00', '2025-10-01 08:20:00', 2),
(4, 'RDR004', 'Reader làn 2 LT-DG', 1, '2025-09-25 08:15:00', '2025-10-01 08:25:00', 2),
(5, 'RDR005', 'Reader làn 1 TL', 1, '2025-09-25 08:20:00', '2025-10-01 08:30:00', 3),
(6, 'RDR006', 'Reader làn 1 CL-LS', 0, '2025-09-25 08:25:00', '2025-10-01 08:35:00', 4),
(7, 'RDR007', 'Reader làn 1 CRM', 1, '2025-09-25 08:30:00', '2025-10-01 08:40:00', 5),
(8, 'RDR008', 'Reader làn 1 BG-LS', 1, '2025-09-25 08:35:00', '2025-10-01 08:45:00', 7),
(9, 'RDR009', 'Reader làn 1 CCT', 1, '2025-09-25 08:40:00', '2025-10-01 08:50:00', 8),
(10, 'RDR010', 'Reader làn 1 HN-HP', 1, '2025-09-25 08:45:00', '2025-10-01 08:55:00', 9);



INSERT INTO rfid_tags (id, tag_uid, status, vehicle_id)
VALUES
(1, 'TAG0001', 'ACTIVE', 1),
(2, 'TAG0002', 'ACTIVE', 2),
(3, 'TAG0003', 'ACTIVE', 3),
(4, 'TAG0004', 'ACTIVE', 4),
(5, 'TAG0005', 'BLOCKED', 5),
(6, 'TAG0006', 'ACTIVE', 6),
(7, 'TAG0007', 'INACTIVE', 7),
(8, 'TAG0008', 'ACTIVE', 8),
(9, 'TAG0009', 'ACTIVE', 9),
(10, 'TAG0010', 'ACTIVE', 10);



-- ============================================
-- 30 VEHICLES (id: 11 → 40)
-- ============================================
INSERT INTO `vehicles` 
(`id`, `brand`, `color`, `created_at`, `model`, `plate_number`, `updated_at`, `vehicle_type`, `user_id`, `status`) VALUES
(11, 'Honda',    'White', '2025-09-01 08:00:00', 'Civic',    '30B-20111', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(12, 'Toyota',   'Black', '2025-09-02 09:10:00', 'Camry',    '30B-20112', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(13, 'Ford',     'Blue',  '2025-09-03 10:20:00', 'Ranger',   '30B-20113', '2025-11-05 12:00:00', 'TRUCK',4, 'ACTIVE'),
(14, 'Kia',      'Red',   '2025-09-04 11:30:00', 'Morning',  '30B-20114', '2025-11-05 12:00:00', 'CAR', 4, 'ACTIVE'),
(15, 'Mazda',    'Grey',  '2025-09-05 12:40:00', '3',        '30B-20115', '2025-11-05 12:00:00', 'CAR', 4, 'ACTIVE'),
(16, 'Hyundai',  'White', '2025-09-06 13:50:00', 'Elantra',  '30B-20116', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(17, 'Mitsubishi','Black','2025-09-07 14:00:00', 'Triton',   '30B-20117', '2025-11-05 12:00:00', 'TRUCK',5, 'ACTIVE'),
(18, 'Suzuki',   'Green', '2025-09-08 15:10:00', 'Swift',    '30B-20118', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(19, 'VinFast',  'Blue',  '2025-09-09 16:20:00', 'VF e34',   '30B-20119', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(20, 'Toyota',   'Silver','2025-09-10 17:30:00', 'Innova',   '30B-20120', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(21, 'Honda',    'Black', '2025-09-11 08:05:00', 'CR-V',     '30B-20121', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(22, 'Ford',     'White', '2025-09-12 09:15:00', 'Transit',  '30B-20122', '2025-11-05 12:00:00', 'TRUCK',4, 'ACTIVE'),
(23, 'Kia',      'Yellow','2025-09-13 10:25:00', 'Sorento',  '30B-20123', '2025-11-05 12:00:00', 'CAR', 4, 'ACTIVE'),
(24, 'Mazda',    'Red',   '2025-09-14 11:35:00', 'CX-5',     '30B-20124', '2025-11-05 12:00:00', 'CAR', 4, 'ACTIVE'),
(25, 'Hyundai',  'Blue',  '2025-09-15 12:45:00', 'SantaFe',  '30B-20125', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(26, 'Mitsubishi','Grey', '2025-09-16 13:55:00', 'Xpander',  '30B-20126', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(27, 'Suzuki',   'White', '2025-09-17 14:05:00', 'Ertiga',   '30B-20127', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(28, 'VinFast',  'Black', '2025-09-18 15:15:00', 'VF 8',     '30B-20128', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(29, 'Toyota',   'Blue',  '2025-09-19 16:25:00', 'Fortuner', '30B-20129', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(30, 'Honda',    'Green', '2025-09-20 17:35:00', 'Accord',   '30B-20130', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(31, 'Ford',     'Red',   '2025-09-21 08:45:00', 'F-150',    '30B-20131', '2025-11-05 12:00:00', 'TRUCK',4, 'ACTIVE'),
(32, 'Kia',      'White', '2025-09-22 09:55:00', 'K5',       '30B-20132', '2025-11-05 12:00:00', 'CAR', 4, 'ACTIVE'),
(33, 'Mazda',    'Black', '2025-09-23 11:05:00', 'CX-3',     '30B-20133', '2025-11-05 12:00:00', 'CAR', 4, 'ACTIVE'),
(34, 'Hyundai',  'Silver','2025-09-24 12:15:00', 'Palisade', '30B-20134', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(35, 'Mitsubishi','Blue', '2025-09-25 13:25:00', 'Pajero',   '30B-20135', '2025-11-05 12:00:00', 'TRUCK',5, 'ACTIVE'),
(36, 'Suzuki',   'Red',   '2025-09-26 14:35:00', 'Ciaz',     '30B-20136', '2025-11-05 12:00:00', 'CAR', 5, 'ACTIVE'),
(37, 'VinFast',  'Green', '2025-09-27 15:45:00', 'VF 3',     '30B-20137', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(38, 'Toyota',   'Brown', '2025-09-28 16:55:00', 'Yaris',    '30B-20138', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(39, 'Honda',    'Orange','2025-09-29 17:05:00', 'BR-V',     '30B-20139', '2025-11-05 12:00:00', 'CAR', 3, 'ACTIVE'),
(40, 'Ford',     'Purple','2025-09-30 08:15:00', 'Courier',  '30B-20140', '2025-11-05 12:00:00', 'TRUCK',4, 'ACTIVE');

-- ============================================
-- 30 RFID TAGS (id: 11 → 40)
-- Each tag linked to the corresponding vehicle_id
-- ============================================
INSERT INTO `rfid_tags` (`id`, `issued_at`, `status`, `tag_uid`, `vehicle_id`) VALUES
(11, '2025-11-01 09:00:00', 'ACTIVE', 'TAG0011', 11),
(12, '2025-11-01 09:05:00', 'ACTIVE', 'TAG0012', 12),
(13, '2025-11-01 09:10:00', 'ACTIVE', 'TAG0013', 13),
(14, '2025-11-01 09:15:00', 'ACTIVE', 'TAG0014', 14),
(15, '2025-11-01 09:20:00', 'ACTIVE', 'TAG0015', 15),
(16, '2025-11-01 09:25:00', 'ACTIVE', 'TAG0016', 16),
(17, '2025-11-01 09:30:00', 'ACTIVE', 'TAG0017', 17),
(18, '2025-11-01 09:35:00', 'ACTIVE', 'TAG0018', 18),
(19, '2025-11-01 09:40:00', 'ACTIVE', 'TAG0019', 19),
(20, '2025-11-01 09:45:00', 'ACTIVE', 'TAG0020', 20),
(21, '2025-11-01 09:50:00', 'ACTIVE', 'TAG0021', 21),
(22, '2025-11-01 09:55:00', 'ACTIVE', 'TAG0022', 22),
(23, '2025-11-01 10:00:00', 'ACTIVE', 'TAG0023', 23),
(24, '2025-11-01 10:05:00', 'ACTIVE', 'TAG0024', 24),
(25, '2025-11-01 10:10:00', 'ACTIVE', 'TAG0025', 25),
(26, '2025-11-01 10:15:00', 'ACTIVE', 'TAG0026', 26),
(27, '2025-11-01 10:20:00', 'ACTIVE', 'TAG0027', 27),
(28, '2025-11-01 10:25:00', 'ACTIVE', 'TAG0028', 28),
(29, '2025-11-01 10:30:00', 'ACTIVE', 'TAG0029', 29),
(30, '2025-11-01 10:35:00', 'ACTIVE', 'TAG0030', 30),
(31, '2025-11-01 10:40:00', 'ACTIVE', 'TAG0031', 31),
(32, '2025-11-01 10:45:00', 'ACTIVE', 'TAG0032', 32),
(33, '2025-11-01 10:50:00', 'ACTIVE', 'TAG0033', 33),
(34, '2025-11-01 10:55:00', 'ACTIVE', 'TAG0034', 34),
(35, '2025-11-01 11:00:00', 'ACTIVE', 'TAG0035', 35),
(36, '2025-11-01 11:05:00', 'ACTIVE', 'TAG0036', 36),
(37, '2025-11-01 11:10:00', 'ACTIVE', 'TAG0037', 37),
(38, '2025-11-01 11:15:00', 'ACTIVE', 'TAG0038', 38),
(39, '2025-11-01 11:20:00', 'ACTIVE', 'TAG0039', 39),
(40, '2025-11-01 11:25:00', 'ACTIVE', 'TAG0040', 40);



INSERT INTO tag_reads (id, tag_uid, process_result, processed, reader_id)
VALUES
(1, 'TAG0001', 'SUCCESS', 1, 1),
(2, 'TAG0002', 'SUCCESS', 1, 2),
(3, 'TAG0003', 'SUCCESS', 1, 3),
(4, 'TAG0004', 'FAILED - INVALID TAG', 1, 4),
(5, 'TAG0005', 'FAILED - BLOCKED', 1, 5),
(6, 'TAG0006', 'SUCCESS', 1, 7),
(7, 'TAG0007', 'FAILED - INACTIVE', 1, 8),
(8, 'TAG0008', 'SUCCESS', 1, 9),
(9, 'TAG0009', 'SUCCESS', 1, 10),
(10, 'TAG0010', 'SUCCESS', 1, 3);




INSERT INTO toll_transactions (id, fee, status, note, reader_id, rfid_tag_id, station_id, vehicle_id)
VALUES
(1, 35000, 'SUCCESS', 'Xe qua trạm PV-CG', 1, 1, 1, 1),
(2, 45000, 'SUCCESS', 'Xe qua trạm LT-DG', 3, 2, 2, 2),
(3, 60000, 'SUCCESS', 'Xe tải qua trạm TL', 5, 3, 3, 3),
(4, 35000, 'ERROR', 'Thẻ không hợp lệ', 4, 4, 2, 4),
(5, 60000, 'FAILED_BALANCE', 'Tài khoản không đủ tiền', 5, 5, 3, 5),
(6, 35000, 'SUCCESS', 'Xe qua trạm CRM', 7, 6, 5, 6),
(7, 35000, 'SUCCESS', 'Xe qua trạm BG-LS', 8, 7, 7, 7),
(8, 40000, 'SUCCESS', 'Xe qua trạm CCT', 9, 8, 8, 8),
(9, 50000, 'SUCCESS', 'Xe qua trạm HN-HP', 10, 9, 9, 9),
(10, 45000, 'SUCCESS', 'Xe qua trạm LT-DG lần 2', 3, 10, 2, 10);



INSERT INTO wallets (id, balance, is_blocked, user_id, created_at, updated_at)
VALUES
(1, 150000, 0, 11, '2025-09-29 08:00:00', '2025-10-01 08:06:00'),
(2, 80000, 0, 2, '2025-09-29 08:10:00', '2025-10-01 08:08:00'),
(3, 200000, 0, 3, '2025-09-29 08:20:00', '2025-10-01 08:09:30'),
(4, 0, 0, 4, '2025-09-29 08:30:00', '2025-10-01 08:10:30'),
(5, 300000, 0, 5, '2025-09-29 08:40:00', '2025-10-01 08:11:30'),
(6, 1000000, 0, 6, '2025-09-29 08:50:00', '2025-10-01 08:12:30'),
(7, 50000, 0, 7, '2025-09-29 09:00:00', '2025-10-01 08:13:30'),
(8, 120000, 0, 8, '2025-09-29 09:10:00', '2025-10-01 08:14:30'),
(9, 95000, 0, 9, '2025-09-29 09:20:00', '2025-10-01 08:15:30'),
(10, 250000, 0, 10, '2025-09-29 09:30:00', '2025-10-01 08:16:30');




INSERT INTO topups (id, amount, description, method, status, reference_code, user_id, wallet_id, created_at, completed_at)
VALUES
(1, 100000, 'Nạp tiền ví lần đầu', 'BANK', 'COMPLETED', 'REF001', 11, 1, '2025-09-28 09:00:00', '2025-09-28 09:01:00'),
(2, 50000, 'Nạp qua ví điện tử', 'WALLET', 'COMPLETED', 'REF002', 2, 2, '2025-09-28 09:05:00', '2025-09-28 09:06:00'),
(3, 200000, 'Nạp tiền tháng 10', 'BANK', 'COMPLETED', 'REF003', 3, 3, '2025-09-28 09:10:00', '2025-09-28 09:11:00'),
(4, 100000, 'Nạp tiền bổ sung', 'WALLET', 'COMPLETED', 'REF004', 4, 4, '2025-09-28 09:15:00', '2025-09-28 09:16:00'),
(5, 300000, 'Nạp tiền ngân hàng', 'BANK', 'COMPLETED', 'REF005', 5, 5, '2025-09-28 09:20:00', '2025-09-28 09:21:00'),
(6, 500000, 'Nạp tiền dự phòng', 'BANK', 'COMPLETED', 'REF006', 6, 6, '2025-09-28 09:25:00', '2025-09-28 09:26:00'),
(7, 100000, 'Nạp thất bại thử nghiệm', 'WALLET', 'FAILED', 'REF007', 7, 7, '2025-09-28 09:30:00', NULL),
(8, 150000, 'Nạp tự động hàng tháng', 'BANK', 'COMPLETED', 'REF008', 8, 8, '2025-09-28 09:35:00', '2025-09-28 09:36:00'),
(9, 50000, 'Nạp từ Momo', 'WALLET', 'COMPLETED', 'REF009', 9, 9, '2025-09-28 09:40:00', '2025-09-28 09:41:00'),
(10, 250000, 'Nạp tiền khuyến mãi', 'BANK', 'COMPLETED', 'REF010', 10, 10, '2025-09-28 09:45:00', '2025-09-28 09:46:00');




INSERT INTO wallet_transactions (id, amount, transaction_type, description, wallet_id, related_toll_transaction_id, created_at)
VALUES
(1, 100000, 'TOPUP', 'Nạp tiền ví lần đầu', 1, NULL, '2025-09-28 09:01:00'),
(2, 50000, 'TOPUP', 'Nạp qua ví điện tử', 2, NULL, '2025-09-28 09:06:00'),
(3, 200000, 'TOPUP', 'Nạp tiền tháng 10', 3, NULL, '2025-09-28 09:11:00'),
(4, 100000, 'TOPUP', 'Nạp tiền bổ sung', 4, NULL, '2025-09-28 09:16:00'),
(5, 300000, 'TOPUP', 'Nạp tiền ngân hàng', 5, NULL, '2025-09-28 09:21:00'),
(6, 35000, 'DEDUCT', 'Xe qua trạm PV-CG', 1, 1, '2025-10-01 08:06:00'),
(7, 45000, 'DEDUCT', 'Xe qua trạm LT-DG', 2, 2, '2025-10-01 08:08:00'),
(8, 60000, 'DEDUCT', 'Xe tải qua trạm TL', 3, 3, '2025-10-01 08:09:30'),
(9, 35000, 'DEDUCT', 'Xe qua trạm CRM', 6, 6, '2025-10-01 08:12:30'),
(10, 50000, 'DEDUCT', 'Xe qua trạm HN-HP', 9, 9, '2025-10-01 08:15:30');
