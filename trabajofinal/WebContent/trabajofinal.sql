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
  `descrip` VARCHAR(1024) NOT NULL ,
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
('Crysis 3','<li>Tercera entrega de la saga de acción desarrollada por CryTek: Crysis. El juego se ambienta de nuevo en la ciudad de Nueva York, ahora más abierta y con mayor variedad de natural de escenarios, en pleno 2047.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: CryTek</li>
<li>Distribuidor: EA</li>
<li>Jugadores: 1-12 (Competitivo: 2-12)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 21 de febrero de 2013 (Pegi: +16)</li>', 299, 1, 'img/accion/crysis3.jpg'),
('Tomb Rider','<li>En esta nueva aventura encarnaremos a una joven e inexperta Lara, que deberá enfrentarse a los diferentes peligros y extrañas criaturas que habitan en una isla de la costa de Japón, dentro de un mundo abierto (sand-box) con tintes de survival-horror.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Crystal Dynamics</li>
<li>Distribuidor: Eidos Interactive</li>
<li>Jugadores: 1-8 (Competitivo: 2-8)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 5 de marzo de 2013 (Pegi: +18)</li>', 199, 1, 'img/accion/tombraider.jpg'),
('Dead Space 3','<li>Tercera entrega dentro de la serie Dead Space que destaca por ofrecer cooperativo online, una campaña con nuevos necromorfos, más duración, un inédito planeta helado y opciones de creación de armas.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Visceral Games</li>
<li>Distribuidor: EA</li>
<li>Jugadores: 1-2 (Cooperativo: 2)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 8 de febrero de 2013 (Pegi: +18)</li>', 359, 1, 'img/accion/deadspace3.jpg'),
('Assassin''s Creed 3','<li>Juego sin reseña</li>', 359, 1, NULL),
('Far Cry','<li>Tercera entrega de la saga que regresará al exótico y peligroso entorno africano.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: UbiSoft</li>
<li>Distribuidor: UbiSoft</li>
<li>Jugadores: 1-16 (Competitivo: 2-16 / Cooperativo: 2-4)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 29 de noviembre de 2012 (Pegi: +18)</li>', 189, 1, 'img/accion/farcry3.jpg'),
('DmC','<li>Nuevo capítulo de la popular saga de acción y fantasía creada por Capcom, cuyo desarrollo ha sido encomendado al estudio Ninja Theory en colaboración con Capcom US. En el juego veremos a un Dante más rápido y demoledor que nunca, pero también mucho más joven e inexperto que, aun así, no pierde sus incipientes aires de chulería.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Ninja Theory</li>
<li>Distribuidor: Capcom</li>
<li>Jugadores: 1</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 15 de enero de 2013 (Pegi: +16)</li>', 259, 1, 'img/accion/dmc.jpg'),
('Watch Dogs','<li>Watch Dogs está ambientado en un futuro cercano en el que el jugador tiene en su poder infinidad de gadgets y de artilugios tecnológicos con los que sembrar el caos a su alrededor para llevar a cabo diferentes tipos de misiones. Un planteamiento mitad aventura mitad shooter que ha levantado admiración durante su presentación en sociedad.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - PS4 - Wii U</li>
<li>Desarrollador: UbiSoft</li>
<li>Distribuidor: UbiSoft</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 2013 (Por determinar) (Pegi: +18)</li>', 199, 1, 'img/accion/watchdogs.jpg'),
('The Cave','<li>Juego sin reseña</li>', 99, 2, NULL),
('Amnesia: A Machine for Pigs','<li>Juego sin reseña</li>', 300, 2, NULL),
('Lucius','<li>Juego sin reseña</li>', 259, 2, NULL),
('Asylum','<li>Juego sin reseña</li>', 156, 2, NULL),
('Anna','<li>Juego sin reseña</li>', 109, 2, NULL),
('Los Sims 3','<li>Tercera parte del que probablemente sea el simulador de vida virtual más famoso del mundo. Nuevas características jugables, un nuevo Creador de Sims, personalidades más realistas y un poderoso sistema de personalización, son algunas de sus novedades.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - Wii - 3DS - DS - iPhone - Mac</li>
<li>Desarrollador: The Sims Studio</li>
<li>Distribuidor: EA</li>
<li>Jugadores: 1</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 26 de octubre de 2010 (Pegi: +12)</li>', 0, 3, 'img/accion/sims3.jpg'),
('Plants Vs. Zombies','<li>Una inmensa horda de divertidos zombis se dispone a invadir tu casa, y tu única línea de defensa es un arsenal de 49 plantas "atrapazombis". Este es el divertido plateamiento de este interesante juego mezcla de estrategia, acción y puzle.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - 3DS - DS - Vita - Web - iPhone - iPad - Android - Mac</li>
<li>Desarrollador: PopCap</li>
<li>Distribuidor: PopCap</li>
<li>Jugadores: 1-2 (Cooperativo: 2)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 9 de febrero de 2011 (Pegi: +12)</li>', 0, 3, 'img/accion/plants_vs_zombies.jpg'),
('Spore','<li>Will Wright creador del exitoso "Los Sims" es el responsable de "Spore", un innovador juego evolutivo en el que el jugador creará sus propias criaturas en un mundo que se desarrolla por toda la galaxia.</li>
<li>Plataforma: PC - DS - iPhone - Mac</li>
<li>Desarrollador: Maxis</li>
<li>Distribuidor: EA</li>
<li>Jugadores: 1</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 5 de septiembre de 2008 (Pegi: +12)</li>', 59, 3, 'img/accion/spore.jpg'),
('Braid','<li>Desarrollado por el diseñador experimental Jonathan Blow, ganador del "Independent Games Festival", Braid es un juego que mezcla plataformas 2D con elementos puzle dentro de un sistema de juego que permite manipular el tiempo de la acción.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Hothead Games</li>
<li>Distribuidor: Sony</li>
<li>Jugadores: 1</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 17 de diciembre de 2009 (Pegi: +7)</li>', 149, 3, 'img/accion/braid.jpg'),
('World of Goo','<li>Es un original juego, tanto en concepto como en estilo gráfico, en el que es posible construir a base de una gotas llamadas "goo", todo tipo de impresionantes estructuras con las que resolver montones de ingeniosos puzles.</li>
<li>Plataforma: PC - Wii - iPhone - iPad - Android - Mac</li>
<li>Desarrollador: 2D Boy</li>
<li>Distribuidor: 2D Boy</li>
<li>Jugadores: 1-2</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 19 de junio de 2009 (Pegi: +3)</li>', 149, 3, 'img/accion/world_of_goo.jpg'),
('Need For Speed: Most Wanted','<li>Juego sin reseña</li>', 299, 4, NULL),
('GRID 2','<li>Secuela del trepidante juego de carreras desarrollado por Codemasters, que cuenta entre otras novedades con pleno la última evolución del premiado motor gráfico EGO.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Codemasters</li>
<li>Distribuidor: Codemasters</li>
<li>Lanzamiento: 31 de mayo de 2013</li>', 399, 4, NULL),
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
