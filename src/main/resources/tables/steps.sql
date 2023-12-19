create table `triguard`.`db_steps` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `steps` int null default null,
    `date` varchar(255) null default null,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;