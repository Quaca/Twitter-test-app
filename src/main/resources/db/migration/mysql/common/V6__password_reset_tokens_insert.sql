CREATE TABLE `password_reset_tokens`
(
    `id`      bigint NOT NULL,
    `expiry_date`  datetime(6)  DEFAULT NULL,
    `token`   varchar(255) DEFAULT NULL,
    `user_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `password_reset_token_user_id_key` (`user_id`),
    CONSTRAINT `password_reset_token_user_id_key` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;