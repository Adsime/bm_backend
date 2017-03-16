-- MySQL Script generated by MySQL Workbench
-- 03/16/17 15:50:40
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
CREATE SCHEMA IF NOT EXISTS `bm_database` DEFAULT CHARACTER SET utf8 ;
USE `bm_database` ;

-- -----------------------------------------------------
-- Table `bm_database`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`USER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL,
  `last_name` VARCHAR(50) NULL,
  `email` VARCHAR(50) NULL,
  `salt` VARCHAR(16) NULL,
  `enterprise_id` VARCHAR(45) NULL,
  `access_level` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`TAG` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(40) NOT NULL,
  `description` VARCHAR(200) NULL,
  `type` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`PROBLEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`PROBLEM` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `path` VARCHAR(100) NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_PROBLEM_USER1_idx` (`user_id` ASC),
  CONSTRAINT `fk_PROBLEM_USER1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bm_database`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`BACHELOR_GROUP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`BACHELOR_GROUP` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NULL,
  `problem_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_GROUP_PROBLEM1_idx` (`problem_id` ASC),
  CONSTRAINT `fk_GROUP_PROBLEM1`
    FOREIGN KEY (`problem_id`)
    REFERENCES `bm_database`.`PROBLEM` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gruppe_has_Bruker_Bruker1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bm_database`.`USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bm_database`.`PASSWORD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bm_database`.`PASSWORD` (
  `id` INT NOT NULL,
  `pass_hash` VARCHAR(64) NULL,
  `eid_hash` VARCHAR(64) NULL,
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tag_has_Oppgave_Oppgave1`
    FOREIGN KEY (`problem_id`)
    REFERENCES `bm_database`.`PROBLEM` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_has_TAG1_TAG1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `bm_database`.`TAG` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BACHELOR_GROUP_has_TAG_TAG1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `bm_database`.`TAG` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
