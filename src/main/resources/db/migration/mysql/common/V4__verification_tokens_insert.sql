CREATE TABLE `verification_tokens`
(
    `id`      bigint NOT NULL,
    `expiry_date`  datetime(6)  DEFAULT NULL,
    `token`   varchar(255) DEFAULT NULL,
    `user_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `token_user_id_key` (`user_id`),
    CONSTRAINT `token_user_id_key` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;