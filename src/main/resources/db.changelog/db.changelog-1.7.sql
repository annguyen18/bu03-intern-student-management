CREATE TABLE `notifications`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `title` varchar(50) NOT NULL,
                                `content` varchar(50) NOT NULL,
                                `created_date` datetime DEFAULT NULL,
                                PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
