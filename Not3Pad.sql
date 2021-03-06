-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 12-08-2020 a las 05:07:09
-- Versión del servidor: 10.3.23-MariaDB-0+deb10u1
-- Versión de PHP: 7.3.19-1+0~20200612.60+debian10~1.gbp6c8fe1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `Not3Pad`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Articulos`
--

CREATE TABLE `Articulos` (
  `Nom_Grupo` varchar(50) NOT NULL,
  `Nom_Articulo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Descripcion`
--

CREATE TABLE `Descripcion` (
  `id` int(11) NOT NULL,
  `Nom_Grupo` varchar(50) NOT NULL,
  `Nom_Articulo` varchar(50) NOT NULL,
  `Texto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Grupos`
--

CREATE TABLE `Grupos` (
  `Nom_Grupo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Articulos`
--
ALTER TABLE `Articulos`
  ADD PRIMARY KEY (`Nom_Articulo`,`Nom_Grupo`) USING BTREE,
  ADD KEY `Nom_Grupo` (`Nom_Grupo`),
  ADD KEY `Nom_Articulo` (`Nom_Articulo`);

--
-- Indices de la tabla `Descripcion`
--
ALTER TABLE `Descripcion`
  ADD PRIMARY KEY (`Nom_Grupo`,`Nom_Articulo`,`id`) USING BTREE,
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `Nom_Grupo` (`Nom_Grupo`),
  ADD KEY `Nom_Articulo` (`Nom_Articulo`);

--
-- Indices de la tabla `Grupos`
--
ALTER TABLE `Grupos`
  ADD PRIMARY KEY (`Nom_Grupo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Descripcion`
--
ALTER TABLE `Descripcion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Articulos`
--
ALTER TABLE `Articulos`
  ADD CONSTRAINT `Articulos_ibfk_1` FOREIGN KEY (`Nom_Grupo`) REFERENCES `Grupos` (`Nom_Grupo`) ON DELETE CASCADE;

--
-- Filtros para la tabla `Descripcion`
--
ALTER TABLE `Descripcion`
  ADD CONSTRAINT `Descripcion_ibfk_1` FOREIGN KEY (`Nom_Articulo`) REFERENCES `Articulos` (`Nom_Articulo`) ON DELETE CASCADE,
  ADD CONSTRAINT `Descripcion_ibfk_2` FOREIGN KEY (`Nom_Grupo`) REFERENCES `Articulos` (`Nom_Grupo`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
