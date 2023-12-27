create table `triguard`.`db_meal_goal` (
    `id` int not null auto_increment,
    `account_id` int null default null,
    `calories` int null default null,
    `carbohydrates` int null default null,
    `lipids` int null default null,
    `cholesterol` int null default null,
    `proteins` int null default null,
    `fiber` int null default null,
    `sodium` int null default null,
    `create_time` datetime null default current_timestamp,
    primary key (`id`) using btree
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;