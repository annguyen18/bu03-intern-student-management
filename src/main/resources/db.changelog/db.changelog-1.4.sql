-- liquibase formatted sql
-- changeset feature-manh:1.4

-- intern.`classSchedule` definition
CREATE TABLE `ClassSchedule` (
                                 `id` BIGINT not null ,
                                 `dayOfDate` VARCHAR(255),
                                 `startTime` TIME,
                                 `endTime` TIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
