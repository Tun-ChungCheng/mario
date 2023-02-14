-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 14, 2023 at 02:11 AM
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
(50, 'Apple', 'Apple', '$2a$10$/ohVpvT5vKmVCSeEVI0CauOJLFS1qFdw.VLAZbqoVdC4gWhi2pOpO', 1500),
(51, 'Banana', 'Banana', '$2a$10$e1ojpSYG5xhmm4zQ5rzcguMvaov1T8rHANxlXzgdUbHKfb9YXcjuO', 9700),
(52, 'Orange', 'Orange', '$2a$10$FjUgWQ17BTNufo9/AWxwv.p9khB1QaV0RMRf7p312vZdDqGcr7k1a', 6600),
(53, 'Peach', 'Peach', '$2a$10$NzJ1qRhmW7Gb.nu3rS1E6.mJZ5aJ.VTMJNwMRrGeT59YXFYjppERS', 5200),
(54, 'Mango', 'Mango', '$2a$10$DrbPc81TW9VvZhhrhXJp..AVKKnHW1c6wdEjXTC1E/M2qtJJfT4N6', 5700);

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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
