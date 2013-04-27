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
  `fecha_compra` DATE NOT NULL ,
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
<li>Lanzamiento: 21 de febrero de 2013 (Pegi: +16)</li>', 299, 1, 'img/crysis3.jpg'),
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
<li>Lanzamiento: 5 de marzo de 2013 (Pegi: +18)</li>', 199, 1, 'img/tombraider.jpg'),
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
<li>Lanzamiento: 8 de febrero de 2013 (Pegi: +18)</li>', 359, 1, 'img/deadspace3.jpg'),
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
<li>Lanzamiento: 29 de noviembre de 2012 (Pegi: +18)</li>', 189, 1, 'img/farcry3.jpg'),
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
<li>Lanzamiento: 15 de enero de 2013 (Pegi: +16)</li>', 259, 1, 'img/dmc.jpg'),
('Watch Dogs','<li>Watch Dogs está ambientado en un futuro cercano en el que el jugador tiene en su poder infinidad de gadgets y de artilugios tecnológicos con los que sembrar el caos a su alrededor para llevar a cabo diferentes tipos de misiones. Un planteamiento mitad aventura mitad shooter que ha levantado admiración durante su presentación en sociedad.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - PS4 - Wii U</li>
<li>Desarrollador: UbiSoft</li>
<li>Distribuidor: UbiSoft</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 2013 (Por determinar) (Pegi: +18)</li>', 199, 1, 'img/watchdogs.jpg'),
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
<li>Lanzamiento: 26 de octubre de 2010 (Pegi: +12)</li>', 0, 3, 'img/sims3.jpg'),
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
<li>Lanzamiento: 9 de febrero de 2011 (Pegi: +12)</li>', 0, 3, 'img/plants_vs_zombies.jpg'),
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
<li>Lanzamiento: 5 de septiembre de 2008 (Pegi: +12)</li>', 59, 3, 'img/spore.jpg'),
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
<li>Lanzamiento: 17 de diciembre de 2009 (Pegi: +7)</li>', 149, 3, 'img/braid.jpg'),
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
<li>Lanzamiento: 19 de junio de 2009 (Pegi: +3)</li>', 149, 3, 'img/world_of_goo.jpg'),
('Need For Speed: Most Wanted','<li>Juego sin reseña</li>', 299, 4, 'img/nfs_mw.jpg'),
('GRID 2','<li>Secuela del trepidante juego de carreras desarrollado por Codemasters, que cuenta entre otras novedades con pleno la última evolución del premiado motor gráfico EGO.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Codemasters</li>
<li>Distribuidor: Codemasters</li>
<li>Lanzamiento: 31 de mayo de 2013</li>', 399, 4, 'img/grid2.jpg'),
('Euro Truck','<li>Segunda entrega del simulador de conducción de vehículos pesados, que nos invita a recorrer una amplia selección de carreteras secundarias europeas.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: SCS Software</li>
<li>Distribuidor: Kaipioni</li>
<li>Lanzamiento: 5 de octubre de 2012 (Pegi: +3)</li>', 99, 4, 'img/eurotruck.jpg'),
('WRC 3','<li>Nueva entrega del simulador de conducción WRC desarrollado por Milestone que presenta un nuevo motor gráfico con efectos de iluminación y de partículas avanzados. El juego pone a disposición del jugador todos los tramos del campeonato actual de rallies junto con los más de 50 equipos oficiales.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - Vita</li>
<li>Desarrollador: Milestone</li>
<li>Distribuidor: Namco Bandai</li>
<li>Jugadores: 1-16 (Competitivo: 16)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 12 de octubre de 2012 (Pegi: +3)</li>', 109, 4, 'img/wrc3.jpg'),
('ToCA Race Driver 3','<li>Es la nueva edición de la exitosa saga automovilística de Codemaster, la cual cubre una gran variedad de deportes de motor. En ToCA Race Driver 3 la competición se desarrolla en circuitos de todo el mundo con dos modos de juego: "Gira mundial" o "Carrera profesional".</li>
<li>Plataforma: PC - PSP - PS2 - XBOX</li>
<li>Desarrollador: Codemasters</li>
<li>Distribuidor: Codemasters</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 24 de febrero de 2006 (Pegi: +12)</li>', 145, 4, 'img/tocarace3.jpg'),
('F1 2012','<li>Videojuego con licencia oficial de la temporada 2012 del campeonato de Fórmula Uno que que incluye todos los equipos, pilotos y circuitos oficiales. Esta entrega incluye varias novedades y mejoras respecto a F1 2011, entre ellas la incorporación de la "Prueba para Jóvenes Pilotos".</li>
<li>Plataforma: PlayStation 3 - PC - X360 - Mac</li>
<li>Desarrollador: Codemasters</li>
<li>Distribuidor: Codemasters</li>
<li>Jugadores: 1-16 (Competitivo: 2-16 / Cooperativo: 2)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 21 de septiembre de 2012 (Pegi: +3)</li>', 289, 4, 'img/f12012.jpg'),
('Trackmania 2: Canyon','<li>Secuela del exitoso arcade de conducción desarrollado por Nadeo. Carreras vertiginosas, un buen número de vehículos, circuitos, ambientaciones, competiciones y una intensa jugabilidad reforzadas con nuevos modos de juego.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Nadeo</li>
<li>Distribuidor: UbiSoft</li>
<li>Jugadores: 1-200 (Competitivo: 2-200)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 14 de septiembre de 2011 (Pegi: +3)</li>', 79, 4, 'img/trackmania2.jpg'),
('FIFA 13','<li>Edición de la temporada 2012-2013 de la popular saga de videojuegos de fútbol de EA Sport.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - Wii - 3DS - PSP - Vita - PS2 - iPhone - iPad - Android - Wii U</li>
<li>Desarrollador: EA Games</li>
<li>Distribuidor: EA Sports</li>
<li>Jugadores: 1-22 (Competitivo: 2-22 / Cooperativo: 2-11)</li>
<li>Soporta: PlayStation Move</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 27 de septiembre de 2012 (Pegi: +3)</li>', 389, 5, 'img/fifa13.jpg'),
('PES 2013','<li>Nueva entrega de la popular serie de videojuegos de fútbol de Konami correspondiente a la temporada 2012-2013.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - Wii - 3DS - PSP - PS2 - iPhone - iPad - Android</li>
<li>Desarrollador: Konami</li>
<li>Distribuidor: Konami</li>
<li>Jugadores: 1-8 (Competitivo: 2-8 / Cooperativo: 2-8)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 20 de septiembre de 2012 (Pegi: +3)</li>', 389, 5, 'img/pes2013.jpg'),
('NBA 2K13','<li>Nueva entrega de la consagrada saga de baloncesto NBA 2K. Una de sus grandes novedades es la incorporación de comentarios en español.</li>
<li>Plataforma: PlayStation 3 - PC - X360 - Wii - PSP - iPhone - iPad - Android - Wii U</li>
<li>Desarrollador: Visual Concepts</li>
<li>Distribuidor: 2K Sports</li>
<li>Jugadores: 1-4 (Competitivo: 2-4)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 5 de octubre de 2012 (Pegi: +3)</li>', 299, 5, 'img/nba2k13.jpg'),
('FIFA Manager 13','<li>Nueva edición dentro de la serie FIFA Manager, destinada a ponernos al cargo de un equipo de fútbol como gestores y entrenadores, desde labores relacionadas con las instalaciones hasta la propia dirección de los futbolistas. Esta edición estrena un conjunto de novedades como el Team Dynamics, nuevo interfaz y herramientas de gestión.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Bright Future Gmbh</li>
<li>Distribuidor: EA Sports</li>
<li>Lanzamiento: 25 de octubre de 2012 (Pegi: +3)</li>', 219, 5, 'img/fifamanager13.jpg'),
('The Hunter','<li>Juego sin reseña</li>', 59, 5, NULL),
('Total War: Rome II','<li>La saga Total War estrena segunda entrega de Rome abarcando uno de los periodos de la historia más célebres. El videojuego combina la mayor campaña basada en turnos que se haya realizado por Creative Assembly, junto con las mayores batallas en tiempo real.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Creative Assembly</li>
<li>Distribuidor: SEGA</li>
<li>Lanzamiento: 2013 (Por determinar)</li>', 459, 6, 'img/tw_rome2.jpg'),
('SimCity V','<li>Quinta entrega de la afamada serie de juegos de gestión y estrategia de Maxis y Electronic Arts. Un buen lavado de cara para la franquicia que ofrece un mayor componente social y un acercamiento igual de apasionante para hardcores de la IP y para neófitos en el concepto de la "estrategia civil".</li>
<li>Plataforma: PC - Mac</li>
<li>Desarrollador: Maxis</li>
<li>Distribuidor: EA</li>
<li>Jugadores: 1-16 (Cooperativo: 2-16)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 7 de marzo de 2013 (Pegi: +7)</li>', 0, 6, 'img/simcity5.jpg'),
('Impire','<li>Juego de acción, estrategia y rol desarrollado por Cyanide que nos pondrá en la piel de un señor de las mazmorras, el demonio Bjal-Abaddon, que deberá defenderse de las incursiones de héroes y nobles caballeros haciendo uso de trampas y un montón de criaturas monstruosas a las que se podrá personalizar con todo tipo de armas y conjuntos de guerra.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Cyanide</li>
<li>Distribuidor: Paradox Interactive</li>
<li>Jugadores: 1-2 (Competitivo: 2-4 / Cooperativo: 2)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 14 de febrero de 2013 (Pegi: +16)</li>', 99, 6, 'img/impire.jpg'),
('Age Of Empire II HD','<li>El clásico Age of Empires II regresa al mercado con esta remasterización a la alta definición que incluye gráficos adaptados a las 1080p, texturas más definidas, así como mejores efectos de iluminación. El juego trae consigo además la expansión Conquerors.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Hidden Path</li>
<li>Distribuidor: Microsoft</li>
<li>Lanzamiento: 9 de abril de 2013 (Pegi: +12)</li>', 0, 6, 'img/aoe2.jpg'),
('StarCraft 2','<li>Continuación del popular juego de estrategia creado por Blizzard. Protoss, Terran y Zerg, vuelven a combatir en tiempo real, pero esta vez en 3D y con nuevas habilidades e incorporaciones. Esta segunda entrega de Starcraft se divide en tres capítulos: El primero, StarCraft II Terrans: Wings of Liberty. El segundo StarCraft II Zerg: Heart of the Swarm. Y el tercero y final, StarCraft II Protoss: Legacy of the Void.</li>
<li>Plataforma: PC - Mac</li>
<li>Desarrollador: Blizzard</li>
<li>Distribuidor: Activision Blizzard</li>
<li>Jugadores: 1-8 (Competitivo: 2-8 / Cooperativo: 2)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 27 de julio de 2010 (Pegi: +16)</li>', 199, 6, 'img/starcraft2.jpg'),
('Neverwinter','<li>Cryptic Studios creadores de títulos como City of Heroes, Champions Online o Star Trek Online, firman este videojuego basado en el universo de Dungeons & Dragons, y más concretamente en la ciudad de Neverwinter como su propio nombre indica.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Cryptic Studios</li>
<li>Distribuidor: Atari</li>
<li>Lanzamiento: 1º trimestre de 2013</li>', 219, 7, 'img/neverwinter.jpg'),
('TERA: True Action Combat','<li>MMORPG desarrollado por Bluehole Studio, con un potente apartado gráfico y un innovador sistema de combate, en el que podremos atacar a los enemigos sin necesidad de seleccionarlos.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Bluehole Studio</li>
<li>Distribuidor: Frogster Interactive</li>
<li>Jugadores: Masivo online</li>
<li>Idioma:<ul>
	<li>Manual: Inglés</li>
	<li>Textos: Inglés</li>
	<li>Voces: Inglés</li></ul>
</li>
<li>Lanzamiento: 3 de mayo de 2012 (Pegi: +12)</li>', 189, 7, 'img/tera.jpg'),
('The War Z','<li>The War Z es un simulador de supervivencia, multijugador masivo online, ambientada en un mundo abierto infestado de zombis.</li>
<li>Plataforma: PC
<li>Desarrollador: Hammerpoint Interactive</li>
<li>Distribuidor: Arktos Entertainment</li>
<li>Lanzamiento: 17 de diciembre de 2012 (Pegi: +18)</li>', 179, 7, 'img/thewarz.jpg'),
('Dota 2','<li>Segunda entrega de Defense of the Ancients, cuyo desarrollo surge de la colaboración de Valve con Ice Frog. Y la cual respetará los más de 100 héroes del original, junto con sus items y las distintas mejoras, dejando de lado el motor de Warcraft III y se apuesta por el Source de la propia Valve.</li>
<li>Plataforma: PC - Mac</li>
<li>Desarrollador: IceFrog</li>
<li>Distribuidor: Valve</li>
<li>Lanzamiento: 2013 (Por determinar)</li>', 180, 7, 'img/dota2.jpg'),
('League of Legends','<li>Juego de acción competitiva con dosis de RPG y estrategia, en un rico universo MMO (multijugador masivo online). La historia se recrea en un mágico mundo asolado por el conflicto de Rune Wars.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Riot Games</li>
<li>Distribuidor: Riot Games</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 30 de octubre de 2009 (Pegi: +12)</li>', 0, 7, 'img/lol.jpg'),
('Skyrim-Dragonborn','<li>Dragonborn, es el tercer contenido descargable para The Elder Scrolls V: Skyrim, el popular RPG de Bethesda.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Tipo: Contenido descargable (DLC) de The Elder Scrolls V: Skyrim (requerido)</li>
<li>Desarrollador: Bethesda Softworks</li>
<li>Distribuidor: Bethesda Softworks</li>
<li>Jugadores: 1</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 27 de febrero de 2013 (Pegi: +18)</li>', 189, 8, 'img/skyrim.jpg'),
('Diablo III','<li>Tercera entrega del ya clásico juego de Blizzard que sentó las bases de lo que hoy en día entendemos como un Action RPG. Un fuerte componente multijugador cooperativo, nuevas clases, habilidades, interfaz de juego y una campaña más profunda y dinámica marcada por la aleatoriedad de las misiones, conforman un auténtico diablo jugable que promete tener mucho que decir.</li>
<li>Plataforma: PlayStation 3 - PC - PS4 - Mac</li>
<li>Desarrollador: Blizzard</li>
<li>Distribuidor: Activision Blizzard</li>
<li>Jugadores: 1-4 (Cooperativo: 2-4)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 2013 (Por determinar) (Pegi: +16)</li>', 209, 8, 'img/diablo3.jpg'),
('LOTR: La Guerra del Norte','<li>Videojuego multijugador de acción y aventura RPG, basado en la obra literaria de Tolkien, donde el jugador se convertirá en héroe de la gran batalla del Norte como parte de la épica Guerra del Anillo. Pudiendo explorar tierras desconocidas, elementos y personajes del universo de la Tierra Media, así como elementos de las últimas películas. Disponiendo además, de un innovador modo cooperativo online de hasta tres jugadores.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Snowblind Studios</li>
<li>Distribuidor: Warner Bros</li>
<li>Jugadores: 1-3 (Cooperativo: 2-3)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Español</li></ul>
</li>
<li>Lanzamiento: 15 de noviembre de 2011 (Pegi: +18)</li>', 199, 8, 'img/lotr.jpg'),
('Dark Souls','<li>RPG de acción y fantasía oscura y medieval muy similar en estilo a Demon\'s Soul, videojuego del que se considera sucesor espiritual.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: From Software</li>
<li>Distribuidor: Namco Bandai</li>
<li>Jugadores: 1-2 (Cooperativo: 2)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Inglés</li></ul>
</li>
<li>Lanzamiento: 7 de octubre de 2011 (Pegi: +16)</li>', 239, 8, 'img/darksouls.jpg'),
('Microsoft Flight','<li>Nuevo episodio de la veterana saga de simulación aérea de Microsoft que responde, en esta ocasión, al escueto nombre de Flight. Tras más de un lustro de ausencia, la IP regresa con Hawái como telón de fondo y un renovado esfuerzo por actualizar su experiencia de juego a los tiempos actuales.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Microsoft</li>
<li>Distribuidor: Microsoft</li>
<li>Lanzamiento: 29 de febrero de 2012 (Pegi: +3)</li>', 0, 9, 'img/microsoft_flight.jpg'),
('X3: Reunion','<li>La saga X vuelve con esta tercera entrega, estrenando un nuevo engine mucho más potente. Con esta entrega sus creadores pretenden “dar” a todos los fans de este simulador espacial las demandas que llevan tiempo solicitando.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: EgoSoft</li>
<li>Distribuidor: LudisGames</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Inglés</li></ul>
</li>
<li>Lanzamiento: 11 de agosto de 2006 (Pegi: +12)</li>', 0, 9, 'img/x3.jpg'),
('JASF','<li>En Jane‘s Advanced Strike Fighters los jugadores combaten para poner fin a la guerra civil que se libra en el estado ficticio de Azbaristan. La acción se desarrolla en 16 misiones, cubre 65.000 kilómetros cuadrados de montañas, desierto y suelo urbano e incluye 30 cazas de EE. UU., Rusia, Europa y China.</li>
<li>Plataforma: PlayStation 3 - PC - X360</li>
<li>Desarrollador: Evolved Games</li>
<li>Distribuidor: Koch Media</li>
<li>Lanzamiento: 2 de marzo de 2012 (Pegi: +7)</li>', 0, 9, 'img/jasf.jpg'),
('Silent Hunter 5','<li>Quinta entrega de la popular serie bélica de combate submarino caracterizada por su realismo e inmersión.</li>
<li>Plataforma: PC</li>
<li>Desarrollador: Ubisoft Romania</li>
<li>Distribuidor: UbiSoft</li>
<li>Jugadores: 1; (8 online) (Competitivo: 8)</li>
<li>Idioma:<ul>
	<li>Manual: Español</li>
	<li>Textos: Español</li>
	<li>Voces: Inglés</li></ul>
</li>
Lanzamiento: 11 de marzo de 2010 (Pegi: +12)</li>', 0, 9, 'img/silent_hunter_5.jpg');


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
