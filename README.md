#Leetcode backend application

## Documentation
https://tomoaki3284.atlassian.net/l/c/0br2ym2x

## Building
```shell script
mvn clean package
```

## Other require software
* MySQL Server (with correct tables)
to generate schema:
```
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `User_ID` INT NOT NULL AUTO_INCREMENT,
  `Email` VARCHAR(45) NOT NULL,
  `Username` VARCHAR(18) NOT NULL,
  `Password` VARCHAR(18) NOT NULL,
  `Creation_Date` DATE NOT NULL,
  PRIMARY KEY (`User_ID`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`Problem` (
  `Problem_ID` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(45) NOT NULL,
  `Difficulty` VARCHAR(10) NOT NULL,
  `Acceptance_Rate` DOUBLE NULL,
  `Type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Problem_ID`),
  UNIQUE INDEX `Problem_ID_UNIQUE` (`Problem_ID` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`UserProblemHistory` (
  `User_ID` INT NOT NULL,
  `Problem_ID` INT NOT NULL,
  `Solved_Time` INT NULL,
  `Note` VARCHAR(100) NULL,
  `Date_Solved` DATE NULL,
  PRIMARY KEY (`Problem_ID`, `User_ID`),
  UNIQUE INDEX `User_ID_UNIQUE` (`User_ID` ASC) VISIBLE,
  UNIQUE INDEX `Problem_ID_UNIQUE` (`Problem_ID` ASC) VISIBLE,
  CONSTRAINT `User_ID`
    FOREIGN KEY (`User_ID`)
    REFERENCES `mydb`.`User` (`User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Problem_ID`
    FOREIGN KEY (`Problem_ID`)
    REFERENCES `mydb`.`Problem` (`Problem_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
```

## How to run
#### Using Spring Boot Maven Plugin
```shell script
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```

## Configuration
Change the configuration to your own in the application.properties file. Below is the path:
```shell script
/medical-center-api/src/main/resources/application.properties
```
Configuration file (.properties) should look like this.
```
spring.r2dbc.ip=127.0.0.1
spring.r2dbc.port=3306
spring.r2dbc.username=root
spring.r2dbc.password=*******
spring.r2dbc.dbname=mydb
spring.r2dbc.initialization-mode=always
```
Modify these value to to your own value to run the program.

## Request
Here is the basic request:
```shell script
curl -X POST http://localhost:8081/auth/signup
   -H 'Content-Type: application/json'
   -d '{"username":"tomo","password":"1234", "email":"tomo@yahoo.com"}'
```