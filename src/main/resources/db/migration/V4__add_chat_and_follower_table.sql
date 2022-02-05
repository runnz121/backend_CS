CREAtE TABLE `chat`
(
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `room_name`     VARCHAR(255)    NOT NULL ,
    `room_owner`    BIGINT          NOT NULL ,
    `consumer`      BIGINT                   ,
    `created_at`    DATETIME                 ,
    `modified_at`    DATETIME                 ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`room_owner`) REFERENCES `users` (id),
    FOREIGN KEY (`consumer`)   REFERENCES `users` (id)
)   DEFAULT CHARSET = UTF8MB4;



