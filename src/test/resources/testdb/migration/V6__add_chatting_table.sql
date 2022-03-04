CREATE TABLE `chat`
(
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `room_id`       VARCHAR(255)    NOT NULL ,
    `room_name`     VARCHAR(255)             ,
    `maker_id`      BIGINT          NOT NULL ,
    `invite_id`     BIGINT          NOT NULL ,
    `type`          VARCHAR(255)    NOT NULL ,
    `created_at`    DATETIME                 ,
    `modified_at`    DATETIME                 ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`maker_id`)    REFERENCES `users`  (id),
    FOREIGN KEY (`invite_id`)   REFERENCES `users`  (id)
) DEFAULT CHARSET = UTF8MB4;


CREATE TABLE `chat_log`
(
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `room_id`       VARCHAR(255)    NOT NULL ,
    `sender`        BIGINT          NOT NULL ,
    `message`       VARCHAR(255)             ,
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = UTF8MB4;
