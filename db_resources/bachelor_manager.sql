-- MySQL Script generated by MySQL Workbench
-- 03/22/17 13:30:46
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bm_database
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bm_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bm_database` DEFAULT CHARACTER SET UTF8;
USE `bm_database` ;

-- -----------------------------------------------------
-- Table `bm_database`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`USER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(1000) NULL,
  `last_name` VARCHAR(1000) NULL,
  `email` VARCHAR(1000) NULL,
  `telephone` VARCHAR(1000) NULL,
  `salt` VARCHAR(1000) NULL,
  `enterprise_id` VARCHAR(1000) NULL,
  `access_level` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `bm_database`.`TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`TAG` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(1000) NOT NULL,
  `description` VARCHAR(10000) NULL,
  `type` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `bm_database`.`PROBLEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`PROBLEM` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `path` VARCHAR(10000) NULL,
  `user_id` INT NULL,
  `title` VARCHAR(10000) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_PROBLEM_USER1_idx` (`user_id` ASC),
  CONSTRAINT `fk_PROBLEM_USER1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bm_database`.`USER` (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`BACHELOR_GROUP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`BACHELOR_GROUP` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(1000) NULL,
  `problem_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_GROUP_PROBLEM1_idx` (`problem_id` ASC),
  CONSTRAINT `fk_GROUP_PROBLEM1`
    FOREIGN KEY (`problem_id`)
    REFERENCES `bm_database`.`PROBLEM` (`id`))

ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`GROUP_ASSOCIATE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`GROUP_ASSOCIATE` (
  `bachelor_group_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`bachelor_group_id`, `user_id`),
  INDEX `fk_Gruppe_has_Bruker_Bruker1_idx` (`user_id` ASC),
  INDEX `fk_Gruppe_has_Bruker_Gruppe1_idx` (`bachelor_group_id` ASC),
  CONSTRAINT `fk_Gruppe_has_Bruker_Gruppe1`
    FOREIGN KEY (`bachelor_group_id`)
    REFERENCES `bm_database`.`BACHELOR_GROUP` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE ,
  CONSTRAINT `fk_Gruppe_has_Bruker_Bruker1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bm_database`.`USER` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`PASSWORD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`PASSWORD` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pass_hash` VARCHAR(1000) NULL,
  `eid_hash` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`PROBLEM_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`PROBLEM_TAG` (
  `tag_id` INT NOT NULL,
  `problem_id` INT NOT NULL,
  PRIMARY KEY (`tag_id`, `problem_id`),
  INDEX `fk_Tag_has_Oppgave_Oppgave1_idx` (`problem_id` ASC),
  INDEX `fk_Tag_has_Oppgave_Tag1_idx` (`tag_id` ASC),
  CONSTRAINT `fk_Tag_has_Oppgave_Tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `bm_database`.`TAG` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Tag_has_Oppgave_Oppgave1`
    FOREIGN KEY (`problem_id`)
    REFERENCES `bm_database`.`PROBLEM` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`USER_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`USER_TAG` (
  `user_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `tag_id`),
  INDEX `fk_USER_has_TAG1_TAG1_idx` (`tag_id` ASC),
  INDEX `fk_USER_has_TAG1_USER1_idx` (`user_id` ASC),
  CONSTRAINT `fk_USER_has_TAG1_USER1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bm_database`.`USER` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_USER_has_TAG1_TAG1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `bm_database`.`TAG` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`GROUP_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`GROUP_TAG` (
  `bachelor_group_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`bachelor_group_id`, `tag_id`),
  INDEX `fk_BACHELOR_GROUP_has_TAG_TAG1_idx` (`tag_id` ASC),
  INDEX `fk_BACHELOR_GROUP_has_TAG_BACHELOR_GROUP1_idx` (`bachelor_group_id` ASC),
  CONSTRAINT `fk_BACHELOR_GROUP_has_TAG_BACHELOR_GROUP1`
    FOREIGN KEY (`bachelor_group_id`)
    REFERENCES `bm_database`.`BACHELOR_GROUP` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_BACHELOR_GROUP_has_TAG_TAG1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `bm_database`.`TAG` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)DEFAULT CHARACTER SET UTF8
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- PRIVILEGES --
USE bm_database;
GRANT ALL PRIVILEGES ON bm_database.* TO 'root'@'34.249.136.226' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bm_database.* TO 'root'@'170.251.113.193' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bm_database.* TO 'root'@'37.191.228.181' IDENTIFIED BY 'admin';


-- DEFAULT DATA --
INSERT INTO USER VALUES
  (1, 'David', 'Silva', 'merlin@mcfc.co.uk','40404040', '', 'silva.david', '0'),
  (2, 'Kevin', 'De Bruyne', 'gingerprince@mcfc.co.uk', '30303030','', 'de.bruyne.kevin', '0'),
  (3, 'Hakon', 'Smorvik', 'hakonsmorvik@hotmail.no', '98844823','', 'smorvik.hakon', '0'),
  (4, 'Duy', 'Nguyen', 'duynguyen@hotmail.no','94432647','','nguyen.duy','0'),
  (5, 'Kim', 'Vu', 'kimvu@hotmail.no','42149144','','vu.kim','0'),
  (6, 'Adrian', 'Melsom', 'adrianmelsom@hotmail.no','234792348','','melsom.adrian','0'),
  (7, 'Jostein', 'Guldal', 'guldal.jostein@accenture.no','23580522','','guldal.jostein','0'),
  (8, 'Joakim', 'Kartveit', 'kartveit.joakim@accenture.no','025839434','','kartveit.joakim','0'),
  (9, 'admin', 'admin', 'admin@admin','94432647', '', 'admin', '0');

INSERT INTO BACHELOR_GROUP VALUES
  (1,'City', null),
  (2,'Bachelor Manager', null);

INSERT INTO TAG VALUES
  (1,'Student', '', 'Rolle'),
  (2,'Veileder', 'Skal bistå med faglig hjelp for bachelorgruppen', 'Rolle'),
  (3,'2017', 'Fiscal Year', 'FY');

INSERT INTO GROUP_ASSOCIATE VALUES (1,1),(1,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8);
INSERT INTO USER_TAG VALUES(1,2),(7,2),(8,2),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8 ,3),(2,1),(3,1),(4,1),(5,1),(6,1);