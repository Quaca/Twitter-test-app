CREATE TABLE `roles`
(
    `id`   bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `roles`
VALUES (1, "ROLE_USER"),
       (2, "ROLE_ADMIN"),
       (3, "ROLE_SUPER_ADMIN");

CREATE TABLE `users_roles`
(
    `user_id` bigint NOT NULL,
    `roles_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `roles_id`),
    KEY `user_id_foreign_key` (`user_id`),
    KEY `role_id_foreign_key` (`roles_id`),
    CONSTRAINT user_id_foreign_key FOREIGN KEY (user_id) REFERENCES `users` (`id`),
    CONSTRAINT role_id_foreign_key FOREIGN KEY (roles_id) REFERENCES `roles` (`id`)

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

