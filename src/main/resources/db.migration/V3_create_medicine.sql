create table `triguard`.`db_medicine` (
    `id` int(11) not null auto_increment,
    `name` varchar(255) null default null,
    `component` varchar(255) null default null,
    `usage` varchar(255) null default null,
    `caution` varchar(255) null default null,
    `side_effect` varchar(255) null default null,
    `interaction` varchar(255) null default null,
    `expiry` varchar(255) null default null,
    `condition` varchar(255) null default null,
    `image` varchar(255) null default null,
    primary key (`id`)
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;