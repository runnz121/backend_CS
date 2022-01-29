CREAtE TABLE `users`
(
    `id`            BIGINT(20)      NOT NULL AUTO_INCREMENT,
    `nickname`      VARCHAR(255)    NOT NULL ,
    `email`         VARCHAR(255)    NOT NULL ,
    `password`      VARCHAR(255)    NOT NULL ,
    `phone_number`  VARCHAR(255)    NOT NULL ,
    `profile_img`    VARCHAR(255)             ,
    `user_roles`    VARCHAR(255)             ,
    `auth_provider` VARCHAR(255)             ,
    `refresh_token` VARCHAR(255)             ,
    `provider_id`   VARCHAR(255)             ,
    `created_at`    DATETIME                 ,
    `modified_at`    DATETIME                 ,
    PRIMARY KEY (`id`)
)   DEFAULT CHARSET = utf8;

CREATE TABLE `user_roles`
(
    `user_id`       BIGINT(20)      NOT NULL AUTO_INCREMENT,
    `roles`         VARCHAR(255)    NOT NULL ,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
)   DEFAULT CHARSET = utf8;

CREATE TABLE `food`
(
    `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(255)               ,
    `image`         VARCHAR(255)               ,
    `created_at`    DATETIME                   ,
    `modified_at`    DATETIME                   ,

    PRIMARY KEY (`id`)
)   DEFAULT CHARSET = utf8;

CREAtE TABLE `cooltime`
(
    `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `start_date`    DATETIME    NOT NULL       ,
    `end_date`      DATETIME    NOT NULL       ,
    `gauge`         VARCHAR(255)               ,
    `created_at`    DATETIME                   ,
    `modified_at`    DATETIME                   ,
    `food_id`       BIGINT(20)                 ,
    `user_id`       BIGINT(20)  NOT NULL       ,
    FOREIGN KEY (`food_id`) REFERENCES `food` (id),
    FOREIGN KEY (`user_id`) REFERENCES `users`(id),
    PRIMARY KEY (`id`)
)   DEFAULT CHARSET = utf8;

