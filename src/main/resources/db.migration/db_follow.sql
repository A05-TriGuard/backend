create table `triguard`.`db_follow` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `follow_id` int null default null,
    `create_time` datetime null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;