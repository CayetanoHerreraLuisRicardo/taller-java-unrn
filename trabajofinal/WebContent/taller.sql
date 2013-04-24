SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `taller` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `taller` ;

-- -----------------------------------------------------
-- Table `taller`.`rol`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taller`.`rol` ;

CREATE  TABLE IF NOT EXISTS `taller`.`rol` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
INSERT INTO `taller`.`rol` (`nombre`) VALUES
('administrador'),
('cliente'),
('invitado');


-- -----------------------------------------------------
-- Table `taller`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taller`.`usuario` ;

CREATE  TABLE IF NOT EXISTS `taller`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `user` VARCHAR(12) NOT NULL ,
  `pass` VARCHAR(11) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `apellido` VARCHAR(45) NOT NULL ,
  `mail` VARCHAR(45) NOT NULL ,
  `rol_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_usuario_rol1_idx` (`rol_id` ASC) ,
  CONSTRAINT `fk_usuario_rol1`
    FOREIGN KEY (`rol_id` )
    REFERENCES `taller`.`rol` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `taller`.`usuario` (`id`,`user`,`pass`,`nombre`,`apellido`,`mail`,`rol_id`) VALUES
(1,'luciano','luciano','Luciano','Graziani','lucianog6@msn.com',1),
(2,'alfredo','alfredo','Alfredo','Graziani','alfredograziani@msn.com',2),
(3,'patricio','patricio','Patricio','Graziani','patogra@hotmail.com',2),
(4,'viviana','viviana','Viviana','Svensson','vivianasvensson@hotmail.com',2);


-- -----------------------------------------------------
-- Table `taller`.`pedido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taller`.`pedido` ;

CREATE  TABLE IF NOT EXISTS `taller`.`pedido` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `usuario_id` INT NOT NULL ,
  `fecha_pedido` DATE NOT NULL ,
  `estado` VARCHAR(45) NOT NULL ,
  `fecha_entrega` DATE NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_pedido_usuario_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_pedido_usuario`
    FOREIGN KEY (`usuario_id` )
    REFERENCES `taller`.`usuario` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taller`.`categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taller`.`categoria` ;

CREATE  TABLE IF NOT EXISTS `taller`.`categoria` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
INSERT INTO `taller`.`categoria` (`nombre`) VALUES
('Cámaras Fotográficas'),
('Pen-Drive\'s'),
('Celulares');


-- -----------------------------------------------------
-- Table `taller`.`producto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taller`.`producto` ;

CREATE  TABLE IF NOT EXISTS `taller`.`producto` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  `descrip` VARCHAR(500) NOT NULL ,
  `precio` DOUBLE NOT NULL ,
  `img_url` VARCHAR(500) NULL ,
  `categoria_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_producto_categoria1_idx` (`categoria_id` ASC) ,
  CONSTRAINT `fk_producto_categoria1`
    FOREIGN KEY (`categoria_id` )
    REFERENCES `taller`.`categoria` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `taller`.`producto` (`nombre`,`descrip`,`precio`,`img_url`,`categoria_id`) VALUES
('Sony W650','<li>Cámara digital compacta Cyber-Shot. Procesamiento de imágenes de alto rendimiento con calidad óptica extraordinaria.</li><li>16MPx</li><li>Pantalla de 3"</li><li>Potente zoom óptico de 5x. Se pueden utilizar mientras se filma.</li><li>Imágenes en 1080p y vídeos en 720p.</li><li>Barrido panorámico 360</li><li>Con 8GB de memoria + funda.</li>',1250,'img/camarasFoto/sonyW650.jpg',1),
('CANON SX160 IS','<li>Incluye un lector de SD, un bolso y una memoria Kingston de 8GB.</li><li>16MPx</li><li>Pantalla LCD de 3"</li><li>Vídeos en 720p con sonido estéreo.</li><li>Optimiza los retratos.</li><li>Zoom óptico de 16x.</li>',2000,'img/camarasFoto/canonSX160.jpg',1),
('Samsung ST68','<li>Incluye memoria de 8GB y funda rígida.</li><li>16MPx</li><li>Zoom óptico de 5x.</li><li>Vídeos en 720p.</li><li>Pantalla de 2.7"</li><li>Fotos panorámicas de 180º.</li>',900,'img/camarasFoto/samsungST68.jpg',1),
('Sony DSC-H90','<li>Incluye: Un lector de memoria. Una memoria de 8GB. Una funda rígida.</li><li>16.2MPx</li><li>Zoom óptico de 16x. Se puede utilizar mientras se filma.</li><li>Pantalla LCD de 3"</li><li>Imágenes en 1080p y vídeos en 720p.</li><li>Panorámico barrido de imágenes.</li>',1850,'img/camarasFoto/sonyDscH90.jpg',1),
('LG Optimus L3 E400','<li>Android</li><li>WiFi</li><li>Pantalla touch 3.2"</li><li>Cámara de fotos de 3.15MPx</li><li>GPS+GoogleMaps</li><li>1GB de memoria interna</li><li>384MB de RAM</li><li>Procesador Qualcom Snadragon 800MHz</li><li>GPU Andreno 200</li><li>Internet móvil 3G.</li>',1000,'img/celular/optimusE400.jpg',3),
('Motorola XT615','<li>Android 2.3</li><li>Pantalla touch de 4"</li><li>Dos cámaras. Trasera de 8MPx con zoom digital. Frontal para poder realizar video llamadas y sacar fotos.</li><li>Posee WiFi y Bluetooth 3.0</li><li>GPS+GoogleMaps</li>',2300,'img/celular/motorolaXT615.jpg',3),
('Samsung Galaxy Note 2 N7100','<li>Android 4.1</li><li>1.6GHz QuadCore</li><li>2GB RAM</li><li>16GB memoria interna</li><li>Internet móvil 4G</li><li>Pantalla táctil de 5.5" con resolución 720p</li><li>Cámara primaria de 8MPx. Cámara secundaria de 1.9MPx.</li><li>Vídeos de 1080p.</li>',6600,'img/celular/galaxyNote2N7100.jpg',3),
('Sony Ericsson Xperia Tipo ST21A','<li>Chipset Qualcomm MSM7227A</li><li>CPU 800 MHz</li><li>GPU Adreno 200.</li><li>2.9GB de memoria física. 512MB RAM</li><li>WiFi</li><li>Bluetooth 2.1</li><li>Internet móvil 3G</li><li>Cámara principal VGA de 3.15MPx</li><li>Pantalla de 3".</li>',1200,'img/celular/xperiaTipoST21A.jpg',3),
('Kingston DT101','<li>16GB de almacenamiento.</li><li>USB 2.0.</li>',99,'img/penDrive/kingstonDT101-16GB.jpg',2),
('Kingston DT101','<li>8GB de almacenamiento.</li><li>USB 2.0.</li>',58,'img/penDrive/kingstonDT101-8GB.jpg',2),
('Kingston G3','<li>32GB de almacenamiento.</li><li>USB 2.0.</li>',185,'img/penDrive/kingstonG3.jpg',2);


-- -----------------------------------------------------
-- Table `taller`.`pedido_producto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taller`.`pedido_producto` ;

CREATE  TABLE IF NOT EXISTS `taller`.`pedido_producto` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `pedido_id` INT NOT NULL ,
  `producto_id` INT NOT NULL ,
  `cantProds` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_pedido_has_producto_producto1_idx` (`producto_id` ASC) ,
  INDEX `fk_pedido_has_producto_pedido1_idx` (`pedido_id` ASC) ,
  CONSTRAINT `fk_pedido_has_producto_pedido1`
    FOREIGN KEY (`pedido_id` )
    REFERENCES `taller`.`pedido` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_has_producto_producto1`
    FOREIGN KEY (`producto_id` )
    REFERENCES `taller`.`producto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
