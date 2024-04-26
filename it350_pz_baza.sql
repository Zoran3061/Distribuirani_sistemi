-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 24, 2019 at 02:18 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `it350_pz`
--
CREATE DATABASE IF NOT EXISTS `it350_pz` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `it350_pz`;

-- --------------------------------------------------------

--
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
CREATE TABLE IF NOT EXISTS `album` (
  `ALBUM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAZIV_ALBUMA` varchar(255) DEFAULT NULL,
  `DATUM_IZDAVANJA_ALBIMA` date DEFAULT NULL,
  PRIMARY KEY (`ALBUM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `album`
--

INSERT INTO `album` (`ALBUM_ID`, `NAZIV_ALBUMA`, `DATUM_IZDAVANJA_ALBIMA`) VALUES
(1, 'Lemonade', '2019-01-05'),
(2, 'Unpologetic', '2019-01-04'),
(3, 'X', '2018-10-12'),
(4, 'The 20/20 expirience', '2019-04-01'),
(5, 'The life of Pablo', '2019-01-01'),
(6, 'Labilna', '2019-01-11'),
(7, 'Balkanska pravila', '2019-03-13'),
(8, 'Dva su koraka', '2019-05-03'),
(9, 'Original', '2018-05-04'),
(10, 'Stil zivota', '2018-02-12');

-- --------------------------------------------------------

--
-- Table structure for table `humanitarni_koncert`
--

DROP TABLE IF EXISTS `humanitarni_koncert`;
CREATE TABLE IF NOT EXISTS `humanitarni_koncert` (
  `HUMANITARNI_ID` int(11) NOT NULL AUTO_INCREMENT,
  `KONCERT_ID` int(11) DEFAULT NULL,
  `NAZIV_USTANOVE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`HUMANITARNI_ID`),
  KEY `FK_RELATIONSHIP_8` (`KONCERT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `humanitarni_koncert`
--

INSERT INTO `humanitarni_koncert` (`HUMANITARNI_ID`, `KONCERT_ID`, `NAZIV_USTANOVE`) VALUES
(1, 1, 'Laza Lazarevic'),
(2, 2, 'Decija klinika'),
(3, 3, 'Tirsova'),
(4, 4, 'Institut za majku i dete'),
(5, 5, 'Zvezanska'),
(6, 6, 'Dom za stare Miljakovac'),
(7, 7, 'Porodiliste Beograd'),
(8, 9, 'Zdravstvo'),
(9, 10, 'Nurdor ustanova\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `izvodjac`
--

DROP TABLE IF EXISTS `izvodjac`;
CREATE TABLE IF NOT EXISTS `izvodjac` (
  `IZVODJAC_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAZIV` varchar(128) DEFAULT NULL,
  `KONTAKT_TELEFON` varchar(128) DEFAULT NULL,
  `MAIL_` varchar(255) DEFAULT NULL,
  `WEB_ADRESA` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IZVODJAC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `izvodjac`
--

INSERT INTO `izvodjac` (`IZVODJAC_ID`, `NAZIV`, `KONTAKT_TELEFON`, `MAIL_`, `WEB_ADRESA`) VALUES
(1, 'Beyonce', '123456', 'beyonce@gmail.com', 'www.beyonce.com'),
(2, 'Rihanna', '654321', 'rihanna@gmail.com', 'www.rihanna.com'),
(3, 'Ed Sheeran', '999999', 'edsheeran@gmail.com', 'www.edsheeran.com'),
(4, 'Justin timberlake', '888888', 'jt@gmail.com', 'www.jt.com'),
(5, 'Kanye West', '777777', 'kw@gmail.com', 'www.kw.com'),
(6, 'Ana Nikolic', '444444', 'an@gmail.com', 'www.an.com'),
(7, 'Lexington Band', '555555', 'lb@gmail.com', 'www.lb.com'),
(8, 'Dzenan Loncarevic', '111111', 'dl@mail.com', 'www.dl.com'),
(9, 'Natasa Bekvalac', '232323', 'nb@gmail.com', 'www.nb.com'),
(10, 'Aca Lukas\r\n', '212121', 'al@gmail.com', 'www.al.com\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `komercijalni_koncert`
--

DROP TABLE IF EXISTS `komercijalni_koncert`;
CREATE TABLE IF NOT EXISTS `komercijalni_koncert` (
  `KOMERCIJALNI_ID` int(11) NOT NULL AUTO_INCREMENT,
  `UGOVOR_ID` int(11) NOT NULL,
  `KONCERT_ID` int(11) DEFAULT NULL,
  `CENA_ULAZNICE` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`KOMERCIJALNI_ID`),
  KEY `FK_RELATIONSHIP_10` (`UGOVOR_ID`),
  KEY `FK_RELATIONSHIP_9` (`KONCERT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `komercijalni_koncert`
--

INSERT INTO `komercijalni_koncert` (`KOMERCIJALNI_ID`, `UGOVOR_ID`, `KONCERT_ID`, `CENA_ULAZNICE`) VALUES
(6, 11, 1, '250'),
(7, 13, 6, '3600'),
(8, 15, 2, '600'),
(9, 17, 5, '1500'),
(10, 19, 2, '3500');

-- --------------------------------------------------------

--
-- Table structure for table `koncert`
--

DROP TABLE IF EXISTS `koncert`;
CREATE TABLE IF NOT EXISTS `koncert` (
  `KONCERT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAZIV_KONCERTA` varchar(255) DEFAULT NULL,
  `DATUM_KONCERTA` datetime DEFAULT NULL,
  `LOKACIJA_KONCERTA` varchar(255) DEFAULT NULL,
  `BROJ_PRODATIH_KARATA` int(11) DEFAULT NULL,
  PRIMARY KEY (`KONCERT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `koncert`
--

INSERT INTO `koncert` (`KONCERT_ID`, `NAZIV_KONCERTA`, `DATUM_KONCERTA`, `LOKACIJA_KONCERTA`, `BROJ_PRODATIH_KARATA`) VALUES
(1, 'Yonce', '2019-04-19 20:00:00', 'Beograd', 2001),
(2, 'Deca Beograda', '2019-01-31 21:00:00', 'Beograd', 1000),
(3, 'Poznati za vas', '2019-08-04 22:00:00', 'Beograd', 900),
(4, 'Novogodisnji koncert', '2018-12-31 23:30:13', 'Beograd', 100),
(5, 'Ed Sheeran X', '2019-03-05 00:00:00', 'Beograd', 2500),
(6, 'Napustena deca', '2019-08-01 00:00:00', 'Beograd', 1050),
(7, 'PabloPabloPablo', '2019-06-02 21:00:00', 'Beograd', 2500),
(8, 'Originalna Bekvalceva', '2019-09-12 20:00:00', 'Beograd', 1500),
(9, 'Work', '2019-05-05 00:00:00', 'Beograd', 3500),
(10, 'Deca sa posebnim potrebama', '2019-02-02 00:00:00', 'Beograd', 500);

-- --------------------------------------------------------

--
-- Table structure for table `menadzer`
--

DROP TABLE IF EXISTS `menadzer`;
CREATE TABLE IF NOT EXISTS `menadzer` (
  `MENADZER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `IME` varchar(128) DEFAULT NULL,
  `PREZIME` varchar(128) DEFAULT NULL,
  `TELEFON` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`MENADZER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menadzer`
--

INSERT INTO `menadzer` (`MENADZER_ID`, `IME`, `PREZIME`, `TELEFON`) VALUES
(1, 'Sara', 'Davidovic', '123456'),
(2, 'Milos', 'Ilic', '654321'),
(3, 'Ljiljana', 'Lukic', '121212'),
(4, 'Nemanja', 'Andric', '987654'),
(5, 'Nikola', 'Sudimac', '141414'),
(6, 'Ilija', 'Taleski', '323232'),
(7, 'Tanja', 'Marijanovic', '101010');

-- --------------------------------------------------------

--
-- Table structure for table `pesma`
--

DROP TABLE IF EXISTS `pesma`;
CREATE TABLE IF NOT EXISTS `pesma` (
  `PESMA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ALBUM_ID` int(11) DEFAULT NULL,
  `NAZIV_PESME` varchar(255) DEFAULT NULL,
  `TRAJANJE_PESME` varchar(16) DEFAULT NULL,
  `REDNI_BROJ_PESME` int(11) DEFAULT NULL,
  PRIMARY KEY (`PESMA_ID`),
  KEY `FK_RELATIONSHIP_3` (`ALBUM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pesma`
--

INSERT INTO `pesma` (`PESMA_ID`, `ALBUM_ID`, `NAZIV_PESME`, `TRAJANJE_PESME`, `REDNI_BROJ_PESME`) VALUES
(1, 1, 'Formation', '00:02:58', 1),
(2, 2, 'Pour it up', '00:03:25', 3),
(3, 3, 'Thinking out loud', '00:05:30', 7),
(4, 4, 'Suit & Tie', '00:04:32', 5),
(5, 5, 'Fade', '00:04:23', 5),
(6, 6, 'Frigidna', '00:07:00', 2),
(7, 7, 'Potrazi me', '00:04:31', 6),
(8, 8, 'Pamuk usne', '00:06:00', 6),
(9, 9, 'Ludilo', '00:04:00', 6),
(10, 10, 'Volis li me', '00:03:49', 3),
(11, 1, 'Umbrella', '00:05:02', 1),
(12, 2, 'TKO', '00:05:06', 3),
(13, 3, 'Gold digger', '00:05:06', 9),
(14, 4, '200/100', '00:03:48', 4),
(15, 5, 'Sexy back', '00:06:00', 7),
(16, 6, 'Vreme za ljubav', '00:07:00', 6),
(17, 7, 'Ljubav preko zice', '00:03:29', 5),
(18, 8, 'Deja vu', '00:03:45', 12),
(19, 7, 'Neka ljubav', '00:03:00', 1);

-- --------------------------------------------------------

--
-- Stand-in structure for view `podaciokoncertima`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `podaciokoncertima`;
CREATE TABLE IF NOT EXISTS `podaciokoncertima` (
`KONCERT_ID` int(11)
,`NAZIV_KONCERTA` varchar(255)
,`DATUM_KONCERTA` datetime
,`LOKACIJA_KONCERTA` varchar(255)
,`BROJ_PRODATIH_KARATA` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `snimio`
--

DROP TABLE IF EXISTS `snimio`;
CREATE TABLE IF NOT EXISTS `snimio` (
  `SNIMIO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ALBUM_ID` int(11) NOT NULL,
  `IZVODJAC_ID` int(11) NOT NULL,
  `BROJ_SATI` int(11) DEFAULT NULL,
  PRIMARY KEY (`SNIMIO_ID`),
  KEY `FK_RELATIONSHIP_1` (`IZVODJAC_ID`),
  KEY `FK_RELATIONSHIP_2` (`ALBUM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `snimio`
--

INSERT INTO `snimio` (`SNIMIO_ID`, `ALBUM_ID`, `IZVODJAC_ID`, `BROJ_SATI`) VALUES
(1, 1, 1, 20),
(2, 2, 2, 21),
(3, 3, 3, 30),
(4, 4, 4, 45),
(5, 6, 6, 20),
(6, 7, 7, 25),
(7, 8, 8, 35),
(8, 9, 9, 22),
(9, 10, 10, 33);

-- --------------------------------------------------------

--
-- Table structure for table `spisak_pesama`
--

DROP TABLE IF EXISTS `spisak_pesama`;
CREATE TABLE IF NOT EXISTS `spisak_pesama` (
  `SPISAK_PESAMA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TERMIN_ID` int(11) DEFAULT NULL,
  `PESMA_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`SPISAK_PESAMA_ID`),
  KEY `FK_RELATIONSHIP_11` (`TERMIN_ID`),
  KEY `FK_RELATIONSHIP_12` (`PESMA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `spisak_pesama`
--

INSERT INTO `spisak_pesama` (`SPISAK_PESAMA_ID`, `TERMIN_ID`, `PESMA_ID`) VALUES
(1, 1, 1),
(2, 2, 4),
(3, 3, 6),
(4, 4, 7),
(5, 5, 3),
(6, 6, 8),
(7, 7, 5),
(8, 8, 9),
(9, 9, 2),
(10, 10, 10),
(11, 11, 11),
(12, 12, 1),
(13, 13, 3),
(14, 14, 12),
(15, 15, 13),
(16, 16, 14),
(17, 3, 16);

-- --------------------------------------------------------

--
-- Table structure for table `termin`
--

DROP TABLE IF EXISTS `termin`;
CREATE TABLE IF NOT EXISTS `termin` (
  `TERMIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `KONCERT_ID` int(11) NOT NULL,
  `IZVODJAC_ID` int(11) NOT NULL,
  `VREME_IZVODJENJA` time DEFAULT NULL,
  PRIMARY KEY (`TERMIN_ID`),
  KEY `FK_RELATIONSHIP_6` (`KONCERT_ID`),
  KEY `FK_RELATIONSHIP_7` (`IZVODJAC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `termin`
--

INSERT INTO `termin` (`TERMIN_ID`, `KONCERT_ID`, `IZVODJAC_ID`, `VREME_IZVODJENJA`) VALUES
(1, 1, 1, '21:00:00'),
(2, 2, 4, '15:00:00'),
(3, 3, 6, '20:00:00'),
(4, 4, 7, '17:00:00'),
(5, 5, 3, '21:00:00'),
(6, 6, 8, '16:30:00'),
(7, 7, 5, '22:00:00'),
(8, 8, 9, '21:00:00'),
(9, 9, 2, '20:00:00'),
(10, 10, 10, '19:00:00'),
(11, 3, 2, '23:00:00'),
(12, 2, 1, '16:00:00'),
(13, 3, 3, '21:00:00'),
(14, 3, 4, '22:00:00'),
(15, 3, 5, '23:00:00'),
(16, 3, 6, '23:59:00');

-- --------------------------------------------------------

--
-- Table structure for table `ugovor`
--

DROP TABLE IF EXISTS `ugovor`;
CREATE TABLE IF NOT EXISTS `ugovor` (
  `UGOVOR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `IZVODJAC_ID` int(11) NOT NULL,
  `MENADZER_ID` int(11) NOT NULL,
  `BROJ_UGOVORA` varchar(255) DEFAULT NULL,
  `PROCENAT` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`UGOVOR_ID`),
  KEY `FK_RELATIONSHIP_4` (`IZVODJAC_ID`),
  KEY `FK_RELATIONSHIP_5` (`MENADZER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ugovor`
--

INSERT INTO `ugovor` (`UGOVOR_ID`, `IZVODJAC_ID`, `MENADZER_ID`, `BROJ_UGOVORA`, `PROCENAT`) VALUES
(11, 1, 1, '1', '20'),
(12, 2, 2, '1', '25'),
(13, 3, 3, '2', '10'),
(14, 4, 4, '4', '14'),
(15, 5, 5, '3', '19'),
(16, 6, 6, '4', '25'),
(17, 7, 7, '5', '28'),
(18, 8, 3, '6', '19'),
(19, 9, 4, '7', '36'),
(20, 10, 5, '1', '30');

-- --------------------------------------------------------

--
-- Structure for view `podaciokoncertima`
--
DROP TABLE IF EXISTS `podaciokoncertima`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `podaciokoncertima`  AS  select `koncert`.`KONCERT_ID` AS `KONCERT_ID`,`koncert`.`NAZIV_KONCERTA` AS `NAZIV_KONCERTA`,`koncert`.`DATUM_KONCERTA` AS `DATUM_KONCERTA`,`koncert`.`LOKACIJA_KONCERTA` AS `LOKACIJA_KONCERTA`,`koncert`.`BROJ_PRODATIH_KARATA` AS `BROJ_PRODATIH_KARATA` from ((`koncert` join `termin` on((`termin`.`KONCERT_ID` = `koncert`.`KONCERT_ID`))) join `spisak_pesama` on((`spisak_pesama`.`TERMIN_ID` = `termin`.`TERMIN_ID`))) where ((`koncert`.`DATUM_KONCERTA` between '2019-01-01' and '2019-11-31') and `koncert`.`KONCERT_ID` in (select `koncert`.`KONCERT_ID` from (`koncert` join `termin` on((`termin`.`KONCERT_ID` = `koncert`.`KONCERT_ID`))) group by `koncert`.`KONCERT_ID` having (count(`termin`.`IZVODJAC_ID`) > 3))) group by `koncert`.`KONCERT_ID`,`koncert`.`NAZIV_KONCERTA`,`koncert`.`DATUM_KONCERTA`,`koncert`.`LOKACIJA_KONCERTA`,`koncert`.`BROJ_PRODATIH_KARATA` having (count(`spisak_pesama`.`PESMA_ID`) >= 5) ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `humanitarni_koncert`
--
ALTER TABLE `humanitarni_koncert`
  ADD CONSTRAINT `FK_RELATIONSHIP_8` FOREIGN KEY (`KONCERT_ID`) REFERENCES `koncert` (`KONCERT_ID`);

--
-- Constraints for table `komercijalni_koncert`
--
ALTER TABLE `komercijalni_koncert`
  ADD CONSTRAINT `FK_RELATIONSHIP_10` FOREIGN KEY (`UGOVOR_ID`) REFERENCES `ugovor` (`UGOVOR_ID`),
  ADD CONSTRAINT `FK_RELATIONSHIP_9` FOREIGN KEY (`KONCERT_ID`) REFERENCES `koncert` (`KONCERT_ID`);

--
-- Constraints for table `pesma`
--
ALTER TABLE `pesma`
  ADD CONSTRAINT `FK_RELATIONSHIP_3` FOREIGN KEY (`ALBUM_ID`) REFERENCES `album` (`ALBUM_ID`);

--
-- Constraints for table `snimio`
--
ALTER TABLE `snimio`
  ADD CONSTRAINT `FK_RELATIONSHIP_1` FOREIGN KEY (`IZVODJAC_ID`) REFERENCES `izvodjac` (`IZVODJAC_ID`),
  ADD CONSTRAINT `FK_RELATIONSHIP_2` FOREIGN KEY (`ALBUM_ID`) REFERENCES `album` (`ALBUM_ID`);

--
-- Constraints for table `spisak_pesama`
--
ALTER TABLE `spisak_pesama`
  ADD CONSTRAINT `FK_RELATIONSHIP_11` FOREIGN KEY (`TERMIN_ID`) REFERENCES `termin` (`TERMIN_ID`),
  ADD CONSTRAINT `FK_RELATIONSHIP_12` FOREIGN KEY (`PESMA_ID`) REFERENCES `pesma` (`PESMA_ID`);

--
-- Constraints for table `termin`
--
ALTER TABLE `termin`
  ADD CONSTRAINT `FK_RELATIONSHIP_6` FOREIGN KEY (`KONCERT_ID`) REFERENCES `koncert` (`KONCERT_ID`),
  ADD CONSTRAINT `FK_RELATIONSHIP_7` FOREIGN KEY (`IZVODJAC_ID`) REFERENCES `izvodjac` (`IZVODJAC_ID`);

--
-- Constraints for table `ugovor`
--
ALTER TABLE `ugovor`
  ADD CONSTRAINT `FK_RELATIONSHIP_4` FOREIGN KEY (`IZVODJAC_ID`) REFERENCES `izvodjac` (`IZVODJAC_ID`),
  ADD CONSTRAINT `FK_RELATIONSHIP_5` FOREIGN KEY (`MENADZER_ID`) REFERENCES `menadzer` (`MENADZER_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
