create table `triguard`.`db_account`  (
    `id` int not null auto_increment,
    `username` varchar(255) character set utf8mb4 collate utf8mb4_0900_ai_ci null default null,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci null default null,
    `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci null default null,
    `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci null default null,
    `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci null default null,
    `create_time` datetime null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;