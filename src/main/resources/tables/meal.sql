create table `triguard`.`db_meal` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `date` varchar(255) null default null,
    `category` varchar(255) null default null,
    `food` varchar(255) null default null,
    `calories` int null default null,
    `weight` int null default null,
    `create_time` datetime not null default current_timestamp,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;