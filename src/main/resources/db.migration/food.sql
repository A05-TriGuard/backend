create table `triguard`.`db_food` (
    `id` int(11) not null auto_increment,
    `name` varchar(255) null default null,
    `calories` int(11) null default null,
    `carbohydrates` int(11) null default null,
    `lipids` int(11) null default null,
    `cholesterol` int(11) null default null,
    `proteins` int(11) null default null,
    `fiber` int(11) null default null,
    `sodium` int(11) null default null,
    primary key (`id`)
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;