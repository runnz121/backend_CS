CREATE TABLE `user`
(
    `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `nickname`      VARCHAR(255) NOT NULL,
    `email`         VARCHAR(255) NOT NULL,
    `password`      VARCHAR(255) NOT NULL,
    `phonenumber`   VARCHAR(255) NOT NULL,
    `profileimg`     VARCHAR(255) NOT NULL
) DEFAULT CHARSET = utf8;