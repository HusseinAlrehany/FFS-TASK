--Auther Script
CREATE TABLE IF NOT EXISTS `author` (
`id` INT AUTO_INCREMENT ,
`name` VARCHAR(50) ,
PRIMARY KEY (id)
);

INSERT INTO `author` 
VALUES (1,'Herman'),
       (2,'John'),
       (3,'Ernest'),
       (4,'Emily');
+++++++++++++++++++++++++++++++++++++
--Book Script
CREATE TABLE IF NOT EXISTS `book` (
`id` INT AUTO_INCREMENT,
`name` VARCHAR(50),
`type` ENUM ('SCIENCE', 'HISTORY', 'FICTION'),
`serial_number` VARCHAR(255),
`creation_date` DATETIME(6),
`last_updated` DATETIME(6),
`price` BIGINT,
`author_id` INT NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES
author (id)
);

