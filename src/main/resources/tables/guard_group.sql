create table `triguard`.`db_guard_group` (
    `id` int not null auto_increment,
    `name` varchar(255) null default null,
    `created_by` int null default null,
    `created_at` datetime not null default current_timestamp,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;