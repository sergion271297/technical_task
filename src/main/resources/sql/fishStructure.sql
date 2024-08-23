-- fishstore.fish definition

CREATE TABLE `fish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `catch_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- fishstore.users definition

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL UNIQUE,
  `role` enum('ADMIN','USER') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- fishstore.images definition

CREATE TABLE `images` (
  `fish_id` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fish_id` (`fish_id`),
  CONSTRAINT `FK_fish_id` FOREIGN KEY (`fish_id`) REFERENCES `fish` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
