CREATE DATABASE `Todo` /*!40100 DEFAULT CHARACTER SET latin1 */;
CREATE TABLE `item` (
  `iditem` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(200) NOT NULL,
  `feito` tinyint(1) NOT NULL,
  `idtodo` int(11) DEFAULT NULL,
  PRIMARY KEY (`iditem`)
) ENGINE=InnoDB;

CREATE TABLE `todo` (
  `idtodo` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) NOT NULL,
  `feito` tinyint(1) NOT NULL,
  `del` varchar(45) NOT NULL,
  PRIMARY KEY (`idtodo`)
) ENGINE=InnoDB ;
