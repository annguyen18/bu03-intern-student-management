
-- intern.`department` definition
CREATE TABLE `department`(
                             `department_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             `name` VARCHAR(255) NOT NULL,
                             `location` VARCHAR(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;