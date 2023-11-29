create table `triguard`.`db_food_favorites` (
    `id` int(11) not null auto_increment,
    `account_id` int(11) not null,
    `food_id` int(11) not null,
    `created_at` datetime not null default current_timestamp,
    primary key (`id`),
    unique key `account_id_food_id` (`account_id`, `food_id`),
    constraint `fk_db_food_favorites_db_account1` foreign key (`account_id`) references `db_account` (`id`) on delete cascade on update cascade,
    constraint `fk_db_food_favorites_db_food1` foreign key (`food_id`) references `db_food` (`id`) on delete cascade on update cascade
) engine=InnoDB auto_increment=1 default charset=utf8mb4 collate=utf8mb4_unicode_ci row_format = dynamic;