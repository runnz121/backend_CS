CREATE TABLE `reservation`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT,
    `reserved_date`     DATETIME        NOT NULL ,
    `reserved_people`   BIGINT          NOT NULL ,
    `reserved_state`    VARCHAR(255)             ,
    `user_id`           BIGINT          NOT NULL ,
    `shop_id`           BIGINT          NOT NULL ,
    `show_check`        BOOLEAN                  ,
    `created_at`        DATETIME             ,
    `modified_at`        DATETIME             ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`)     REFERENCES `users`   (id),
    FOREIGN KEY (`shop_id`)     REFERENCES `shop`    (id)
--     FOREIGN KEY (`comment_id`)  REFERENCES `comment` (id)
)   DEFAULT CHARSET = UTF8MB4;



CREATE TABLE `comment`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT,
    `score`             BIGINT               ,
    `state`             VARCHAR(255)         ,
    `user_id`           BIGINT               ,
    `reservation_id`    BIGINT               ,
    `created_at`        DATETIME             ,
    `modified_at`        DATETIME             ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`)         REFERENCES `users`   (id),
    FOREIGN KEY (`reservation_id`)  REFERENCES `reservation` (id)
)   DEFAULT CHARSET = UTF8MB4;





