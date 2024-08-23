-- Create the database
CREATE DATABASE IF NOT EXISTS `medicine`;
USE `medicine`;

-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `generic_name` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `quantity_in_stock` int DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `medicine_id` int NOT NULL,
  `quantity` int NOT NULL,
  `date_received` date DEFAULT NULL,
  `batch_number` varchar(50) DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `medicine_id` (`medicine_id`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` (`id`, `name`, `generic_name`, `manufacturer`, `price`, `expiry_date`, `quantity_in_stock`, `description`) VALUES 
(1, 'Paracetamol', 'Paracetamol', 'Pharma Supplies Ltd.', 25.00, '2025-12-31', 100, 'Used for pain relief and fever reduction'),
(2, 'Aspirin', 'Acetylsalicylic Acid', 'MedEquip Pvt Ltd.', 15.00, '2024-11-30', 200, 'Used for pain relief and anti-inflammatory'),
(3, 'Vitamin C', 'Ascorbic Acid', 'Pharma Supplies Ltd.', 10.00, '2026-05-31', 150, 'Used to prevent vitamin C deficiency');
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` (`id`, `medicine_id`, `quantity`, `date_received`, `batch_number`, `expiry_date`) VALUES 
(1, 1, 50, '2024-08-23', 'BATCH001', '2025-12-31'),
(2, 2, 100, '2024-08-23', 'BATCH002', '2024-11-30'),
(3, 3, 75, '2024-08-23', 'BATCH003', '2026-05-31');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;
