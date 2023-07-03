CREATE TABLE `COURSE` (
                           `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
                           `NAME` VARCHAR(255) DEFAULT NULL,
                          `CREDIT_COUNT` INT(1) DEFAULT NULL,
                           `START_DATE` DATE DEFAULT NULL,
                           `END_DATE` DATE DEFAULT NULL,
                           `STATUS` BOOLEAN DEFAULT NULL,
                           `CLASSROOM` VARCHAR(51) DEFAULT NULL,
                           PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
