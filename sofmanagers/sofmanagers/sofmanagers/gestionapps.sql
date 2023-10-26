-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 31 juil. 2023 à 13:47
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestionapps`
--

-- --------------------------------------------------------

--
-- Structure de la table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(421);

-- --------------------------------------------------------

--
-- Structure de la table `t_application`
--

CREATE TABLE `t_application` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `etp` float DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `technologie` varchar(255) DEFAULT NULL,
  `domaine_id` bigint(20) DEFAULT NULL,
  `rd_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_application`
--

INSERT INTO `t_application` (`id`, `description`, `etp`, `nom`, `technologie`, `domaine_id`, `rd_id`) VALUES
(1, 'L\'application LP appartient au domaine DEFY et est gérée par l\'équipe E-chat, avec Mr. Patrick Quedeville comme responsable. Sous la direction de Mr. Slim Hsairi en tant que manager.', 1.7, 'LP', 'Django/React', 2, 3),
(2, 'L\'application TAO appartient au domaine Réseau et est gérée par l\'équipe Réseau 5t, avec Mr. Patrick Quedeville comme responsable. Sous la direction de Mr. Slim Hsairi en tant que manager.', 1.4, 'TAO', 'Python', 7, 2),
(3, 'L\'application RST appartient au domaine Réseau et est gérée par l\'équipe Réseau 5t, avec Mr. Patrick Quedeville comme responsable. Sous la direction de Mr. Slim Hsairi en tant que manager.', 2, 'RST', 'Django', 7, 2),
(133, 'L\'application Etask appartient au domaine DEFY et est gérée par l\'équipe E-chat, avec Mr. Patrick Quedeville comme responsable. Sous la direction de Mr. Slim Hsairi en tant que manager.', 1.9, 'Etask', 'Python', 2, 4),
(250, 'L\'application Botman appartient au domaine DEFY et est gérée par l\'équipe E-chat, avec Mr. Patrick Quedeville comme responsable. Sous la direction de Mr. Slim Hsairi en tant que manager.', 1.4, 'BOTMAN', 'Spring Boot', 2, 3),
(408, 'L\'application PPDR appartient au domaine Réseau et est gérée par l\'équipe Réseau 5, avec Mr. Patrick Quedeville comme responsable. Sous la direction de Mr. Slim Hsairi en tant que manager.', 0.6, 'PPDR', 'Java & Angular', 7, 2);

-- --------------------------------------------------------

--
-- Structure de la table `t_das`
--

CREATE TABLE `t_das` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `domainedas_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_das`
--

INSERT INTO `t_das` (`id`, `nom`, `domainedas_id`) VALUES
(1, 'Franck SMEJKAL', 7),
(2, 'QUEDEVILLE Patrick', 2);

-- --------------------------------------------------------

--
-- Structure de la table `t_domaine`
--

CREATE TABLE `t_domaine` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_domaine`
--

INSERT INTO `t_domaine` (`id`, `description`, `nom`) VALUES
(2, 'Description of the current domain', 'DEFY'),
(7, NULL, 'Reseau'),
(238, 'GL domaine is back', 'GL'),
(249, 'twin', 'Twin'),
(328, 'yoo yoo', 'yooo');

-- --------------------------------------------------------

--
-- Structure de la table `t_equipeapp`
--

CREATE TABLE `t_equipeapp` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_equipeapp`
--

INSERT INTO `t_equipeapp` (`id`, `nom`, `application_id`) VALUES
(329, 'E-chat', 1),
(332, 'Reseau 5', 2),
(333, 'Reseau 5', 3),
(334, 'E-chat', 133),
(335, 'E-chat', 250),
(409, 'Reseau 5', 408);

-- --------------------------------------------------------

--
-- Structure de la table `t_etatapp`
--

CREATE TABLE `t_etatapp` (
  `id` bigint(20) NOT NULL,
  `current` bit(1) NOT NULL,
  `dateaffectation_etat` datetime DEFAULT NULL,
  `datedebut_etat` datetime DEFAULT NULL,
  `datefin_etat` datetime DEFAULT NULL,
  `desciption` varchar(255) DEFAULT NULL,
  `etatcourant` varchar(255) DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL,
  `alert` bit(1) NOT NULL,
  `satisfaction` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_etatapp`
--

INSERT INTO `t_etatapp` (`id`, `current`, `dateaffectation_etat`, `datedebut_etat`, `datefin_etat`, `desciption`, `etatcourant`, `application_id`, `alert`, `satisfaction`) VALUES
(3, b'0', '2023-03-27 11:05:18', '2023-03-27 11:05:18', '2023-07-20 10:07:56', ' Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state', 'Sur charge', 1, b'1', 0),
(14, b'0', '2022-01-01 01:00:00', '2022-01-01 01:00:00', '2023-07-20 10:07:56', 'Description of the current state', 'crise', 3, b'1', 0),
(135, b'0', '2023-05-08 01:26:04', '2023-05-08 01:26:04', '2023-05-08 15:23:40', 'Description of the current state', 'Ok', 133, b'0', 80),
(220, b'1', '2023-05-08 23:55:41', '2023-05-20 01:26:00', '2023-05-11 01:41:32', 'La situation sur l\'application ETASK devient de plus en plus critique en raison d\'une surcharge de travail. L\'équipe fait face à des défis majeurs pour gérer cette charge croissante, et il est essentiel de prendre des mesures rapides pour faire face à cet', 'Sur charge', 133, b'1', 0),
(239, b'0', '2023-05-25 03:40:37', '2023-05-25 15:10:00', '2023-05-25 20:23:00', ' Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state', 'in Bad', 1, b'0', 80),
(240, b'0', '2023-05-11 01:41:32', '2023-05-20 01:26:00', '2023-07-20 10:07:56', 'Description of the current state', 'Test', 133, b'0', 40),
(251, b'0', '2023-05-18 04:11:49', '2023-05-18 04:11:49', '2023-06-04 01:38:09', 'Situation En crise...\n', 'En crise', 250, b'1', 0),
(252, b'1', '2023-05-18 04:18:01', '2023-03-27 11:05:00', '2023-07-20 10:07:56', 'L\'application TAO est en un excellent etat.', 'Excellent état', 2, b'0', 100),
(256, b'1', '2023-03-27 11:05:18', '2023-03-27 11:05:18', '2023-06-04 03:24:00', ' Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state Description of the current state', 'En crise', 3, b'1', 0),
(257, b'0', '2023-03-27 11:05:18', '2023-03-27 11:05:18', '2023-06-07 00:37:08', 'L\'application LP est dans un état optimal, faisant preuve de performances remarquables. Avec une satisfaction client qui s\'élève à 80%, elle démontre son efficacité et son engagement à répondre aux besoins de ses utilisateurs.', 'Bon état', 1, b'0', 80),
(375, b'0', '2023-06-15 08:52:39', '2023-05-18 04:11:49', '2023-07-21 07:55:15', 'L\'application est en état moyen, la satisfaction RD reccule petit à petit', 'état moyen', 250, b'0', 40),
(376, b'0', '2023-07-20 11:31:43', '2023-07-20 11:31:43', '2023-07-20 10:07:56', 'La situation est un peu stagnée depuis un temps.', 'Stagnation', 408, b'0', 60),
(419, b'1', '2023-07-20 10:07:56', '2023-07-20 11:31:43', NULL, 'La situation est un peu stagnée depuis un temps. Il faut la revoir.', 'Stagnation', 408, b'0', 60),
(420, b'1', '2023-07-21 07:55:15', '2023-03-27 11:05:18', NULL, 'L\'application LP est dans un état optimal, faisant preuve de performances remarquables. Avec une satisfaction client qui s\'élève à 80%, elle démontre son efficacité et son engagement à répondre aux besoins de ses utilisateurs.', '', 1, b'1', 0);

-- --------------------------------------------------------

--
-- Structure de la table `t_rd`
--

CREATE TABLE `t_rd` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `das_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_rd`
--

INSERT INTO `t_rd` (`id`, `nom`, `das_id`) VALUES
(2, 'Maurice Amar', 1),
(3, 'ETIENNE Alexandre', 2),
(4, 'EL ARBI Nouha', 2);

-- --------------------------------------------------------

--
-- Structure de la table `t_talentapp`
--

CREATE TABLE `t_talentapp` (
  `id` bigint(20) NOT NULL,
  `niveau_maitrise` varchar(255) DEFAULT NULL,
  `periode_affectation` datetime DEFAULT NULL,
  `talent_id` bigint(20) DEFAULT NULL,
  `talenttype` int(11) DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL,
  `occupation` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `t_talentapp`
--

INSERT INTO `t_talentapp` (`id`, `niveau_maitrise`, `periode_affectation`, `talent_id`, `talenttype`, `application_id`, `occupation`) VALUES
(399, 'Maîtrise complète', '2021-11-11 01:00:00', 2356, 1, 1, 30),
(400, 'Avancé', '2022-05-07 01:00:00', 4667, 1, 1, 60),
(401, 'Expert', '2022-03-25 01:00:00', 7765, 1, 1, 40),
(402, 'Intermédiaire', '2023-01-06 01:00:00', 7767, 1, 1, 40),
(403, 'Débutant', '2022-08-05 01:00:00', 4667, 2, 250, 40),
(404, 'Maîtrise complète', '2022-01-15 01:00:00', 3456, 0, 250, 100),
(405, 'Maîtrise complète', '2021-07-02 01:00:00', 2356, 0, 133, 70),
(406, 'Avancé', '2022-09-09 01:00:00', 7765, 1, 133, 60),
(407, 'Avancé', '2022-06-16 01:00:00', 7767, 1, 133, 60),
(410, 'Avancé', '2022-12-15 01:00:00', 2466, 1, 3, 20),
(411, 'Expert', '2022-02-11 01:00:00', 4321, 0, 3, 50),
(412, 'Intermédiaire', '2022-08-25 01:00:00', 7769, 1, 3, 30),
(413, 'Débutant', '2023-04-05 01:00:00', 6779, 2, 3, 100),
(414, 'Maîtrise complète', '2021-10-14 01:00:00', 4321, 1, 2, 50),
(415, 'Expert', '2022-01-06 01:00:00', 7769, 1, 2, 70),
(416, 'Avancé', '2022-10-06 01:00:00', 2466, 2, 2, 20),
(417, 'Maîtrise complète', '2022-06-30 01:00:00', 2466, 0, 408, 60);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `t_application`
--
ALTER TABLE `t_application`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgovtxy6gx9rdvvavu7qxverwn` (`domaine_id`),
  ADD KEY `FKrpr9yn87h6g88plqgsu5712yp` (`rd_id`);

--
-- Index pour la table `t_das`
--
ALTER TABLE `t_das`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4rwvulhyir2qe2qlhcj5vqx1w` (`domainedas_id`);

--
-- Index pour la table `t_domaine`
--
ALTER TABLE `t_domaine`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_equipeapp`
--
ALTER TABLE `t_equipeapp`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1gdj41byg4l8666csewunfkic` (`application_id`);

--
-- Index pour la table `t_etatapp`
--
ALTER TABLE `t_etatapp`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdkahy564y37dm00rr2gb6wv4i` (`application_id`);

--
-- Index pour la table `t_rd`
--
ALTER TABLE `t_rd`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8q36gpc5bvpdw73k2a5e6rn6r` (`das_id`);

--
-- Index pour la table `t_talentapp`
--
ALTER TABLE `t_talentapp`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfr9hy4jy2xh2wha4qa0m784r0` (`application_id`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `t_application`
--
ALTER TABLE `t_application`
  ADD CONSTRAINT `FKgovtxy6gx9rdvvavu7qxverwn` FOREIGN KEY (`domaine_id`) REFERENCES `t_domaine` (`id`),
  ADD CONSTRAINT `FKrpr9yn87h6g88plqgsu5712yp` FOREIGN KEY (`rd_id`) REFERENCES `t_rd` (`id`);

--
-- Contraintes pour la table `t_das`
--
ALTER TABLE `t_das`
  ADD CONSTRAINT `FK4rwvulhyir2qe2qlhcj5vqx1w` FOREIGN KEY (`domainedas_id`) REFERENCES `t_domaine` (`id`);

--
-- Contraintes pour la table `t_equipeapp`
--
ALTER TABLE `t_equipeapp`
  ADD CONSTRAINT `FK1gdj41byg4l8666csewunfkic` FOREIGN KEY (`application_id`) REFERENCES `t_application` (`id`);

--
-- Contraintes pour la table `t_etatapp`
--
ALTER TABLE `t_etatapp`
  ADD CONSTRAINT `FKdkahy564y37dm00rr2gb6wv4i` FOREIGN KEY (`application_id`) REFERENCES `t_application` (`id`);

--
-- Contraintes pour la table `t_rd`
--
ALTER TABLE `t_rd`
  ADD CONSTRAINT `FK8q36gpc5bvpdw73k2a5e6rn6r` FOREIGN KEY (`das_id`) REFERENCES `t_das` (`id`);

--
-- Contraintes pour la table `t_talentapp`
--
ALTER TABLE `t_talentapp`
  ADD CONSTRAINT `FKfr9hy4jy2xh2wha4qa0m784r0` FOREIGN KEY (`application_id`) REFERENCES `t_application` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
