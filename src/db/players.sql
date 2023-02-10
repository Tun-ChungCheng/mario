-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 10, 2023 at 08:34 AM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mario`
--

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `score` bigint(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `name`, `account`, `password`, `score`) VALUES
(22, 'Brad', 'Brad', '$2a$10$QMdOfNVsQSGZ9xPBAdATKu6KzjUF.u9wBuHzGHPNIz6OvZR6Pxa6G', 0),
(23, 'test', 'test', '$2a$10$wKbDMr3PEK/hcCNtRAU6WO9G0loJh0H7vAGXtROI1ewsQzyFieZxa', 0),
(25, 'ted', 'ted', '$2a$10$kBCjpuClBq5zD0DQ2LJFuuD1e0THO9602sv1ZrFPnaXZMMSdmEebO', 0),
(32, 'test1', 'test1', '$2a$10$h5yM96r8qvT4Y7494nOWsODdzht0jcOJM85rom1SEQcvsfChXD/Pq', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
