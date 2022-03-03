CREATE TABLE `comment_img`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT ,
    `comment_id`        BIGINT          NOT NULL ,
    `img_url`           VARCHAR(255)             ,
    `created_at`        DATETIME                 ,
    `modified_at`        DATETIME                 ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`comment_id`) REFERENCES `comment` (id)
)   DEFAULT CHARSET = UTF8MB4;

-- ALTER TABLE `comment`ADD `comment_img_id` BIGINT AFTER `user_id`;
ALTER TABLE `comment`ADD `visible` VARCHAR(255) AFTER `user_id`;
ALTER TABLE `comment`ADD `content` TEXT AFTER `visible`;