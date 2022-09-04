# CREATE TABLE `users` (
#     `id` bigint NOT NULL,
#     `country` varchar(255) DEFAULT NULL,
#     `name` varchar(255) DEFAULT NULL,
#     `surname` varchar(255) DEFAULT NULL,
#     PRIMARY KEY (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `followers` (
    `to_id` varchar(255) NOT NULL,
    `from_id` varchar(255) NOT NULL,
    PRIMARY KEY (`to_id`,`from_id`)
#      `to_id` bigint NOT NULL,
#      `from_id` bigint NOT NULL,
#      PRIMARY KEY (`to_id`,`from_id`),
#      KEY `FKt6wj8auxalhvqvjecv350hs1e` (`from_id`),
#      CONSTRAINT `FKjnoxodpbmalgo9utnfq1qctnr` FOREIGN KEY (`to_id`) REFERENCES `users` (`id`),
#      CONSTRAINT `FKt6wj8auxalhvqvjecv350hs1e` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tweets` (
    `id` bigint NOT NULL,
    `published_at` datetime(6) DEFAULT NULL,
    `text` varchar(255) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `user_id` varchar(255) DEFAULT NULL,
#     `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
#     KEY `FKgclwpsnjft4s6umfjopgcp051` (`user_id`),
#     CONSTRAINT `FKgclwpsnjft4s6umfjopgcp051` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comments` (
    `id` bigint NOT NULL,
    `tweet_id` bigint NOT NULL,
    `published_at` datetime(6) DEFAULT NULL,
    `text` varchar(255) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `user_id` varchar(255) DEFAULT NULL,
#     `user_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
#     KEY `FK2occdyecvmxwqjm9eu28a9x3n` (`tweet_id`),
#     KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
#     CONSTRAINT `FK2occdyecvmxwqjm9eu28a9x3n` FOREIGN KEY (`tweet_id`) REFERENCES `tweets` (`id`),
#     CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `hibernate_sequence` VALUES (1);