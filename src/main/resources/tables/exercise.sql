create table `triguard`.`db_exercise` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `type` int null default null,
    `start_time` varchar(255) null default null,
    `end_time` varchar(255) null default null,
    `duration` int not null default 0,
    `feelings` int null default null,
    `remark` varchar(255) null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;