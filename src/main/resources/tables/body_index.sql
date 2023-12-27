create table `triguard`.`db_body_index` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `sex` varchar(255) null default null,
    `age` int null default null,
    `height` int null default null,
    `weight` int null default null,
    `level` int null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;