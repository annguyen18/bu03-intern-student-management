-- liquibase formatted sql
-- changeset CUONGLV40:1.0

-- intern.`user` definition

CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `created_by` varchar(50) NOT NULL,
                        `created_date` datetime DEFAULT NULL,
                        `last_modified_by` varchar(50) DEFAULT NULL,
                        `last_modified_date` datetime DEFAULT NULL,
                        `avatar` varchar(255) DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `status` int(11) DEFAULT NULL,
                        `username` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username_un` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- intern.authority definition

CREATE TABLE `authority` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `created_by` varchar(50) NOT NULL,
                             `created_date` datetime DEFAULT NULL,
                             `last_modified_by` varchar(50) DEFAULT NULL,
                             `last_modified_date` datetime DEFAULT NULL,
                             `name` varchar(255) DEFAULT NULL,
                             `status` int(11) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- intern.user_authority definition

CREATE TABLE `user_authority` (
                                  `user_id` bigint(20) NOT NULL,
                                  `authority_id` bigint(20) NOT NULL,
                                  KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
                                  KEY `FKpqlsjpkybgos9w2svcri7j8xy` (`user_id`),
                                  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
                                  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- intern.param_config definition

CREATE TABLE `param_config` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `description` varchar(255) DEFAULT NULL,
                                `env` varchar(255) DEFAULT NULL,
                                `param_key` varchar(255) DEFAULT NULL,
                                `param_value` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--Create admin
INSERT INTO intern.`user`(id,created_by, created_date, email, password, status, username)
VALUES(1,'ADMIN',SYSDATE() ,"email@gmail.com","$2a$10$slBW/ZjbA2fP/Xc06i0x8ui6GTk58R/Ar.lvNr4EO.wQhngtEHKXe","1", "ADMIN");

INSERT INTO intern.authority(id,created_by, created_date, name, status)
VALUES(1,'admin', sysdate(), "admin", "1");

INSERT INTO intern.user_authority(user_id, authority_id)
VALUES(1, 1);
