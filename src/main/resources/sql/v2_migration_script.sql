-- Create the new 'images' table
CREATE TABLE IF NOT EXISTS `images` (
  `fish_id` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fish_id` (`fish_id`),
  CONSTRAINT `FK_fish_id` FOREIGN KEY (`fish_id`) REFERENCES `fish` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Migrate the 'image_file_name' data from 'fish' table to 'images' table
INSERT INTO `images` (`fish_id`, `file_name`)
SELECT `id`, `image_file_name`
FROM `fish`
WHERE `image_file_name` IS NOT NULL;

-- Drop the 'image_file_name' column from the 'fish' table
ALTER TABLE `fish`
DROP COLUMN `image_file_name`;

-- Create table users
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL UNIQUE,
  `role` enum('ADMIN','USER') NOT NULL,
  PRIMARY KEY (`id`)
)

-- Create data for users
INSERT INTO users (username, password, role) VALUES ('admin', 'admin', 'ADMIN');
INSERT INTO users (username, password, role) VALUES ('user', 'user', 'USER');