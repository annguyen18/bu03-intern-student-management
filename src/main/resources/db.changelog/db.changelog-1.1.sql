
-- intern.`course` definition
CREATE TABLE `COURSE` (
                           `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
                           `NAME` VARCHAR(255) DEFAULT NULL,
                          `CREDIT_COUNT` INT(1) DEFAULT NULL,
                          `CODE` VARCHAR(20) DEFAULT NULL,
                          `MAX_STUDENT_COUNT` INT(2) DEFAULT NULL,
                           `CREATED_BY` varchar(50) NOT NULL,
                           `CREATED_DATE` datetime DEFAULT NULL,
                           `LAST_MODIFIED_BY` varchar(50) DEFAULT NULL,
                           `LAST_MODIFIED_DATE` datetime DEFAULT NULL,
                           `STATUS` BOOLEAN DEFAULT NULL,
                           `CLASSROOM` VARCHAR(51) DEFAULT NULL,
                           `LECTURER_ID` BIGINT(20) DEFAULT NULL,
                           `SCHEDULE_ID` BIGINT(20) DEFAULT NULL,
                           `DEPARTMENT_ID` BIGINT(20) DEFAULT NULL,
                           PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
