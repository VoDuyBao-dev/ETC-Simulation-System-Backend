CREATE DATABASE  IF NOT EXISTS `etc_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `etc_system`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: etc_system
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `invalidated_token`
--

DROP TABLE IF EXISTS `invalidated_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invalidated_token` (
  `id` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invalidated_token`
--

LOCK TABLES `invalidated_token` WRITE;
/*!40000 ALTER TABLE `invalidated_token` DISABLE KEYS */;
INSERT INTO `invalidated_token` VALUES ('36a997b6-61a5-490d-9977-fd998c42f10e','2025-11-23 10:30:24.000000'),('8184eaa7-3f6b-4a08-bf6a-52e8090abfac','2025-11-26 00:08:54.000000'),('cfb52f99-f71d-4fa4-b980-bdadb2072593','2025-11-26 18:45:06.000000'),('e7710337-f006-45f2-83f3-5a55ecf96452','2025-11-26 00:11:21.000000');
/*!40000 ALTER TABLE `invalidated_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_verifications`
--

DROP TABLE IF EXISTS `otp_verifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_verifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `otp_code` varchar(10) NOT NULL,
  `used` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_verifications`
--

LOCK TABLES `otp_verifications` WRITE;
/*!40000 ALTER TABLE `otp_verifications` DISABLE KEYS */;
INSERT INTO `otp_verifications` VALUES (1,'2025-11-22 23:50:36.262545','voduybao19052005@gmail.com','2025-11-22 23:51:36.260031','591166',_binary '');
/*!40000 ALTER TABLE `otp_verifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rfid_readers`
--

DROP TABLE IF EXISTS `rfid_readers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rfid_readers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `last_heartbeat` datetime(6) DEFAULT NULL,
  `reader_uid` varchar(255) DEFAULT NULL,
  `station_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfc625omr84c7ptpmwnl4orxwn` (`reader_uid`),
  KEY `FKpb8rsbah5s5t9gbga0nkojtnj` (`station_id`),
  CONSTRAINT `FKpb8rsbah5s5t9gbga0nkojtnj` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rfid_readers`
--

LOCK TABLES `rfid_readers` WRITE;
/*!40000 ALTER TABLE `rfid_readers` DISABLE KEYS */;
INSERT INTO `rfid_readers` VALUES (1,'2024-12-29 02:00:00','Reader làn 1 PV-CG',_binary '','2024-12-29 11:00:00.000000','RDR001',1),(2,'2024-12-29 02:05:00','Reader làn 2 PV-CG',_binary '','2024-12-29 11:05:00.000000','RDR002',1),(3,'2024-12-30 02:00:00','Reader làn 1 LT-DG',_binary '','2024-12-30 11:00:00.000000','RDR003',2),(4,'2024-12-30 02:05:00','Reader làn 2 LT-DG',_binary '','2024-12-30 11:05:00.000000','RDR004',2),(5,'2024-12-31 02:00:00','Reader làn 1 TL',_binary '','2024-12-31 11:00:00.000000','RDR005',3),(6,'2024-12-31 02:05:00','Reader làn 2 TL',_binary '','2024-12-31 11:05:00.000000','RDR006',3),(7,'2025-01-01 02:00:00','Reader làn 1 CL-LS',_binary '','2025-01-01 11:00:00.000000','RDR007',4),(8,'2025-01-01 02:05:00','Reader làn 2 CL-LS',_binary '','2025-01-01 11:05:00.000000','RDR008',4),(9,'2025-01-02 02:00:00','Reader làn 1 CRM',_binary '','2025-01-02 11:00:00.000000','RDR009',5),(10,'2025-01-02 02:05:00','Reader làn 2 CRM',_binary '','2025-01-02 11:05:00.000000','RDR010',5),(11,'2025-01-03 02:00:00','Reader làn 1 CMT',_binary '','2025-01-03 11:00:00.000000','RDR011',6),(12,'2025-01-03 02:05:00','Reader làn 2 CMT',_binary '','2025-01-03 11:05:00.000000','RDR012',6),(13,'2025-01-04 02:00:00','Reader làn 1 BG-LS',_binary '','2025-01-04 11:00:00.000000','RDR013',7),(14,'2025-01-04 02:05:00','Reader làn 2 BG-LS',_binary '','2025-01-04 11:05:00.000000','RDR014',7),(15,'2025-01-05 02:00:00','Reader làn 1 CCT',_binary '','2025-01-05 11:00:00.000000','RDR015',8),(16,'2025-01-05 02:05:00','Reader làn 2 CCT',_binary '','2025-01-05 11:05:00.000000','RDR016',8),(17,'2025-01-06 02:00:00','Reader làn 1 HN-HP',_binary '','2025-01-06 11:00:00.000000','RDR017',9),(18,'2025-01-06 02:05:00','Reader làn 2 HN-HP',_binary '','2025-01-06 11:05:00.000000','RDR018',9),(19,'2025-01-07 02:00:00','Reader làn 1 Tân Đệ',_binary '','2025-01-07 11:00:00.000000','RDR019',10),(20,'2025-01-07 02:05:00','Reader làn 2 Tân Đệ',_binary '','2025-01-07 11:05:00.000000','RDR020',10);
/*!40000 ALTER TABLE `rfid_readers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rfid_tags`
--

DROP TABLE IF EXISTS `rfid_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rfid_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `issued_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_passage_station_id` bigint DEFAULT NULL,
  `last_successful_passage` datetime(6) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `tag_uid` varchar(255) DEFAULT NULL,
  `vehicle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbfhr5yh2sojl8pia2yl4mt6f1` (`tag_uid`),
  KEY `FKnwwplr27b40eo7pam6o14l4vn` (`vehicle_id`),
  CONSTRAINT `FKnwwplr27b40eo7pam6o14l4vn` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rfid_tags`
--

LOCK TABLES `rfid_tags` WRITE;
/*!40000 ALTER TABLE `rfid_tags` DISABLE KEYS */;
INSERT INTO `rfid_tags` VALUES (1,'2025-01-01 01:33:00',1,'2025-01-03 14:20:00.000000','ACTIVE','2891721111',1),(2,'2025-01-02 02:14:00',3,'2025-01-04 10:15:00.000000','ACTIVE','6068111111',2),(3,'2025-01-03 03:08:00',2,'2025-01-06 09:40:00.000000','ACTIVE','2891726103',3),(4,'2025-01-04 04:18:00',5,'2025-01-06 11:10:00.000000','ACTIVE','2891726104',4),(5,'2025-01-05 05:23:00',4,'2025-01-08 16:55:00.000000','ACTIVE','2891726105',5),(6,'2025-01-06 01:44:00',7,'2025-01-09 13:27:00.000000','ACTIVE','2891726106',6),(7,'2025-01-07 02:54:00',8,'2025-01-10 15:41:00.000000','ACTIVE','2891726107',7),(8,'2025-01-08 03:34:00',9,'2025-01-11 11:31:00.000000','ACTIVE','2891726108',8),(9,'2025-01-09 04:14:00',6,'2025-01-12 18:20:00.000000','ACTIVE','2891726109',9),(10,'2025-01-10 05:14:00',10,'2025-01-13 14:05:00.000000','ACTIVE','2891726110',10),(11,'2025-01-01 01:03:00',3,'2025-01-05 10:20:00.000000','ACTIVE','2891726201',11),(12,'2025-01-02 02:13:00',1,'2025-01-06 11:30:00.000000','ACTIVE','2891726202',12),(13,'2025-01-03 03:23:00',4,'2025-01-10 12:45:00.000000','ACTIVE','2891726203',13),(14,'2025-01-04 04:33:00',2,'2025-01-09 09:15:00.000000','ACTIVE','2891726204',14),(15,'2025-01-05 05:43:00',7,'2025-01-12 14:05:00.000000','ACTIVE','2891726205',15),(16,'2025-01-06 06:53:00',5,'2025-01-15 08:20:00.000000','ACTIVE','2891726206',16),(17,'2025-01-07 07:03:00',9,'2025-01-13 16:00:00.000000','ACTIVE','2891726207',17),(18,'2025-01-08 08:13:00',10,'2025-01-18 17:45:00.000000','ACTIVE','2891726208',18),(19,'2025-01-09 09:23:00',6,'2025-01-20 09:10:00.000000','ACTIVE','2891726209',19),(20,'2025-01-10 10:33:00',8,'2025-01-19 11:55:00.000000','ACTIVE','2891726210',20),(21,'2025-01-11 01:08:00',3,'2025-01-14 10:30:00.000000','ACTIVE','2891726211',21),(22,'2025-01-12 02:18:00',4,'2025-01-16 12:40:00.000000','ACTIVE','2891726212',22),(23,'2025-01-13 03:28:00',2,'2025-01-17 15:00:00.000000','ACTIVE','2891726213',23),(24,'2025-01-14 04:38:00',10,'2025-01-20 17:20:00.000000','ACTIVE','2891726214',24),(25,'2025-01-15 05:48:00',5,'2025-01-23 08:50:00.000000','ACTIVE','2891726215',25),(26,'2025-01-16 06:58:00',9,'2025-01-24 14:35:00.000000','ACTIVE','2891726216',26),(27,'2025-01-17 07:08:00',1,'2025-01-22 11:22:00.000000','ACTIVE','2891726217',27),(28,'2025-01-18 08:18:00',7,'2025-01-26 16:40:00.000000','ACTIVE','2891726218',28),(29,'2025-01-19 09:28:00',6,'2025-01-29 13:05:00.000000','ACTIVE','2891726219',29),(30,'2025-01-20 10:38:00',8,'2025-01-30 10:18:00.000000','ACTIVE','2891726220',30),(31,'2025-01-21 01:48:00',3,'2025-01-28 11:30:00.000000','ACTIVE','2891726221',31),(32,'2025-01-22 02:58:00',4,'2025-01-27 15:45:00.000000','ACTIVE','2891726222',32),(33,'2025-01-23 04:08:00',2,'2025-01-29 16:55:00.000000','ACTIVE','2891726223',33),(34,'2025-01-24 05:18:00',10,'2025-01-30 18:20:00.000000','ACTIVE','2891726224',34),(35,'2025-01-25 06:28:00',9,'2025-01-02 08:15:00.000000','ACTIVE','2891726225',35),(36,'2025-01-26 07:38:00',6,'2025-01-03 10:10:00.000000','ACTIVE','2891726226',36),(37,'2025-01-27 08:48:00',7,'2025-01-05 12:55:00.000000','ACTIVE','2891726227',37),(38,'2025-01-28 09:58:00',1,'2025-01-06 14:45:00.000000','ACTIVE','2891726228',38),(39,'2025-01-29 10:08:00',5,'2025-01-08 15:21:00.000000','ACTIVE','2891726229',39),(40,'2025-01-30 01:18:00',8,'2025-01-09 16:35:00.000000','ACTIVE','2891726230',40),(41,'2025-11-22 16:55:08',NULL,NULL,'ACTIVE','2891726086',41),(42,'2025-11-22 16:56:29',NULL,NULL,'ACTIVE','60689670',42),(43,'2025-11-22 17:35:48',NULL,NULL,'ACTIVE','8345102056',43);
/*!40000 ALTER TABLE `rfid_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` enum('ACTIVE','INACTIVE','MAINTENANCE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKa5jb873kqd0gpggavx5n5l2gd` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'Hà Nội','PV-CG','2024-12-28 02:00:00',20.91234,105.85567,'Trạm thu phí Pháp Vân - Cầu Giẽ','ACTIVE'),(2,'Đồng Nai','LT-DG','2024-12-29 02:00:00',10.77345,107.12345,'Trạm thu phí Long Thành - Dầu Giây','ACTIVE'),(3,'Tiền Giang','TL','2024-12-30 02:00:00',10.39765,106.34567,'Trạm thu phí Trung Lương','ACTIVE'),(4,'Thừa Thiên Huế','CL-LS','2024-12-31 02:00:00',16.25012,107.23001,'Trạm thu phí Cam Lộ - La Sơn','MAINTENANCE'),(5,'Bến Tre','CRM','2025-01-01 02:00:00',10.22567,106.34555,'Trạm thu phí Cầu Rạch Miễu','ACTIVE'),(6,'Vĩnh Long','CMT','2025-01-02 02:00:00',10.21212,105.65432,'Trạm thu phí Cầu Mỹ Thuận','INACTIVE'),(7,'Bắc Giang','BG-LS','2025-01-03 02:00:00',21.28567,106.22555,'Trạm thu phí Bắc Giang - Lạng Sơn','ACTIVE'),(8,'Cần Thơ','CCT','2025-01-04 02:00:00',10.04567,105.77432,'Trạm thu phí Cầu Cần Thơ','ACTIVE'),(9,'Hải Phòng','HN-HP','2025-01-05 02:00:00',20.87999,106.6789,'Trạm thu phí Cao tốc Hà Nội - Hải Phòng','ACTIVE'),(10,'Thái Bình','TD','2025-01-06 02:00:00',20.49567,106.35555,'Trạm thu phí Tân Đệ','INACTIVE');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_reads`
--

DROP TABLE IF EXISTS `tag_reads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag_reads` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `process_result` enum('FAILED','PENDING','SUCCESS') DEFAULT NULL,
  `processed` bit(1) DEFAULT NULL,
  `read_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tag_uid` varchar(255) NOT NULL,
  `reader_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1c2khs6rjrtvq374llgri68pb` (`reader_id`),
  CONSTRAINT `FK1c2khs6rjrtvq374llgri68pb` FOREIGN KEY (`reader_id`) REFERENCES `rfid_readers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_reads`
--

LOCK TABLES `tag_reads` WRITE;
/*!40000 ALTER TABLE `tag_reads` DISABLE KEYS */;
INSERT INTO `tag_reads` VALUES (1,'SUCCESS',_binary '','2025-01-01 02:00:00','2891726086',3),(2,'SUCCESS',_binary '','2025-01-03 03:20:00','2891726086',12),(3,'SUCCESS',_binary '','2025-01-02 02:40:00','60689670',5),(4,'SUCCESS',_binary '','2025-01-04 04:10:00','60689670',10),(5,'SUCCESS',_binary '','2025-01-03 03:25:00','2891726103',7),(6,'SUCCESS',_binary '','2025-01-06 03:40:00','2891726103',19),(7,'SUCCESS',_binary '','2025-01-04 04:40:00','2891726104',8),(8,'SUCCESS',_binary '','2025-01-06 06:25:00','2891726104',2),(9,'SUCCESS',_binary '','2025-01-05 05:45:00','2891726105',9),(10,'SUCCESS',_binary '','2025-01-08 10:10:00','2891726105',15),(11,'SUCCESS',_binary '','2025-01-06 02:10:00','2891726106',16),(12,'SUCCESS',_binary '','2025-01-09 07:00:00','2891726106',6),(13,'SUCCESS',_binary '','2025-01-07 03:15:00','2891726107',13),(14,'SUCCESS',_binary '','2025-01-10 09:00:00','2891726107',1),(15,'SUCCESS',_binary '','2025-01-08 04:00:00','2891726108',14),(16,'SUCCESS',_binary '','2025-01-11 05:10:00','2891726108',8),(17,'SUCCESS',_binary '','2025-01-09 04:40:00','2891726109',20),(18,'SUCCESS',_binary '','2025-01-12 11:45:00','2891726109',4),(19,'SUCCESS',_binary '','2025-01-10 05:40:00','2891726110',11),(20,'SUCCESS',_binary '','2025-01-13 07:30:00','2891726110',9),(21,'SUCCESS',_binary '','2025-01-01 01:20:00','2891726201',5),(22,'SUCCESS',_binary '','2025-01-05 03:40:00','2891726201',15),(23,'SUCCESS',_binary '','2025-01-02 02:30:00','2891726202',12),(24,'SUCCESS',_binary '','2025-01-06 04:50:00','2891726202',1),(25,'SUCCESS',_binary '','2025-01-03 03:40:00','2891726203',8),(26,'SUCCESS',_binary '','2025-01-10 06:00:00','2891726203',14),(27,'SUCCESS',_binary '','2025-01-04 04:50:00','2891726204',6),(28,'SUCCESS',_binary '','2025-01-09 02:30:00','2891726204',2),(29,'SUCCESS',_binary '','2025-01-05 06:05:00','2891726205',17),(30,'SUCCESS',_binary '','2025-01-12 07:25:00','2891726205',10),(31,'SUCCESS',_binary '','2025-01-06 07:10:00','2891726206',3),(32,'SUCCESS',_binary '','2025-01-15 01:40:00','2891726206',11),(33,'SUCCESS',_binary '','2025-01-07 07:20:00','2891726207',19),(34,'SUCCESS',_binary '','2025-01-13 09:20:00','2891726207',16),(35,'SUCCESS',_binary '','2025-01-08 08:30:00','2891726208',7),(36,'SUCCESS',_binary '','2025-01-18 11:00:00','2891726208',18),(37,'SUCCESS',_binary '','2025-01-09 09:35:00','2891726209',13),(38,'SUCCESS',_binary '','2025-01-20 03:00:00','2891726209',4),(39,'SUCCESS',_binary '','2025-01-10 10:45:00','2891726210',15),(40,'SUCCESS',_binary '','2025-01-19 05:10:00','2891726210',9),(41,'SUCCESS',_binary '','2025-01-11 01:20:00','2891726211',6),(42,'SUCCESS',_binary '','2025-01-14 03:50:00','2891726211',3),(43,'SUCCESS',_binary '','2025-01-12 02:35:00','2891726212',10),(44,'SUCCESS',_binary '','2025-01-16 05:55:00','2891726212',18),(45,'SUCCESS',_binary '','2025-01-13 03:45:00','2891726213',5),(46,'SUCCESS',_binary '','2025-01-17 08:20:00','2891726213',7),(47,'SUCCESS',_binary '','2025-01-14 04:55:00','2891726214',14),(48,'SUCCESS',_binary '','2025-01-20 10:45:00','2891726214',19),(49,'SUCCESS',_binary '','2025-01-15 06:05:00','2891726215',12),(50,'SUCCESS',_binary '','2025-01-23 02:10:00','2891726215',1),(51,'SUCCESS',_binary '','2025-01-16 07:10:00','2891726216',17),(52,'SUCCESS',_binary '','2025-01-24 07:50:00','2891726216',8),(53,'SUCCESS',_binary '','2025-01-17 07:20:00','2891726217',20),(54,'SUCCESS',_binary '','2025-01-22 04:40:00','2891726217',13),(55,'SUCCESS',_binary '','2025-01-18 08:28:00','2891726218',10),(56,'SUCCESS',_binary '','2025-01-26 09:55:00','2891726218',5),(57,'SUCCESS',_binary '','2025-01-19 09:40:00','2891726219',16),(58,'SUCCESS',_binary '','2025-01-29 06:20:00','2891726219',9),(59,'SUCCESS',_binary '','2025-01-20 10:45:00','2891726220',4),(60,'SUCCESS',_binary '','2025-01-30 03:35:00','2891726220',15),(61,'SUCCESS',_binary '','2025-01-21 02:00:00','2891726221',7),(62,'SUCCESS',_binary '','2025-01-28 04:40:00','2891726221',19),(63,'SUCCESS',_binary '','2025-01-22 03:10:00','2891726222',11),(64,'SUCCESS',_binary '','2025-01-27 08:55:00','2891726222',17),(65,'SUCCESS',_binary '','2025-01-23 04:25:00','2891726223',14),(66,'SUCCESS',_binary '','2025-01-29 10:10:00','2891726223',6),(67,'SUCCESS',_binary '','2025-01-24 05:25:00','2891726224',1),(68,'SUCCESS',_binary '','2025-01-30 11:45:00','2891726224',3),(69,'SUCCESS',_binary '','2025-01-25 06:35:00','2891726225',5),(70,'SUCCESS',_binary '','2025-01-02 01:25:00','2891726225',12),(71,'SUCCESS',_binary '','2025-01-26 07:45:00','2891726226',8),(72,'SUCCESS',_binary '','2025-01-03 03:25:00','2891726226',16),(73,'SUCCESS',_binary '','2025-01-27 08:55:00','2891726227',7),(74,'SUCCESS',_binary '','2025-01-05 06:05:00','2891726227',18),(75,'SUCCESS',_binary '','2025-01-28 10:05:00','2891726228',9),(76,'SUCCESS',_binary '','2025-01-06 07:55:00','2891726228',4),(77,'SUCCESS',_binary '','2025-01-29 10:20:00','2891726229',13),(78,'SUCCESS',_binary '','2025-01-08 08:35:00','2891726229',20),(79,'SUCCESS',_binary '','2025-01-30 01:40:00','2891726230',10),(80,'SUCCESS',_binary '','2025-01-09 09:50:00','2891726230',2),(81,'SUCCESS',_binary '',NULL,'2891726086',1),(82,'SUCCESS',_binary '',NULL,'2891726086',1),(83,'SUCCESS',_binary '',NULL,'2891726086',1),(84,'SUCCESS',_binary '',NULL,'2891726086',1),(85,'SUCCESS',_binary '',NULL,'2891726086',1),(86,'FAILED',_binary '',NULL,'2891726086',1),(87,'FAILED',_binary '',NULL,'2891726086',1),(88,'FAILED',_binary '',NULL,'2891726086',1),(89,'FAILED',_binary '',NULL,'2891726086',1),(90,'SUCCESS',_binary '',NULL,'2891726086',9),(91,'SUCCESS',_binary '',NULL,'2891726086',17),(92,'FAILED',_binary '',NULL,'2891726086',17),(93,'FAILED',_binary '',NULL,'2891726086',17),(94,'FAILED',_binary '',NULL,'2891726086',17),(95,'FAILED',_binary '',NULL,'2891726086',17),(96,'FAILED',_binary '',NULL,'2891726086',15),(97,'FAILED',_binary '',NULL,'2891726086',15),(98,'SUCCESS',_binary '',NULL,'2891726086',15),(99,'FAILED',_binary '',NULL,'2891726086',15),(100,'FAILED',_binary '',NULL,'2891726086',15);
/*!40000 ALTER TABLE `tag_reads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toll_transactions`
--

DROP TABLE IF EXISTS `toll_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `toll_transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fee` decimal(38,2) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` enum('ERROR','FAILED_BALANCE','SUCCESS') DEFAULT NULL,
  `reader_id` bigint DEFAULT NULL,
  `rfid_tag_id` bigint DEFAULT NULL,
  `station_id` bigint DEFAULT NULL,
  `vehicle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgepi0iom63ita28wv1q7howoc` (`reader_id`),
  KEY `FKilfiei9463e1qc7aqgtc3qcaa` (`rfid_tag_id`),
  KEY `FK4emaogmm3gwqy5dbdnnwau53s` (`station_id`),
  KEY `FKpaovsi7qoh7xt2crgwfq9ueek` (`vehicle_id`),
  CONSTRAINT `FK4emaogmm3gwqy5dbdnnwau53s` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`),
  CONSTRAINT `FKgepi0iom63ita28wv1q7howoc` FOREIGN KEY (`reader_id`) REFERENCES `rfid_readers` (`id`),
  CONSTRAINT `FKilfiei9463e1qc7aqgtc3qcaa` FOREIGN KEY (`rfid_tag_id`) REFERENCES `rfid_tags` (`id`),
  CONSTRAINT `FKpaovsi7qoh7xt2crgwfq9ueek` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toll_transactions`
--

LOCK TABLES `toll_transactions` WRITE;
/*!40000 ALTER TABLE `toll_transactions` DISABLE KEYS */;
INSERT INTO `toll_transactions` VALUES (1,'2025-01-01 02:00:00',35000.00,'Auto toll deduction','SUCCESS',3,1,2,1),(2,'2025-01-03 03:20:00',35000.00,'Auto toll deduction','SUCCESS',12,1,6,1),(3,'2025-01-02 02:40:00',35000.00,'Auto toll deduction','SUCCESS',5,2,3,2),(4,'2025-01-04 04:10:00',35000.00,'Insufficient balance','FAILED_BALANCE',10,2,5,2),(5,'2025-01-03 03:25:00',50000.00,'Auto toll deduction','SUCCESS',7,3,4,3),(6,'2025-01-06 03:40:00',50000.00,'Auto toll deduction','SUCCESS',19,3,10,3),(7,'2025-01-04 04:40:00',35000.00,'Auto toll deduction','SUCCESS',8,4,4,4),(8,'2025-01-06 06:25:00',35000.00,'Auto toll deduction','SUCCESS',2,4,1,4),(9,'2025-01-05 05:45:00',50000.00,'Auto toll deduction','SUCCESS',9,5,5,5),(10,'2025-01-08 10:10:00',50000.00,'Auto toll deduction','SUCCESS',15,5,8,5),(11,'2025-01-06 02:10:00',35000.00,'Auto toll deduction','SUCCESS',16,6,8,6),(12,'2025-01-09 07:00:00',35000.00,'Auto toll deduction','SUCCESS',6,6,3,6),(13,'2025-01-07 03:15:00',35000.00,'Auto toll deduction','SUCCESS',13,7,7,7),(14,'2025-01-10 09:00:00',35000.00,'Insufficient balance','FAILED_BALANCE',1,7,1,7),(15,'2025-01-08 04:00:00',50000.00,'Auto toll deduction','SUCCESS',14,8,7,8),(16,'2025-01-11 05:10:00',50000.00,'Auto toll deduction','SUCCESS',8,8,4,8),(17,'2025-01-09 04:40:00',35000.00,'Auto toll deduction','SUCCESS',20,9,10,9),(18,'2025-01-12 11:45:00',35000.00,'Auto toll deduction','SUCCESS',4,9,2,9),(19,'2025-01-10 05:40:00',35000.00,'Auto toll deduction','SUCCESS',11,10,6,10),(20,'2025-01-13 07:30:00',35000.00,'Auto toll deduction','SUCCESS',9,10,5,10),(21,'2025-02-01 01:20:00',35000.00,'Auto toll deduction','SUCCESS',5,11,3,11),(22,'2025-02-05 03:40:00',35000.00,'Auto toll deduction','SUCCESS',15,11,8,11),(23,'2025-02-02 02:30:00',35000.00,'Auto toll deduction','SUCCESS',12,12,6,12),(24,'2025-02-06 04:50:00',35000.00,'Insufficient balance','FAILED_BALANCE',1,12,1,12),(25,'2025-02-03 03:40:00',50000.00,'Auto toll deduction','SUCCESS',8,13,4,13),(26,'2025-02-10 06:00:00',50000.00,'Auto toll deduction','SUCCESS',14,13,7,13),(27,'2025-02-04 04:50:00',35000.00,'Auto toll deduction','SUCCESS',6,14,3,14),(28,'2025-02-09 02:30:00',35000.00,'Auto toll deduction','SUCCESS',2,14,1,14),(29,'2025-02-05 06:05:00',35000.00,'Auto toll deduction','SUCCESS',17,15,9,15),(30,'2025-02-12 07:25:00',35000.00,'Auto toll deduction','SUCCESS',10,15,5,15),(31,'2025-02-06 07:10:00',35000.00,'Auto toll deduction','SUCCESS',3,16,2,16),(32,'2025-02-15 01:40:00',35000.00,'Auto toll deduction','SUCCESS',11,16,6,16),(33,'2025-02-07 07:20:00',50000.00,'Auto toll deduction','SUCCESS',19,17,10,17),(34,'2025-02-13 09:20:00',50000.00,'Auto toll deduction','SUCCESS',16,17,8,17),(35,'2025-02-08 08:30:00',35000.00,'Auto toll deduction','SUCCESS',7,18,4,18),(36,'2025-02-18 11:00:00',35000.00,'Auto toll deduction','SUCCESS',18,18,10,18),(37,'2025-02-09 09:35:00',35000.00,'Auto toll deduction','SUCCESS',13,19,7,19),(38,'2025-02-20 03:00:00',35000.00,'Auto toll deduction','SUCCESS',4,19,2,19),(39,'2025-02-10 10:45:00',35000.00,'Auto toll deduction','SUCCESS',15,20,8,20),(40,'2025-02-19 05:10:00',35000.00,'Insufficient balance','FAILED_BALANCE',9,20,5,20),(41,'2025-03-11 01:20:00',35000.00,'Auto toll deduction','SUCCESS',6,21,4,21),(42,'2025-03-14 03:50:00',35000.00,'Auto toll deduction','SUCCESS',3,21,2,21),(43,'2025-03-12 02:35:00',50000.00,'Auto toll deduction','SUCCESS',10,22,5,22),(44,'2025-03-16 05:55:00',50000.00,'Auto toll deduction','SUCCESS',18,22,9,22),(45,'2025-03-13 03:45:00',35000.00,'Auto toll deduction','SUCCESS',5,23,3,23),(46,'2025-03-17 08:20:00',35000.00,'Insufficient balance','FAILED_BALANCE',7,23,4,23),(47,'2025-03-14 04:55:00',35000.00,'Auto toll deduction','SUCCESS',14,24,7,24),(48,'2025-03-20 10:45:00',35000.00,'Auto toll deduction','SUCCESS',19,24,10,24),(49,'2025-03-15 06:05:00',50000.00,'Auto toll deduction','SUCCESS',12,25,6,25),(50,'2025-03-23 02:10:00',50000.00,'Auto toll deduction','SUCCESS',1,25,1,25),(51,'2025-03-16 07:10:00',35000.00,'Auto toll deduction','SUCCESS',17,26,9,26),(52,'2025-03-24 07:50:00',35000.00,'Auto toll deduction','SUCCESS',8,26,4,26),(53,'2025-04-17 07:20:00',35000.00,'Auto toll deduction','SUCCESS',20,27,10,27),(54,'2025-04-22 04:40:00',35000.00,'Auto toll deduction','SUCCESS',13,27,7,27),(55,'2025-04-18 08:28:00',35000.00,'Auto toll deduction','SUCCESS',10,28,5,28),(56,'2025-04-26 09:55:00',35000.00,'Auto toll deduction','SUCCESS',15,28,8,28),(57,'2025-04-19 09:40:00',35000.00,'Auto toll deduction','SUCCESS',16,29,8,29),(58,'2025-04-29 06:20:00',35000.00,'Auto toll deduction','SUCCESS',9,29,5,29),(59,'2025-04-20 10:45:00',35000.00,'Auto toll deduction','SUCCESS',4,30,2,30),(60,'2025-04-30 03:35:00',35000.00,'Auto toll deduction','SUCCESS',15,30,8,30),(61,'2025-04-21 02:00:00',50000.00,'Auto toll deduction','SUCCESS',7,31,4,31),(62,'2025-04-28 04:40:00',50000.00,'Auto toll deduction','SUCCESS',19,31,10,31),(63,'2025-04-22 03:10:00',35000.00,'Auto toll deduction','SUCCESS',11,32,6,32),(64,'2025-04-27 08:55:00',35000.00,'Auto toll deduction','SUCCESS',17,32,9,32),(65,'2025-04-23 04:25:00',35000.00,'Auto toll deduction','SUCCESS',14,33,7,33),(66,'2025-04-29 10:10:00',35000.00,'Auto toll deduction','SUCCESS',6,33,3,33),(67,'2025-04-24 05:25:00',35000.00,'Auto toll deduction','SUCCESS',1,34,1,34),(68,'2025-04-30 11:45:00',35000.00,'Auto toll deduction','SUCCESS',3,34,2,34),(69,'2025-04-25 06:35:00',50000.00,'Auto toll deduction','SUCCESS',5,35,3,35),(70,'2025-05-02 01:25:00',50000.00,'Auto toll deduction','SUCCESS',12,35,6,35),(71,'2025-04-26 07:45:00',35000.00,'Auto toll deduction','SUCCESS',16,36,8,36),(72,'2025-05-03 03:25:00',35000.00,'Auto toll deduction','SUCCESS',17,36,9,36),(73,'2025-05-27 08:55:00',35000.00,'Auto toll deduction','SUCCESS',14,37,7,37),(74,'2025-05-05 06:05:00',35000.00,'Auto toll deduction','SUCCESS',18,37,10,37),(75,'2025-05-28 10:05:00',35000.00,'Auto toll deduction','SUCCESS',10,38,5,38),(76,'2025-05-06 07:55:00',35000.00,'Auto toll deduction','SUCCESS',4,38,2,38),(77,'2025-05-29 10:20:00',35000.00,'Auto toll deduction','SUCCESS',13,39,7,39),(78,'2025-05-08 08:35:00',35000.00,'Auto toll deduction','SUCCESS',20,39,10,39),(79,'2025-05-30 01:40:00',35000.00,'Auto toll deduction','SUCCESS',10,40,5,40),(80,'2025-05-09 09:50:00',35000.00,'Insufficient balance','FAILED_BALANCE',2,40,1,40),(81,'2025-11-23 02:32:24',30000.00,'Thu phí qua làn thành công!','SUCCESS',1,41,1,41),(82,'2025-11-23 02:32:45',30000.00,'Thu phí qua làn thành công!','SUCCESS',1,41,1,41),(83,'2025-11-23 02:33:10',30000.00,'Thu phí qua làn thành công!','SUCCESS',1,41,1,41),(84,'2025-11-23 02:33:23',30000.00,'Thu phí qua làn thành công!','SUCCESS',1,41,1,41),(85,'2025-11-23 02:33:36',30000.00,'Thu phí qua làn thành công!','SUCCESS',1,41,1,41),(86,'2025-11-23 02:34:31',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',1,41,1,41),(87,'2025-11-23 02:39:21',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',1,41,1,41),(88,'2025-11-23 02:39:28',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',1,41,1,41),(89,'2025-11-23 02:39:34',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',1,41,1,41),(90,'2025-11-26 02:57:31',30000.00,'Thu phí qua làn thành công!','SUCCESS',9,41,5,41),(91,'2025-11-26 02:58:12',30000.00,'Thu phí qua làn thành công!','SUCCESS',17,41,9,41),(92,'2025-11-26 02:59:38',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',17,41,9,41),(93,'2025-11-26 02:59:43',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',17,41,9,41),(94,'2025-11-26 02:59:51',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',17,41,9,41),(95,'2025-11-26 03:00:08',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',17,41,9,41),(96,'2025-11-26 03:20:38',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',15,41,8,41),(97,'2025-11-26 03:23:51',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',15,41,8,41),(98,'2025-11-26 03:40:09',30000.00,'Thu phí qua làn thành công!','SUCCESS',15,41,8,41),(99,'2025-11-26 03:40:34',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',15,41,8,41),(100,'2025-11-26 03:41:17',30000.00,'Thu phí qua làn thất bại!','FAILED_BALANCE',15,41,8,41);
/*!40000 ALTER TABLE `toll_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topups`
--

DROP TABLE IF EXISTS `topups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `balance_after` decimal(38,2) DEFAULT NULL,
  `bank_code` varchar(255) DEFAULT NULL,
  `completed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `method` enum('BANK','VNPAY') DEFAULT NULL,
  `pay_date` datetime(6) DEFAULT NULL,
  `reference_code` varchar(255) DEFAULT NULL,
  `status` enum('COMPLETED','FAILED','PENDING') DEFAULT NULL,
  `transaction_no` varchar(255) DEFAULT NULL,
  `vnp_response_code` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `wallet_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd98pja3i1c6gyt0owhet2906r` (`user_id`),
  KEY `FK3cnjmtlpa7xs90vrv6y2aj6fw` (`wallet_id`),
  CONSTRAINT `FK3cnjmtlpa7xs90vrv6y2aj6fw` FOREIGN KEY (`wallet_id`) REFERENCES `wallets` (`id`),
  CONSTRAINT `FKd98pja3i1c6gyt0owhet2906r` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topups`
--

LOCK TABLES `topups` WRITE;
/*!40000 ALTER TABLE `topups` DISABLE KEYS */;
INSERT INTO `topups` VALUES (1,300000.00,300000.00,'VCB','2025-01-01 08:00:00.000000','2025-01-01 08:00:00.000000','Initial topup','VNPAY','2025-01-01 08:00:00.000000','INIT-2','COMPLETED','TN2','00',2,2),(2,300000.00,300000.00,'VCB','2025-01-02 08:00:00.000000','2025-01-02 08:00:00.000000','Initial topup','VNPAY','2025-01-02 08:00:00.000000','INIT-3','COMPLETED','TN3','00',3,3),(3,300000.00,300000.00,'VCB','2025-01-03 08:00:00.000000','2025-01-03 08:00:00.000000','Initial topup','VNPAY','2025-01-03 08:00:00.000000','INIT-4','COMPLETED','TN4','00',4,4),(4,300000.00,300000.00,'VCB','2025-01-04 08:00:00.000000','2025-01-04 08:00:00.000000','Initial topup','VNPAY','2025-01-04 08:00:00.000000','INIT-5','COMPLETED','TN5','00',5,5),(5,300000.00,300000.00,'VCB','2025-01-05 08:00:00.000000','2025-01-05 08:00:00.000000','Initial topup','VNPAY','2025-01-05 08:00:00.000000','INIT-6','COMPLETED','TN6','00',6,6),(6,300000.00,300000.00,'VCB','2025-01-06 08:00:00.000000','2025-01-06 08:00:00.000000','Initial topup','VNPAY','2025-01-06 08:00:00.000000','INIT-7','COMPLETED','TN7','00',7,7),(7,300000.00,300000.00,'VCB','2025-01-07 08:00:00.000000','2025-01-07 08:00:00.000000','Initial topup','VNPAY','2025-01-07 08:00:00.000000','INIT-8','COMPLETED','TN8','00',8,8),(8,300000.00,300000.00,'VCB','2025-01-08 08:00:00.000000','2025-01-08 08:00:00.000000','Initial topup','VNPAY','2025-01-08 08:00:00.000000','INIT-9','COMPLETED','TN9','00',9,9),(9,300000.00,300000.00,'VCB','2025-01-09 08:00:00.000000','2025-01-09 08:00:00.000000','Initial topup','VNPAY','2025-01-09 08:00:00.000000','INIT-10','COMPLETED','TN10','00',10,10),(10,300000.00,300000.00,'VCB','2025-01-10 08:00:00.000000','2025-01-10 08:00:00.000000','Initial topup','VNPAY','2025-01-10 08:00:00.000000','INIT-11','COMPLETED','TN11','00',11,11),(11,100000.00,100000.00,'NCB','2025-11-22 23:54:27.672627','2025-11-22 23:53:54.089937','Nạp tiền vào ví','VNPAY','2025-11-22 23:54:19.000000','REFce2ca008ac','COMPLETED','15281280','00',12,12),(12,50000.00,150000.00,'NCB','2025-11-23 00:33:57.190146','2025-11-23 00:33:16.109602','Nạp tiền vào ví','VNPAY','2025-11-23 00:33:48.000000','REF1d08cf38db','COMPLETED','15281318','00',12,12),(13,50000.00,NULL,NULL,NULL,'2025-11-23 01:10:27.173814','Nạp tiền vào ví','VNPAY',NULL,'REFb3e2959432','PENDING',NULL,NULL,12,12),(14,10000.00,160000.00,'NCB','2025-11-23 01:11:23.663589','2025-11-23 01:10:42.191327','Nạp tiền vào ví','VNPAY','2025-11-23 01:11:08.000000','REF2db89e9860','COMPLETED','15281347','00',12,12),(15,100000.00,NULL,NULL,NULL,'2025-11-23 09:34:41.429021','Nạp tiền vào ví','VNPAY',NULL,'REFb6d8ba8451','PENDING',NULL,NULL,12,12),(16,50000.00,NULL,NULL,NULL,'2025-11-26 09:51:52.699331','Nạp tiền vào ví','VNPAY',NULL,'REF6875ad3a78','PENDING',NULL,NULL,12,12),(17,50000.00,60000.00,'NCB','2025-11-26 09:54:14.783514','2025-11-26 09:53:32.165940','Nạp tiền vào ví','VNPAY','2025-11-26 09:54:02.000000','REFaf568874a8','COMPLETED','15301739','00',12,12),(18,50000.00,NULL,NULL,NULL,'2025-11-26 10:20:53.298150','Nạp tiền vào ví','VNPAY',NULL,'REF84ff583515','PENDING',NULL,NULL,12,12),(19,50000.00,50000.00,'NCB','2025-11-26 10:39:38.936985','2025-11-26 10:38:54.582132','Nạp tiền vào ví','VNPAY','2025-11-26 10:39:21.000000','REFe67ab19218','COMPLETED','15301874','00',12,12);
/*!40000 ALTER TABLE `topups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ADMIN'),(2,'CUSTOMER'),(3,'CUSTOMER'),(4,'CUSTOMER'),(5,'CUSTOMER'),(6,'CUSTOMER'),(7,'CUSTOMER'),(8,'CUSTOMER'),(9,'CUSTOMER'),(10,'CUSTOMER'),(11,'CUSTOMER'),(12,'CUSTOMER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `activation_expiry_time` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','BLOCKED','PENDING') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-11-22 23:51:51.197616','123 Lê Lợi, TP.HCM','2025-11-22 23:48:51.226750','admin@gmail.com',_binary '','Nguyen Van A','$2a$10$DhY62UTdP0U1URFxi15Ojusk7SA0wJF08MgBozHXOB49eIKEvkPEi','0901234567','ACTIVE','2025-11-22 23:48:51.226750','admin'),(2,NULL,'456 Nguyễn Trãi, Hà Nội','2025-01-01 08:30:00.000000','tranb@example.com',NULL,'Tran Thi B','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0912345678','ACTIVE',NULL,'tranb@example.com'),(3,NULL,'789 Lý Thường Kiệt, Đà Nẵng','2025-01-01 08:30:00.000000','levanc@example.com',NULL,'Le Van C','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0932123456','ACTIVE',NULL,'levanc@example.com'),(4,NULL,'12 Trần Hưng Đạo, Cần Thơ','2025-01-01 08:30:00.000000','phamd@example.com',NULL,'Pham Thi D','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0987654321','ACTIVE',NULL,'phamd@example.com'),(5,NULL,'34 Nguyễn Huệ, Huế','2025-01-01 08:30:00.000000','buie@example.com',NULL,'Bui Van E','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0909123456','ACTIVE',NULL,'buie@example.com'),(6,NULL,'56 Phan Chu Trinh, Vũng Tàu','2025-01-01 08:30:00.000000','dof@example.com',NULL,'Do Thi F','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0976543210','ACTIVE',NULL,'dof@example.com'),(7,NULL,'78 Hai Bà Trưng, Hà Nội','2025-01-01 08:30:00.000000','nguyeng@example.com',NULL,'Nguyen Van G','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0911122233','ACTIVE',NULL,'nguyeng@example.com'),(8,NULL,'90 Lý Tự Trọng, TP.HCM','2025-01-01 08:30:00.000000','tranh@example.com',NULL,'Tran Van H','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0945678901','ACTIVE',NULL,'tranh@example.com'),(9,NULL,'101 Bạch Đằng, Đà Nẵng','2025-01-01 08:30:00.000000','voi@example.com',NULL,'Vo Thi I','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0923456789','ACTIVE',NULL,'voi@example.com'),(10,NULL,'112 Hùng Vương, Cần Thơ','2025-01-01 08:30:00.000000','phamj@example.com',NULL,'Pham Van J','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0906789123','ACTIVE',NULL,'phamj@example.com'),(11,NULL,'23 Điện Biên Phủ, Huế','2025-01-01 08:30:00.000000','lek@example.com',NULL,'Le Thi K','$2a$10$oA5lMQPcXuecMov6jJij6.EBN9mJJNPlt7sMHlGJkcnriyhNzn6yO','0977123456','ACTIVE',NULL,'lek@example.com'),(12,'2025-11-22 23:53:36.242396','Gò Vấp, Tp Hồ Chí Minh','2025-11-22 23:50:36.242395','voduybao19052005@gmail.com',_binary '','voduybao19052005@gmail.com','$2a$10$YhypazWiTx6aT0iLrF2TY.hdpIIYXh/9E7ixOxvCFfNJYVxywtVlS','0236547891','ACTIVE','2025-11-22 23:51:00.108644','voduybao19052005@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `plate_number` varchar(255) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `vehicle_type` enum('CAR','TRUCK') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKh6kd0awsaislk5n2f3ea1hhuq` (`plate_number`),
  KEY `FKo4u5y92lt2sx8y2dc1bb9sewc` (`user_id`),
  CONSTRAINT `FKo4u5y92lt2sx8y2dc1bb9sewc` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,'Toyota','Red','2025-01-01 08:30:00.000000','Vios','33A-12345','ACTIVE','2025-11-26 18:03:58.304711','CAR',11),(2,'Hyundai','White','2025-01-02 09:10:00.000000','Accent','43A-67890','ACTIVE','2025-01-02 09:10:00.000000','CAR',2),(3,'Ford','Blue','2025-01-03 10:05:00.000000','Ranger','51C-24680','ACTIVE','2025-01-03 10:05:00.000000','TRUCK',3),(4,'Kia','Black','2025-01-04 11:15:00.000000','Morning','65A-11223','ACTIVE','2025-01-04 11:15:00.000000','CAR',4),(5,'Isuzu','Silver','2025-01-05 12:20:00.000000','D-Max','75C-33445','ACTIVE','2025-01-05 12:20:00.000000','TRUCK',5),(6,'Mazda','Gray','2025-01-06 08:40:00.000000','3','29A-55667','ACTIVE','2025-01-06 08:40:00.000000','CAR',7),(7,'Honda','White','2025-01-07 09:50:00.000000','City','60A-77889','ACTIVE','2025-01-07 09:50:00.000000','CAR',8),(8,'Suzuki','Blue','2025-01-08 10:30:00.000000','Carry','36C-99001','ACTIVE','2025-01-08 10:30:00.000000','TRUCK',9),(9,'VinFast','Black','2025-01-09 11:10:00.000000','Lux A2.0','30G-11234','ACTIVE','2025-01-09 11:10:00.000000','CAR',10),(10,'Toyota','Brown','2025-01-10 12:10:00.000000','Fortuner','50A-22334','ACTIVE','2025-01-10 12:10:00.000000','CAR',6),(11,'Honda','White','2025-01-01 08:00:00.000000','Civic','30B-20111','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(12,'Toyota','Black','2025-01-02 09:10:00.000000','Camry','30B-20112','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(13,'Ford','Blue','2025-01-03 10:20:00.000000','Ranger','30B-20113','ACTIVE','2025-03-05 12:00:00.000000','TRUCK',4),(14,'Kia','Red','2025-01-04 11:30:00.000000','Morning','30B-20114','ACTIVE','2025-03-05 12:00:00.000000','CAR',4),(15,'Mazda','Grey','2025-01-05 12:40:00.000000','3','30B-20115','ACTIVE','2025-03-05 12:00:00.000000','CAR',4),(16,'Hyundai','White','2025-01-06 13:50:00.000000','Elantra','30B-20116','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(17,'Mitsubishi','Black','2025-01-07 14:00:00.000000','Triton','30B-20117','ACTIVE','2025-03-05 12:00:00.000000','TRUCK',5),(18,'Suzuki','Green','2025-01-08 15:10:00.000000','Swift','30B-20118','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(19,'VinFast','Blue','2025-01-09 16:20:00.000000','VF e34','30B-20119','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(20,'Toyota','Silver','2025-01-10 17:30:00.000000','Innova','30B-20120','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(21,'Honda','Black','2025-01-11 08:05:00.000000','CR-V','30B-20121','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(22,'Ford','White','2025-01-12 09:15:00.000000','Transit','30B-20122','ACTIVE','2025-03-05 12:00:00.000000','TRUCK',4),(23,'Kia','Yellow','2025-01-13 10:25:00.000000','Sorento','30B-20123','ACTIVE','2025-03-05 12:00:00.000000','CAR',4),(24,'Mazda','Red','2025-01-14 11:35:00.000000','CX-5','30B-20124','ACTIVE','2025-03-05 12:00:00.000000','CAR',4),(25,'Hyundai','Blue','2025-01-15 12:45:00.000000','SantaFe','30B-20125','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(26,'Mitsubishi','Grey','2025-01-16 13:55:00.000000','Xpander','30B-20126','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(27,'Suzuki','White','2025-01-17 14:05:00.000000','Ertiga','30B-20127','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(28,'VinFast','Black','2025-01-18 15:15:00.000000','VF 8','30B-20128','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(29,'Toyota','Blue','2025-01-19 16:25:00.000000','Fortuner','30B-20129','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(30,'Honda','Green','2025-01-20 17:35:00.000000','Accord','30B-20130','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(31,'Ford','Red','2025-01-21 08:45:00.000000','F-150','30B-20131','ACTIVE','2025-03-05 12:00:00.000000','TRUCK',4),(32,'Kia','White','2025-01-22 09:55:00.000000','K5','30B-20132','ACTIVE','2025-03-05 12:00:00.000000','CAR',4),(33,'Mazda','Black','2025-01-23 11:05:00.000000','CX-3','30B-20133','ACTIVE','2025-03-05 12:00:00.000000','CAR',4),(34,'Hyundai','Silver','2025-01-24 12:15:00.000000','Palisade','30B-20134','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(35,'Mitsubishi','Blue','2025-01-25 13:25:00.000000','Pajero','30B-20135','ACTIVE','2025-03-05 12:00:00.000000','TRUCK',5),(36,'Suzuki','Red','2025-01-26 14:35:00.000000','Ciaz','30B-20136','ACTIVE','2025-03-05 12:00:00.000000','CAR',5),(37,'VinFast','Green','2025-01-27 15:45:00.000000','VF 3','30B-20137','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(38,'Toyota','Brown','2025-01-28 16:55:00.000000','Yaris','30B-20138','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(39,'Honda','Orange','2025-01-29 17:05:00.000000','BR-V','30B-20139','ACTIVE','2025-03-05 12:00:00.000000','CAR',3),(40,'Ford','Purple','2025-01-30 08:15:00.000000','Courier','30B-20140','ACTIVE','2025-03-05 12:00:00.000000','TRUCK',4),(41,NULL,NULL,'2025-11-22 23:55:07.751873',NULL,'81H-9999','ACTIVE','2025-11-26 19:03:24.607025','CAR',12),(42,NULL,NULL,'2025-11-22 23:56:29.484649',NULL,'81H-8888','ACTIVE','2025-11-23 01:14:38.276617','CAR',12),(43,NULL,NULL,'2025-11-23 00:35:48.131458',NULL,'81H-4444','ACTIVE','2025-11-26 00:01:41.679802','CAR',12);
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_transactions`
--

DROP TABLE IF EXISTS `wallet_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `balance_after` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `transaction_type` enum('DEDUCT','TOPUP') DEFAULT NULL,
  `related_toll_transaction_id` bigint DEFAULT NULL,
  `wallet_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq18sbtc5c4bhu52ls8r1jcu9f` (`related_toll_transaction_id`),
  KEY `FK8seu7b87ifqi09ghhssusmb0x` (`wallet_id`),
  CONSTRAINT `FK8seu7b87ifqi09ghhssusmb0x` FOREIGN KEY (`wallet_id`) REFERENCES `wallets` (`id`),
  CONSTRAINT `FKq18sbtc5c4bhu52ls8r1jcu9f` FOREIGN KEY (`related_toll_transaction_id`) REFERENCES `toll_transactions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_transactions`
--

LOCK TABLES `wallet_transactions` WRITE;
/*!40000 ALTER TABLE `wallet_transactions` DISABLE KEYS */;
INSERT INTO `wallet_transactions` VALUES (1,300000.00,300000.00,'2025-01-01 08:00:00.000000','Initial topup','TOPUP',NULL,2),(2,300000.00,300000.00,'2025-01-02 08:00:00.000000','Initial topup','TOPUP',NULL,3),(3,300000.00,300000.00,'2025-01-03 08:00:00.000000','Initial topup','TOPUP',NULL,4),(4,300000.00,300000.00,'2025-01-04 08:00:00.000000','Initial topup','TOPUP',NULL,5),(5,300000.00,300000.00,'2025-01-05 08:00:00.000000','Initial topup','TOPUP',NULL,6),(6,300000.00,300000.00,'2025-01-06 08:00:00.000000','Initial topup','TOPUP',NULL,7),(7,300000.00,300000.00,'2025-01-07 08:00:00.000000','Initial topup','TOPUP',NULL,8),(8,300000.00,300000.00,'2025-01-08 08:00:00.000000','Initial topup','TOPUP',NULL,9),(9,300000.00,300000.00,'2025-01-09 08:00:00.000000','Initial topup','TOPUP',NULL,10),(10,300000.00,300000.00,'2025-01-10 08:00:00.000000','Initial topup','TOPUP',NULL,11),(11,35000.00,265000.00,'2025-01-01 09:00:00.000000','Auto toll deduction','DEDUCT',1,11),(12,35000.00,230000.00,'2025-01-03 10:20:00.000000','Auto toll deduction','DEDUCT',2,11),(13,35000.00,265000.00,'2025-01-02 09:40:00.000000','Auto toll deduction','DEDUCT',3,2),(14,50000.00,250000.00,'2025-01-03 10:25:00.000000','Auto toll deduction','DEDUCT',5,3),(15,50000.00,200000.00,'2025-01-06 10:40:00.000000','Auto toll deduction','DEDUCT',6,3),(16,35000.00,265000.00,'2025-01-04 11:40:00.000000','Auto toll deduction','DEDUCT',7,4),(17,35000.00,230000.00,'2025-01-06 13:25:00.000000','Auto toll deduction','DEDUCT',8,4),(18,50000.00,250000.00,'2025-01-05 12:45:00.000000','Auto toll deduction','DEDUCT',9,5),(19,50000.00,200000.00,'2025-01-08 17:10:00.000000','Auto toll deduction','DEDUCT',10,5),(20,35000.00,265000.00,'2025-01-06 09:10:00.000000','Auto toll deduction','DEDUCT',11,7),(21,35000.00,230000.00,'2025-01-09 14:00:00.000000','Auto toll deduction','DEDUCT',12,7),(22,35000.00,265000.00,'2025-01-07 10:15:00.000000','Auto toll deduction','DEDUCT',13,8),(23,50000.00,250000.00,'2025-01-08 11:00:00.000000','Auto toll deduction','DEDUCT',15,9),(24,50000.00,200000.00,'2025-01-11 12:10:00.000000','Auto toll deduction','DEDUCT',16,9),(25,35000.00,265000.00,'2025-01-09 11:40:00.000000','Auto toll deduction','DEDUCT',17,10),(26,35000.00,230000.00,'2025-01-12 18:45:00.000000','Auto toll deduction','DEDUCT',18,10),(27,35000.00,265000.00,'2025-01-10 12:40:00.000000','Auto toll deduction','DEDUCT',19,6),(28,35000.00,230000.00,'2025-01-13 14:30:00.000000','Auto toll deduction','DEDUCT',20,6),(29,35000.00,265000.00,'2025-02-01 08:20:00.000000','Auto toll deduction','DEDUCT',21,3),(30,35000.00,230000.00,'2025-02-05 10:40:00.000000','Auto toll deduction','DEDUCT',22,3),(31,35000.00,265000.00,'2025-02-02 09:30:00.000000','Auto toll deduction','DEDUCT',23,3),(32,50000.00,250000.00,'2025-02-03 10:40:00.000000','Auto toll deduction','DEDUCT',25,4),(33,50000.00,200000.00,'2025-02-10 13:00:00.000000','Auto toll deduction','DEDUCT',26,4),(34,35000.00,265000.00,'2025-02-04 11:50:00.000000','Auto toll deduction','DEDUCT',27,4),(35,35000.00,230000.00,'2025-02-09 09:30:00.000000','Auto toll deduction','DEDUCT',28,4),(36,35000.00,265000.00,'2025-02-05 13:05:00.000000','Auto toll deduction','DEDUCT',29,5),(37,35000.00,230000.00,'2025-02-12 14:25:00.000000','Auto toll deduction','DEDUCT',30,5),(38,35000.00,265000.00,'2025-02-06 14:10:00.000000','Auto toll deduction','DEDUCT',31,5),(39,35000.00,230000.00,'2025-02-15 08:40:00.000000','Auto toll deduction','DEDUCT',32,5),(40,50000.00,250000.00,'2025-02-07 14:20:00.000000','Auto toll deduction','DEDUCT',33,3),(41,50000.00,200000.00,'2025-02-13 16:20:00.000000','Auto toll deduction','DEDUCT',34,3),(42,35000.00,265000.00,'2025-02-08 15:30:00.000000','Auto toll deduction','DEDUCT',35,5),(43,35000.00,230000.00,'2025-02-18 18:00:00.000000','Auto toll deduction','DEDUCT',36,5),(44,35000.00,265000.00,'2025-02-09 16:35:00.000000','Auto toll deduction','DEDUCT',37,3),(45,35000.00,230000.00,'2025-02-20 10:00:00.000000','Auto toll deduction','DEDUCT',38,3),(46,35000.00,265000.00,'2025-02-10 17:45:00.000000','Auto toll deduction','DEDUCT',39,3),(47,35000.00,265000.00,'2025-03-11 08:20:00.000000','Auto toll deduction','DEDUCT',41,3),(48,35000.00,230000.00,'2025-03-14 10:50:00.000000','Auto toll deduction','DEDUCT',42,3),(49,50000.00,250000.00,'2025-03-12 09:35:00.000000','Auto toll deduction','DEDUCT',43,4),(50,50000.00,200000.00,'2025-03-16 12:55:00.000000','Auto toll deduction','DEDUCT',44,4),(51,35000.00,165000.00,'2025-03-13 10:45:00.000000','Auto toll deduction','DEDUCT',45,4),(52,35000.00,265000.00,'2025-03-14 11:55:00.000000','Auto toll deduction','DEDUCT',47,4),(53,35000.00,230000.00,'2025-03-20 17:45:00.000000','Auto toll deduction','DEDUCT',48,4),(54,50000.00,150000.00,'2025-03-15 13:05:00.000000','Auto toll deduction','DEDUCT',49,5),(55,50000.00,100000.00,'2025-03-23 09:10:00.000000','Auto toll deduction','DEDUCT',50,5),(56,35000.00,195000.00,'2025-03-16 14:10:00.000000','Auto toll deduction','DEDUCT',51,4),(57,35000.00,160000.00,'2025-03-24 14:50:00.000000','Auto toll deduction','DEDUCT',52,4),(58,35000.00,265000.00,'2025-03-17 14:20:00.000000','Auto toll deduction','DEDUCT',53,5),(59,35000.00,230000.00,'2025-03-22 11:40:00.000000','Auto toll deduction','DEDUCT',54,5),(60,35000.00,265000.00,'2025-03-18 15:28:00.000000','Auto toll deduction','DEDUCT',55,3),(61,35000.00,230000.00,'2025-03-26 16:55:00.000000','Auto toll deduction','DEDUCT',56,3),(62,35000.00,265000.00,'2025-03-19 16:40:00.000000','Auto toll deduction','DEDUCT',57,3),(63,35000.00,230000.00,'2025-03-29 13:20:00.000000','Auto toll deduction','DEDUCT',58,3),(64,35000.00,195000.00,'2025-03-20 17:45:00.000000','Auto toll deduction','DEDUCT',59,4),(65,35000.00,160000.00,'2025-03-30 10:35:00.000000','Auto toll deduction','DEDUCT',60,4),(66,50000.00,110000.00,'2025-03-21 09:00:00.000000','Auto toll deduction','DEDUCT',61,4),(67,50000.00,60000.00,'2025-03-28 11:40:00.000000','Auto toll deduction','DEDUCT',62,4),(68,35000.00,125000.00,'2025-03-22 10:10:00.000000','Auto toll deduction','DEDUCT',63,4),(69,35000.00,90000.00,'2025-03-27 15:55:00.000000','Auto toll deduction','DEDUCT',64,4),(70,35000.00,55000.00,'2025-03-23 11:25:00.000000','Auto toll deduction','DEDUCT',65,4),(71,35000.00,20000.00,'2025-03-29 17:10:00.000000','Auto toll deduction','DEDUCT',66,4),(72,35000.00,265000.00,'2025-03-24 12:25:00.000000','Auto toll deduction','DEDUCT',67,5),(73,35000.00,230000.00,'2025-03-30 18:45:00.000000','Auto toll deduction','DEDUCT',68,5),(74,50000.00,180000.00,'2025-03-25 13:35:00.000000','Auto toll deduction','DEDUCT',69,5),(75,50000.00,130000.00,'2025-04-02 08:25:00.000000','Auto toll deduction','DEDUCT',70,5),(76,35000.00,265000.00,'2025-03-26 14:45:00.000000','Auto toll deduction','DEDUCT',71,3),(77,35000.00,230000.00,'2025-04-03 10:25:00.000000','Auto toll deduction','DEDUCT',72,3),(78,35000.00,195000.00,'2025-03-27 15:55:00.000000','Auto toll deduction','DEDUCT',73,3),(79,35000.00,160000.00,'2025-04-05 13:05:00.000000','Auto toll deduction','DEDUCT',74,3),(80,35000.00,125000.00,'2025-03-28 17:05:00.000000','Auto toll deduction','DEDUCT',75,3),(81,35000.00,90000.00,'2025-04-06 14:55:00.000000','Auto toll deduction','DEDUCT',76,3),(82,35000.00,55000.00,'2025-03-29 17:20:00.000000','Auto toll deduction','DEDUCT',77,3),(83,35000.00,20000.00,'2025-04-08 15:35:00.000000','Auto toll deduction','DEDUCT',78,3),(84,35000.00,265000.00,'2025-03-30 08:40:00.000000','Auto toll deduction','DEDUCT',79,3),(85,30000.00,130000.00,'2025-11-23 09:32:23.818913','Trừ phí qua trạm Trạm thu phí Pháp Vân - Cầu Giẽ','DEDUCT',81,12),(86,30000.00,100000.00,'2025-11-23 09:32:44.737042','Trừ phí qua trạm Trạm thu phí Pháp Vân - Cầu Giẽ','DEDUCT',82,12),(87,30000.00,70000.00,'2025-11-23 09:33:10.334250','Trừ phí qua trạm Trạm thu phí Pháp Vân - Cầu Giẽ','DEDUCT',83,12),(88,30000.00,40000.00,'2025-11-23 09:33:23.364912','Trừ phí qua trạm Trạm thu phí Pháp Vân - Cầu Giẽ','DEDUCT',84,12),(89,30000.00,10000.00,'2025-11-23 09:33:36.470471','Trừ phí qua trạm Trạm thu phí Pháp Vân - Cầu Giẽ','DEDUCT',85,12),(90,30000.00,30000.00,'2025-11-26 09:57:31.154372','Trừ phí qua trạm Trạm thu phí Cầu Rạch Miễu','DEDUCT',90,12),(91,30000.00,0.00,'2025-11-26 09:58:12.194372','Trừ phí qua trạm Trạm thu phí Cao tốc Hà Nội - Hải Phòng','DEDUCT',91,12),(92,30000.00,20000.00,'2025-11-26 10:40:08.801515','Trừ phí qua trạm Trạm thu phí Cầu Cần Thơ','DEDUCT',98,12);
/*!40000 ALTER TABLE `wallet_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallets`
--

DROP TABLE IF EXISTS `wallets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_blocked` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsswfdl9fq40xlkove1y5kc7kv` (`user_id`),
  CONSTRAINT `FKc1foyisidw7wqqrkamafuwn4e` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallets`
--

LOCK TABLES `wallets` WRITE;
/*!40000 ALTER TABLE `wallets` DISABLE KEYS */;
INSERT INTO `wallets` VALUES (2,120000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-04 11:20:00.000000',2),(3,80000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-05 09:10:00.000000',3),(4,250000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-06 13:45:00.000000',4),(5,150000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-07 15:30:00.000000',5),(6,200000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-08 16:20:00.000000',6),(7,50000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-09 17:00:00.000000',7),(8,300000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-10 08:30:00.000000',8),(9,100000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-11 09:55:00.000000',9),(10,400000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-12 10:40:00.000000',10),(11,350000.00,'2025-01-01 08:00:00.000000',_binary '\0','2025-01-13 11:00:00.000000',11),(12,20000.00,'2025-11-22 23:50:36.251934',_binary '\0','2025-11-26 10:40:08.771987',12);
/*!40000 ALTER TABLE `wallets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26 19:04:13


ALTER TABLE vehicles
ADD COLUMN is_delete TINYINT(1) NOT NULL DEFAULT 0;

UPDATE vehicles
SET is_delete = CASE
    WHEN status = 'INACTIVE' THEN 1
    ELSE 0
END
WHERE id > 0;



ALTER TABLE stations
ADD COLUMN is_delete TINYINT(1) NOT NULL DEFAULT 0;


UPDATE stations
SET is_delete = 0
WHERE id > 0;
