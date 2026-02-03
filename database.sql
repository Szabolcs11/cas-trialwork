-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 16, 2026 at 12:34 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `demo`
--

-- --------------------------------------------------------

--
-- Table structure for table `environments`
--

CREATE TABLE `environments` (
  `Id` int(11) NOT NULL,
  `Name` varchar(64) NOT NULL,
  `Protected` tinyint(1) NOT NULL DEFAULT 0,
  `CreatedAt` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `environments`
--

INSERT INTO `environments` (`Id`, `Name`, `Protected`, `CreatedAt`) VALUES
(1, 'production', 1, '2026-01-16 09:04:22'),
(5, 'staging', 0, '2026-01-16 09:06:55'),
(6, 'test', 0, '2026-01-16 09:44:57');

-- --------------------------------------------------------

--
-- Table structure for table `environment_features`
--

CREATE TABLE `environment_features` (
  `Id` int(11) NOT NULL,
  `EnvironmentId` int(11) NOT NULL,
  `FeatureId` int(11) NOT NULL,
  `Enabled` int(11) NOT NULL DEFAULT 0,
  `LastUpdated` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `environment_features`
--

INSERT INTO `environment_features` (`Id`, `EnvironmentId`, `FeatureId`, `Enabled`, `LastUpdated`) VALUES
(1, 1, 1, 1, NULL),
(3, 1, 3, 1, NULL),
(4, 1, 4, 1, NULL),
(5, 1, 5, 0, NULL),
(6, 1, 6, 0, NULL),
(7, 5, 1, 0, NULL),
(9, 5, 3, 0, NULL),
(10, 5, 4, 1, NULL),
(11, 5, 5, 0, NULL),
(12, 5, 6, 1, NULL),
(15, 6, 1, 1, NULL),
(17, 6, 3, 0, NULL),
(18, 6, 4, 0, NULL),
(19, 6, 5, 1, NULL),
(20, 6, 6, 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `features`
--

CREATE TABLE `features` (
  `Id` int(11) NOT NULL,
  `Identifier` varchar(64) NOT NULL,
  `Name` varchar(64) NOT NULL,
  `Description` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `features`
--

INSERT INTO `features` (`Id`, `Identifier`, `Name`, `Description`) VALUES
(1, 'DARK_MODE', 'Dark Mode', 'Enables a dark-themed user interface to reduce eye strain in low-light environments and improve battery efficiency on supported devices.'),
(3, 'PRIVATE_MESSAGES', 'Private Messages', 'Allows users to send and receive direct, private messages within the platform, ensuring secure and confidential communication.'),
(4, 'PUSH_NOTIFICATIONS', 'Push Notifications', 'Delivers real-time alerts and updates to users’ devices, keeping them informed about important events and activities.'),
(5, 'GEOLOCATION', 'Geolocation', 'Captures and utilizes location data to enable location-based features, services, and personalized experiences.'),
(6, 'SCHEDULED_BACKUP', 'Scheduled Backup', 'Automatically creates backups of system data at predefined intervals to ensure data safety and recovery in case of failures.');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Id` int(11) NOT NULL,
  `Email` varchar(64) NOT NULL,
  `Password` varchar(128) NOT NULL,
  `Admin` tinyint(1) NOT NULL DEFAULT 0,
  `LastLogin` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Id`, `Email`, `Password`, `Admin`, `LastLogin`) VALUES
(1, 'admin@gmail.com', '$2a$12$T5gCSuHe9Ig8jvKtyGDDz.stdIWK0GJiutzEsJZM5NvcbXy6u4GDi', 1, '2026-01-16 12:32:57'),
(9, 'peti@gmail.com', '$2a$12$WInWtz8hoQN/cDPNk5MCp.NqhtbEjroS5FellWOhdZ0TxvCuYU4Ke', 0, '2026-01-16 12:32:03'),
(10, 'szabi@gmail.com', '$2a$12$JkNnxZymZAC.JpNOwO38..jl9tmx0NyKo0bTmXqICkwSdVssLn0Vu', 0, NULL),
(11, 'fejleszto1@gmail.com', '$2a$12$WInWtz8hoQN/cDPNk5MCp.NqhtbEjroS5FellWOhdZ0TxvCuYU4Ke', 0, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `environments`
--
ALTER TABLE `environments`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `ENVIRONMENTS_NAME` (`Name`);

--
-- Indexes for table `environment_features`
--
ALTER TABLE `environment_features`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `ENVIRONMENTFEATURES_ENVIRONMENTID_TO_ENVIRONMENTID` (`EnvironmentId`),
  ADD KEY `ENVIRONMENTFEATURES_FEATUREID_TO_FEATURESID` (`FeatureId`);

--
-- Indexes for table `features`
--
ALTER TABLE `features`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `FEATURES_IDENTIFIER` (`Identifier`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `USERS_EMAIL` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `environments`
--
ALTER TABLE `environments`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `environment_features`
--
ALTER TABLE `environment_features`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `features`
--
ALTER TABLE `features`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `environment_features`
--
ALTER TABLE `environment_features`
  ADD CONSTRAINT `ENVIRONMENTFEATURES_ENVIRONMENTID_TO_ENVIRONMENTID` FOREIGN KEY (`EnvironmentId`) REFERENCES `environments` (`Id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ENVIRONMENTFEATURES_FEATUREID_TO_FEATURESID` FOREIGN KEY (`FeatureId`) REFERENCES `features` (`Id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
