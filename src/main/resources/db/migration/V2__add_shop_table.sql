CREAtE TABLE `shop`
(
    `id`            BIGINT          NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(255)    NOT NULL ,
    `image`         VARCHAR(255)             ,
    `bellscore`         BIGINT                   ,
    `latitude`      DECIMAL                  ,
    `longitude`     DECIMAL                  ,
    `address`       VARCHAR(255)             ,
    `runtime`       VARCHAR(255)             ,
    `created_at`    DATETIME                 ,
    `modified_at`    DATETIME                 ,
    PRIMARY KEY (`id`)
)   DEFAULT CHARSET = UTF8MB4;


CREATE TABLE `reservation_timetable`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT ,
    `shop_id`           BIGINT          NOT NULL ,
    `reservation_date`  VARCHAR(255)    NOT NULL ,
    `time_start`        DATETIME        NOT NULL ,
    `time_end`          DATETIME        NOT NULL ,
    `team`              BIGINT          NOT NULL ,
    `people`            BIGINT          NOT NULL ,
    `check`             VARCHAR(255)    NOT NULL ,
    `created_at`        DATETIME                 ,
    `modified_at`        DATETIME                 ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`shop_id`) REFERENCES `shop` (id)
)   DEFAULT CHARSET = UTF8MB4;

CREATE TABLE `follow_shop`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT ,
    `user_id`           BIGINT          NOT NULL ,
    `shop_id`           BIGINT          NOT NULL ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (id),
    FOREIGN KEY (`shop_id`) REFERENCES `shop` (id)
)   DEFAULT CHARSET = UTF8MB4;

CREATE TABLE `shop_menu`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT ,
    `food_id`           BIGINT          NOT NULL ,
    `shop_id`           BIGINT          NOT NULL ,
    `menu`              VARCHAR(255)    NOT NULL ,
    `price`             BIGINT          NOT NULL ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`food_id`) REFERENCES `food`  (id),
    FOREIGN KEY (`shop_id`) REFERENCES `shop`  (id)
)   DEFAULT CHARSET = UTF8MB4;

CREATE TABLE `feed_post`
(
    `id`                BIGINT          NOT NULL AUTO_INCREMENT ,
    `content`           MEDIUMTEXT      ,
    `image`             VARCHAR(255)    ,
    `shop_id`           BIGINT          NOT NULL ,
    `created_at`        DATETIME        ,
    `modified_at`        DATETIME        ,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`shop_id`) REFERENCES `shop` (id)
)   DEFAULT CHARSET = UTF8MB4;