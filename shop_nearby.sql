-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 10 jan. 2019 à 21:30
-- Version du serveur :  5.7.17
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `shop_nearby`
--

-- --------------------------------------------------------

--
-- Structure de la table `relation`
--

CREATE TABLE `relation` (
  `ID` bigint(20) NOT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `SHOP_ID` bigint(20) DEFAULT NULL,
  `USER_EMAIL` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `relation`
--

INSERT INTO `relation` (`ID`, `TYPE`, `SHOP_ID`, `USER_EMAIL`) VALUES
(81, 2, 5, 'fakhita@gmail.com'),
(80, 2, 6, 'fakhita@gmail.com'),
(76, 1, 8, 'fakhita@gmail.com'),
(67, 2, 4, 'fakhita@gmail.com'),
(77, 1, 2, 'fakhita@gmail.com'),
(65, 2, 7, 'fakhita@gmail.com'),
(79, 1, 1, 'fakhita@gmail.com'),
(78, 1, 3, 'fakhita@gmail.com'),
(61, 2, 5, 'fatima@gmail.com'),
(60, 1, 6, 'fatima@gmail.com'),
(59, 1, 7, 'fatima@gmail.com'),
(58, 2, 4, 'fatima@gmail.com'),
(57, 2, 3, 'fatima@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `shop`
--

CREATE TABLE `shop` (
  `ID` bigint(20) NOT NULL,
  `CITY` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `shop`
--

INSERT INTO `shop` (`ID`, `CITY`, `NAME`) VALUES
(1, 'Rabat', 'first shop'),
(2, 'Casa Blanca', 'Second shop'),
(3, 'Marrakech', '3th shop'),
(4, 'Marrakech', '4th shop'),
(5, 'Rabat', '5th shop'),
(6, 'Tetouan', '6th shop'),
(7, 'Tetouan', '7th shop'),
(8, 'Taroudant', '8th shop');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `EMAIL` varchar(255) NOT NULL,
  `PASSWRD` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`EMAIL`, `PASSWRD`) VALUES
('fatimaac391@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
('fatima@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
('ghita@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
('fakhita@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `relation`
--
ALTER TABLE `relation`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_RELATION_SHOP_ID` (`SHOP_ID`),
  ADD KEY `FK_RELATION_USER_EMAIL` (`USER_EMAIL`);

--
-- Index pour la table `shop`
--
ALTER TABLE `shop`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`EMAIL`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `relation`
--
ALTER TABLE `relation`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;
--
-- AUTO_INCREMENT pour la table `shop`
--
ALTER TABLE `shop`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
