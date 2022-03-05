-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2022 at 04:12 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foms`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers_details`
--

CREATE TABLE `customers_details` (
  `Email` varchar(50) NOT NULL,
  `PhoneNo` varchar(100) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customers_feedback`
--

CREATE TABLE `customers_feedback` (
  `Order_Id` int(11) NOT NULL,
  `FeedBack` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `ID` int(11) NOT NULL,
  `Item_Name` varchar(255) DEFAULT NULL,
  `Item_Detail` varchar(255) DEFAULT NULL,
  `Price` decimal(13,2) DEFAULT NULL,
  `Img` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`ID`, `Item_Name`, `Item_Detail`, `Price`, `Img`) VALUES
(1, 'Test', 'Test', '12.00', 'Test.png'),
(2, 'Zinger Burger', 'Crispy Fried To Perfection Boneless Thigh with Signature Sauce and Lettuce Held in Corn-Dusted Bun.', '330.00', 'Zinger Burger.png'),
(3, 'Zinger Supreme', '2 Crispy Fried To Perfection Boneless Thigh with Signature Sauce, Lettuce and A Cheese Slice Held in Corn-Dusted Bun.', '450.00', 'Zinger Supreme.png');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `Order_Id` int(11) NOT NULL,
  `Order_By` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `orders_item`
--

CREATE TABLE `orders_item` (
  `ID` int(11) NOT NULL,
  `Item_Id` int(11) DEFAULT NULL,
  `Price` decimal(13,2) DEFAULT NULL,
  `Order_At` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Pswd` varchar(256) DEFAULT NULL,
  `Type` char(10) DEFAULT NULL,
  `Created_at` datetime DEFAULT NULL,
  `Img` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Name`, `Email`, `Pswd`, `Type`, `Created_at`, `Img`) VALUES
(18, NULL, 'admin', '1234', NULL, NULL, NULL),
(20, 'Jimmy James', 'def@gmail.com', '1234', 'Operator', '2022-03-01 18:54:06', 'Jimmy James.png'),
(21, 'Albert Watson', 'abc@gmail.com', '1234', 'Operator', '2022-03-01 19:02:11', 'Albert Watson.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers_details`
--
ALTER TABLE `customers_details`
  ADD PRIMARY KEY (`Email`);

--
-- Indexes for table `customers_feedback`
--
ALTER TABLE `customers_feedback`
  ADD PRIMARY KEY (`Order_Id`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`Order_Id`),
  ADD KEY `Order_By` (`Order_By`);

--
-- Indexes for table `orders_item`
--
ALTER TABLE `orders_item`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Item_Id` (`Item_Id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `orders_item`
--
ALTER TABLE `orders_item`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customers_details`
--
ALTER TABLE `customers_details`
  ADD CONSTRAINT `customers_details_ibfk_1` FOREIGN KEY (`Email`) REFERENCES `users` (`Email`);

--
-- Constraints for table `customers_feedback`
--
ALTER TABLE `customers_feedback`
  ADD CONSTRAINT `customers_feedback_ibfk_1` FOREIGN KEY (`Order_Id`) REFERENCES `orders_item` (`ID`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`Order_By`) REFERENCES `users` (`Email`);

--
-- Constraints for table `orders_item`
--
ALTER TABLE `orders_item`
  ADD CONSTRAINT `orders_item_ibfk_1` FOREIGN KEY (`Item_Id`) REFERENCES `items` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
