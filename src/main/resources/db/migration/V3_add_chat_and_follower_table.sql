# ALTER TABLE `users`
#     ADD COLUMN `owner`      BIGINT             ,
#     ADD COLUMN `consumer`   BIGINT             ;


CREATE TABLE `chat`
(
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `room_name`     VARCHAR(255)    NOT NULL ,
    `room_owner`    BIGINT                   ,
    `consumer`      BIGINT                   ,
    `group_id`      VARCHAR(255)             ,
    `created_at`    DATETIME                 ,
    `modified_at`    DATETIME                 ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`room_owner`) REFERENCES `users` (id),
    FOREIGN KEY (`consumer`)   REFERENCES `users` (id)
)   DEFAULT CHARSET = UTF8MB4;


ALTER TABLE `chat`
    CHANGE `consumer`   `consumer_id`   BIGINT,
    CHANGE `room_owner` `room_owner_id` BIGINT;


