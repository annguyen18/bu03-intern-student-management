
-- intern.`classSchedule` definition
CREATE TABLE `Class_Schedule` (
                                 `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
                                 `DAY_OF_WEEK` INT(1) DEFAULT NULL,
                                 `START_PERIOD` INT(2) DEFAULT NULL,
                                 `END_PERIOD` INT(2) DEFAULT NULL,
                                 PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
