create table `db_guard_group_member` (
    `id` int not null auto_increment,
    `group_id` int null default null,
    `account_id` int null default null,
    `role` varchar(255) null default null,
    `nickname` varchar(255) null default null,
    `created_at` datetime null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;