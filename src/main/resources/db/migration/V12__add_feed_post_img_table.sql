ALTER TABLE `feed_post` CHANGE `image` `represent_img` VARCHAR(255);
ALTER TABLE `feed_post` ADD `title` VARCHAR(255) AFTER `content`;


CREATE TABLE `feed_post_img`
(
    `id`             BIGINT          NOT NULL AUTO_INCREMENT ,
    `feed_post_id`   BIGINT          NOT NULL ,
    `image`          VARCHAR(255)             ,
    `created_at`     DATETIME                 ,
    `modified_at`     DATETIME                 ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`feed_post_id`) REFERENCES `feed_post` (id)
)   DEFAULT CHARSET = UTF8MB4;