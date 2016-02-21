-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 13. Jan 2016 um 13:42
-- Server-Version: 10.1.8-MariaDB
-- PHP-Version: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `moaiosbeer`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `game_v1_01`
--

CREATE TABLE `game_v1_01` (
  `game_id` bigint(20) NOT NULL,
  `currentround` int(11) DEFAULT NULL,
  `gamename` varchar(255) DEFAULT NULL,
  `maxrounds` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `game_v1_01`
--

INSERT INTO `game_v1_01` (`game_id`, `currentround`, `gamename`, `maxrounds`) VALUES
(1, 0, 'The Game', 0),
(2, 0, 'BeerGame33', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `game_v1_01_pk`
--

CREATE TABLE `game_v1_01_pk` (
  `Game_V1_01_ID` varchar(255) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `game_v1_01_pk`
--

INSERT INTO `game_v1_01_pk` (`Game_V1_01_ID`, `next_val`) VALUES
('Game_V1_01_Value', 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `game_v1_01_playsheet_v1_01`
--

CREATE TABLE `game_v1_01_playsheet_v1_01` (
  `Game_V1_01_game_id` bigint(20) NOT NULL,
  `playsheets_playsheetid` bigint(20) NOT NULL,
  `playsheets_round` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `game_v1_01_playsheet_v1_01`
--

INSERT INTO `game_v1_01_playsheet_v1_01` (`Game_V1_01_game_id`, `playsheets_playsheetid`, `playsheets_round`) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 1),
(2, 4, 1),
(2, 5, 1),
(2, 6, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `game_v1_01_user_v1_01`
--

CREATE TABLE `game_v1_01_user_v1_01` (
  `Game_V1_01_game_id` bigint(20) NOT NULL,
  `userlist_user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `game_v1_01_user_v1_01`
--

INSERT INTO `game_v1_01_user_v1_01` (`Game_V1_01_game_id`, `userlist_user_id`) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6);

-- --------------------------------------------------------

--
-- Stellvertreter-Struktur des Views `groupview`
--
CREATE TABLE `groupview` (
`username` varchar(255)
,`rolename` varchar(255)
);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `playsheet_v1_01`
--

CREATE TABLE `playsheet_v1_01` (
  `playsheetid` bigint(20) NOT NULL,
  `round` int(11) NOT NULL,
  `backorder` int(11) DEFAULT NULL,
  `delivery` int(11) DEFAULT NULL,
  `gamerole` varchar(255) DEFAULT NULL,
  `incoming` int(11) DEFAULT NULL,
  `inventory` int(11) DEFAULT NULL,
  `neworder` int(11) DEFAULT NULL,
  `ourorder` int(11) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `totalcosts` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `playsheet_v1_01`
--

INSERT INTO `playsheet_v1_01` (`playsheetid`, `round`, `backorder`, `delivery`, `gamerole`, `incoming`, `inventory`, `neworder`, `ourorder`, `owner_id`, `totalcosts`) VALUES
(1, 1, 0, 0, 'Wholesaler', 0, 15, 0, 0, 1, 0),
(1, 2, 0, 0, 'Wholesaler', 5, 15, 0, 0, 1, 0),
(1, 3, 0, 0, 'Wholesaler', 6, 15, 0, 0, 1, 0),
(1, 4, 0, 0, 'Wholesaler', 7, 15, 0, 0, 1, 0),
(1, 5, 0, 0, 'Wholesaler', 8, 15, 0, 0, 1, 0),
(1, 6, 0, 0, 'Wholesaler', 8, 15, 0, 0, 1, 0),
(1, 7, 0, 0, 'Wholesaler', 5, 15, 0, 0, 1, 0),
(1, 8, 0, 0, 'Wholesaler', 4, 15, 0, 0, 1, 0),
(1, 9, 0, 0, 'Wholesaler', 30, 15, 0, 0, 1, 0),
(1, 10, 0, 0, 'Wholesaler', 30, 12, 0, 0, 1, 0),
(1, 11, 0, 0, 'Wholesaler', 30, 5, 0, 0, 1, 0),
(1, 12, 0, 0, 'Wholesaler', 30, 8, 0, 0, 1, 0),
(1, 13, 0, 0, 'Wholesaler', 30, 20, 0, 0, 1, 0),
(2, 1, 0, 0, 'Distributor', 0, 15, 0, 0, 2, 0),
(3, 1, 0, 0, 'Wholesaler', 0, 15, 0, 0, 3, 0),
(4, 1, 0, 0, 'Distributor', 0, 15, 0, 0, 4, 0),
(5, 1, 0, 0, 'Retailer', 0, 15, 0, 0, 5, 0),
(6, 1, 0, 0, 'Factory', 0, 15, 0, 0, 6, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `playsheet_v1_01_game_v1_01`
--

CREATE TABLE `playsheet_v1_01_game_v1_01` (
  `Playsheet_V1_01_playsheetid` bigint(20) NOT NULL,
  `Playsheet_V1_01_round` int(11) NOT NULL,
  `gamelist_game_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tomcatuserroles_v1_01`
--

CREATE TABLE `tomcatuserroles_v1_01` (
  `role_id` bigint(20) NOT NULL,
  `rolename` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `tomcatuserroles_v1_01`
--

INSERT INTO `tomcatuserroles_v1_01` (`role_id`, `rolename`) VALUES
(1, 'manager-gui'),
(2, 'admin'),
(3, 'player'),
(4, 'admin');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tomcatuserroles_v1_01_pk`
--

CREATE TABLE `tomcatuserroles_v1_01_pk` (
  `TomcatUserRoles_V1_01_ID` varchar(255) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `tomcatuserroles_v1_01_pk`
--

INSERT INTO `tomcatuserroles_v1_01_pk` (`TomcatUserRoles_V1_01_ID`, `next_val`) VALUES
('TomcatUserRoles_V1_01_Value', 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_v1_01`
--

CREATE TABLE `user_v1_01` (
  `user_id` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user_v1_01`
--

INSERT INTO `user_v1_01` (`user_id`, `password`, `username`) VALUES
(1, '1234', 'Ich'),
(2, '1234', 'Heinrich'),
(3, '1234', 'Ich2'),
(4, '1234', 'Heinrich2'),
(5, '1234', 'Tim'),
(6, '1234', 'JonFire');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_v1_01_game_v1_01`
--

CREATE TABLE `user_v1_01_game_v1_01` (
  `User_V1_01_user_id` bigint(20) NOT NULL,
  `gamelist_game_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_v1_01_pk`
--

CREATE TABLE `user_v1_01_pk` (
  `User_V1_01_ID` varchar(255) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user_v1_01_pk`
--

INSERT INTO `user_v1_01_pk` (`User_V1_01_ID`, `next_val`) VALUES
('User_V1_01_Value', 7);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user_v1_01_tomcatuserroles_v1_01`
--

CREATE TABLE `user_v1_01_tomcatuserroles_v1_01` (
  `User_V1_01_user_id` bigint(20) NOT NULL,
  `roles_role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user_v1_01_tomcatuserroles_v1_01`
--

INSERT INTO `user_v1_01_tomcatuserroles_v1_01` (`User_V1_01_user_id`, `roles_role_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 3),
(6, 4);

-- --------------------------------------------------------

--
-- Struktur des Views `groupview`
--
DROP TABLE IF EXISTS `groupview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `groupview`  AS  select `u`.`username` AS `username`,`tr`.`rolename` AS `rolename` from ((`user_v1_01_tomcatuserroles_v1_01` `tu` join `user_v1_01` `u`) join `tomcatuserroles_v1_01` `tr`) where ((`u`.`user_id` = `tu`.`User_V1_01_user_id`) and (`tr`.`role_id` = `tu`.`roles_role_id`)) ;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `game_v1_01`
--
ALTER TABLE `game_v1_01`
  ADD PRIMARY KEY (`game_id`);

--
-- Indizes für die Tabelle `game_v1_01_pk`
--
ALTER TABLE `game_v1_01_pk`
  ADD PRIMARY KEY (`Game_V1_01_ID`);

--
-- Indizes für die Tabelle `game_v1_01_playsheet_v1_01`
--
ALTER TABLE `game_v1_01_playsheet_v1_01`
  ADD KEY `FKat4j88mhdvmyjroak6vkf5j2g` (`playsheets_playsheetid`,`playsheets_round`),
  ADD KEY `FKdd1re129j1x3pqr4fldsysrmn` (`Game_V1_01_game_id`);

--
-- Indizes für die Tabelle `game_v1_01_user_v1_01`
--
ALTER TABLE `game_v1_01_user_v1_01`
  ADD KEY `FK3c4mlio0ji4l0s82v17hecgs1` (`userlist_user_id`),
  ADD KEY `FKarrpl39u9am2whki4efpttses` (`Game_V1_01_game_id`);

--
-- Indizes für die Tabelle `playsheet_v1_01`
--
ALTER TABLE `playsheet_v1_01`
  ADD PRIMARY KEY (`playsheetid`,`round`);

--
-- Indizes für die Tabelle `playsheet_v1_01_game_v1_01`
--
ALTER TABLE `playsheet_v1_01_game_v1_01`
  ADD KEY `FKlp9og4bc69bl4mh1h1nt3291` (`gamelist_game_id`),
  ADD KEY `FKh8cs5lge4lkrajwvd67jelgyh` (`Playsheet_V1_01_playsheetid`,`Playsheet_V1_01_round`);

--
-- Indizes für die Tabelle `tomcatuserroles_v1_01`
--
ALTER TABLE `tomcatuserroles_v1_01`
  ADD PRIMARY KEY (`role_id`);

--
-- Indizes für die Tabelle `tomcatuserroles_v1_01_pk`
--
ALTER TABLE `tomcatuserroles_v1_01_pk`
  ADD PRIMARY KEY (`TomcatUserRoles_V1_01_ID`);

--
-- Indizes für die Tabelle `user_v1_01`
--
ALTER TABLE `user_v1_01`
  ADD PRIMARY KEY (`user_id`);

--
-- Indizes für die Tabelle `user_v1_01_game_v1_01`
--
ALTER TABLE `user_v1_01_game_v1_01`
  ADD KEY `FKfu9tcwp49niddcgqh98gqa0vv` (`gamelist_game_id`),
  ADD KEY `FKljxrghim1q25hqlfra01pt16l` (`User_V1_01_user_id`);

--
-- Indizes für die Tabelle `user_v1_01_pk`
--
ALTER TABLE `user_v1_01_pk`
  ADD PRIMARY KEY (`User_V1_01_ID`);

--
-- Indizes für die Tabelle `user_v1_01_tomcatuserroles_v1_01`
--
ALTER TABLE `user_v1_01_tomcatuserroles_v1_01`
  ADD KEY `FKjc4v29iuixq3fx6tnw1sx0qyj` (`roles_role_id`),
  ADD KEY `FKqu0u2irlekyuqg8vtxllpu31h` (`User_V1_01_user_id`);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `game_v1_01_playsheet_v1_01`
--
ALTER TABLE `game_v1_01_playsheet_v1_01`
  ADD CONSTRAINT `FKat4j88mhdvmyjroak6vkf5j2g` FOREIGN KEY (`playsheets_playsheetid`,`playsheets_round`) REFERENCES `playsheet_v1_01` (`playsheetid`, `round`),
  ADD CONSTRAINT `FKdd1re129j1x3pqr4fldsysrmn` FOREIGN KEY (`Game_V1_01_game_id`) REFERENCES `game_v1_01` (`game_id`);

--
-- Constraints der Tabelle `game_v1_01_user_v1_01`
--
ALTER TABLE `game_v1_01_user_v1_01`
  ADD CONSTRAINT `FK3c4mlio0ji4l0s82v17hecgs1` FOREIGN KEY (`userlist_user_id`) REFERENCES `user_v1_01` (`user_id`),
  ADD CONSTRAINT `FKarrpl39u9am2whki4efpttses` FOREIGN KEY (`Game_V1_01_game_id`) REFERENCES `game_v1_01` (`game_id`);

--
-- Constraints der Tabelle `playsheet_v1_01_game_v1_01`
--
ALTER TABLE `playsheet_v1_01_game_v1_01`
  ADD CONSTRAINT `FKh8cs5lge4lkrajwvd67jelgyh` FOREIGN KEY (`Playsheet_V1_01_playsheetid`,`Playsheet_V1_01_round`) REFERENCES `playsheet_v1_01` (`playsheetid`, `round`),
  ADD CONSTRAINT `FKlp9og4bc69bl4mh1h1nt3291` FOREIGN KEY (`gamelist_game_id`) REFERENCES `game_v1_01` (`game_id`);

--
-- Constraints der Tabelle `user_v1_01_game_v1_01`
--
ALTER TABLE `user_v1_01_game_v1_01`
  ADD CONSTRAINT `FKfu9tcwp49niddcgqh98gqa0vv` FOREIGN KEY (`gamelist_game_id`) REFERENCES `game_v1_01` (`game_id`),
  ADD CONSTRAINT `FKljxrghim1q25hqlfra01pt16l` FOREIGN KEY (`User_V1_01_user_id`) REFERENCES `user_v1_01` (`user_id`);

--
-- Constraints der Tabelle `user_v1_01_tomcatuserroles_v1_01`
--
ALTER TABLE `user_v1_01_tomcatuserroles_v1_01`
  ADD CONSTRAINT `FKjc4v29iuixq3fx6tnw1sx0qyj` FOREIGN KEY (`roles_role_id`) REFERENCES `tomcatuserroles_v1_01` (`role_id`),
  ADD CONSTRAINT `FKqu0u2irlekyuqg8vtxllpu31h` FOREIGN KEY (`User_V1_01_user_id`) REFERENCES `user_v1_01` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
