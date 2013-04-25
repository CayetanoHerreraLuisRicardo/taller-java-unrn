SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `trabajofinal` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `trabajofinal` ;

-- -----------------------------------------------------
-- Table `trabajofinal`.`rol`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trabajofinal`.`rol` ;

CREATE  TABLE IF NOT EXISTS `trabajofinal`.`rol` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
INSERT INTO `trabajofinal`.`rol` (`nombre`) VALUES
('administrador'),
('cliente');


-- -----------------------------------------------------
-- Table `trabajofinal`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trabajofinal`.`usuario` ;

CREATE  TABLE IF NOT EXISTS `trabajofinal`.`usuario` (
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
    REFERENCES `trabajofinal`.`rol` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `trabajofinal`.`usuario` (`id`,`user`,`pass`,`nombre`,`apellido`,`mail`,`rol_id`) VALUES
(1,'luciano','luciano','Luciano','Graziani','lucianog6@msn.com',1),
(2,'alfredo','alfredo','Alfredo','Graziani','alfredograziani@msn.com',2),
(3,'patricio','patricio','Patricio','Graziani','patogra@hotmail.com',2),
(4,'viviana','viviana','Viviana','Svensson','vivianasvensson@hotmail.com',2),
(5,'manolo','manolo','Manolo','Sanhueza','emanuel_sanhueza@hotmail,com',1);


-- -----------------------------------------------------
-- Table `trabajofinal`.`pedido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trabajofinal`.`pedido` ;

CREATE  TABLE IF NOT EXISTS `trabajofinal`.`pedido` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `usuario_id` INT NOT NULL ,
  `fecha_pedido` DATE NOT NULL ,
  `estado` VARCHAR(45) NOT NULL ,
  `fecha_entrega` DATE NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_pedido_usuario_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_pedido_usuario`
    FOREIGN KEY (`usuario_id` )
    REFERENCES `trabajofinal`.`usuario` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trabajofinal`.`categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trabajofinal`.`categoria` ;

CREATE  TABLE IF NOT EXISTS `trabajofinal`.`categoria` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
INSERT INTO `trabajofinal`.`categoria` (`nombre`) VALUES
('Acción'),
('Aventura'),
('Casual'),
('Conducción'),
('Deportes'),
('Estrategia'),
('MMO'),
('Rol-RPG'),
('Simulación');


-- -----------------------------------------------------
-- Table `trabajofinal`.`producto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trabajofinal`.`producto` ;

CREATE  TABLE IF NOT EXISTS `trabajofinal`.`producto` (
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
    REFERENCES `trabajofinal`.`categoria` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `trabajofinal`.`producto` (`nombre`,`descrip`,`precio`,`categoria_id`,`img_url`) VALUES
('Crysis 3','<li>Juego sin reseña</li>', 299, 1, NULL),
('Tomb Rider','<li>Juego sin reseña</li>', 199, 1, NULL),
('Dead Space 3','<li>Juego sin reseña</li>', 359, 1, NULL),
('Assassin''s Creed 3','<li>Juego sin reseña</li>', 359, 1, NULL),
('Far Cry','<li>Juego sin reseña</li>', 189, 1, NULL),
('DmC','<li>Juego sin reseña</li>', 259, 1, NULL),
('Wath Dogs','<li>Juego sin reseña</li>', 199, 1, NULL),
('The Cave','<li>Juego sin reseña</li>', 99, 2, NULL),
('Amnesia: A Machine for Pigs','<li>Juego sin reseña</li>', 300, 2, NULL),
('Lucius','<li>Juego sin reseña</li>', 259, 2, NULL),
('Asylum','<li>Juego sin reseña</li>', 156, 2, NULL),
('Anna','<li>Juego sin reseña</li>', 109, 2, NULL),
('Los Sims 3','<li>Juego sin reseña</li>', 0, 3, NULL),
('Plants Vs. Zombies','<li>Juego sin reseña</li>', 0, 3, NULL),
('Spore','<li>Juego sin reseña</li>', 59, 3, NULL),
('Braid','<li>Juego sin reseña</li>', 149, 3, NULL),
('Word of Goo','<li>Juego sin reseña</li>', 149, 3, NULL),
('Need For Speed Most Wanted','<li>Juego sin reseña</li>', 299, 4, NULL),
('GRID 2','<li>Juego sin reseña</li>', 399, 4, NULL),
('Euro Truck','<li>Juego sin reseña</li>', 99, 4, NULL),
('WRC 3','<li>Juego sin reseña</li>', 109, 4, NULL),
('ToCA Race Driver 3','<li>Juego sin reseña</li>', 0, 4, NULL),
('F1 2012','<li>Juego sin reseña</li>', 289, 4, NULL),
('Trackmania 2: Canyon','<li>Juego sin reseña</li>', 79, 4, NULL),
('FIFA 13','<li>Juego sin reseña</li>', 389, 5, NULL),
('PES 2013','<li>Juego sin reseña</li>', 389, 5, NULL),
('NBA 2K13','<li>Juego sin reseña</li>', 299, 5, NULL),
('FIFA Manager 13','<li>Juego sin reseña</li>', 219, 5, NULL),
('The Hunter','<li>Juego sin reseña</li>', 59, 5, NULL),
('Total War: Rome II','<li>Juego sin reseña</li>', 459, 6, NULL),
('SimCity','<li>Juego sin reseña</li>', 0, 6, NULL),
('Impire','<li>Juego sin reseña</li>', 99, 6, NULL),
('Age Of Empire II HD','<li>Juego sin reseña</li>', 0, 6, NULL),
('StarCraft 2','<li>Juego sin reseña</li>', 199, 6, NULL),
('Neverwinter','<li>Juego sin reseña</li>', 219, 7, NULL),
('TERA: True Action Combat','<li>Juego sin reseña</li>', 189, 7, NULL),
('The War Z','<li>Juego sin reseña</li>', 179, 7, NULL),
('Dota 2','<li>Juego sin reseña</li>', 180, 7, NULL),
('League of Legends','<li>Juego sin reseña</li>', 0, 7, NULL),
('Skyrim-Dragonborn','<li>Juego sin reseña</li>', 189, 8, NULL),
('Diablo III','<li>Juego sin reseña</li>', 209, 8, NULL),
('LOTR: La Guerra del Norte','<li>Juego sin reseña</li>', 199, 8, NULL),
('Dark Souls','<li>Juego sin reseña</li>', 239, 8, NULL),
('Microsoft Flight','<li>Juego sin reseña</li>', 0, 9, NULL),
('X3: Reunion','<li>Juego sin reseña</li>', 0, 9, NULL),
('JASF','<li>Juego sin reseña</li>', 0, 9, NULL),
('Silent Hunter 5','<li>Juego sin reseña</li>', 0, 9, NULL);


-- -----------------------------------------------------
-- Table `trabajofinal`.`pedido_producto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trabajofinal`.`pedido_producto` ;

CREATE  TABLE IF NOT EXISTS `trabajofinal`.`pedido_producto` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `pedido_id` INT NOT NULL ,
  `producto_id` INT NOT NULL ,
  `cantProds` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_pedido_has_producto_producto1_idx` (`producto_id` ASC) ,
  INDEX `fk_pedido_has_producto_pedido1_idx` (`pedido_id` ASC) ,
  CONSTRAINT `fk_pedido_has_producto_pedido1`
    FOREIGN KEY (`pedido_id` )
    REFERENCES `trabajofinal`.`pedido` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_has_producto_producto1`
    FOREIGN KEY (`producto_id` )
    REFERENCES `trabajofinal`.`producto` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
