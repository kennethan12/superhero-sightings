-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema superdbTest
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `superdbTest` ;

-- -----------------------------------------------------
-- Schema superdbTest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superdbTest` DEFAULT CHARACTER SET utf8 ;
USE `superdbTest` ;

-- -----------------------------------------------------
-- Table `superdbTest`.`Power`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superdbTest`.`Power` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `superdbTest`.`Super`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superdbTest`.`Super` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `description` VARCHAR(150) NULL,
  `powerId` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Super_Power1_idx` (`powerId` ASC),
  CONSTRAINT `fk_Super_Power1`
    FOREIGN KEY (`powerId`)
    REFERENCES `superdbTest`.`Power` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superdbTest`.`Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superdbTest`.`Location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(150) NULL,
  `address` VARCHAR(150) NOT NULL,
  `latitude` VARCHAR(10) NULL,
  `longitude` VARCHAR(10) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superdbTest`.`Organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superdbTest`.`Organization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(150) NULL,
  `address` VARCHAR(150) NULL,
  `contact` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superdbTest`.`Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superdbTest`.`Sighting` (
  `superId` INT NOT NULL,
  `locationId` INT NOT NULL,
  `date` DATETIME NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  INDEX `fk_Super_has_Location_Location1_idx` (`locationId` ASC),
  INDEX `fk_Super_has_Location_Super_idx` (`superId` ASC),
  CONSTRAINT `fk_Super_has_Location_Super`
    FOREIGN KEY (`superId`)
    REFERENCES `superdbTest`.`Super` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Super_has_Location_Location1`
    FOREIGN KEY (`locationId`)
    REFERENCES `superdbTest`.`Location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superdbTest`.`OrganizationMember`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superdbTest`.`OrganizationMember` (
  `organizationId` INT NOT NULL,
  `superId` INT NOT NULL,
  PRIMARY KEY (`organizationId`, `superId`),
  INDEX `fk_Organization_has_Super_Super1_idx` (`superId` ASC),
  INDEX `fk_Organization_has_Super_Organization1_idx` (`organizationId` ASC),
  CONSTRAINT `fk_Organization_has_Super_Organization1`
    FOREIGN KEY (`organizationId`)
    REFERENCES `superdbTest`.`Organization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Organization_has_Super_Super1`
    FOREIGN KEY (`superId`)
    REFERENCES `superdbTest`.`Super` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;