create table `triguard`.`db_moment_comment` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `moment_id` int null default null,
    `content` varchar(255) null default null,
    `create_time` datetime null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;