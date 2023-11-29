create table `triguard`.`db_blood_sugar` (
    `id` int(11) not null auto_increment,
    `account_id` int(11) not null,
    `bs` float null default null,
    `meal` int null default null,
    `feeling` int null default null,
    `remark` varchar(255) null default null,
    `date` varchar(255) null default null,
    `time` varchar(255) null default null,
    `create_time` datetime null default null,
    primary key (`id`)
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;