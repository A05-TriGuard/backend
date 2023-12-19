create table `triguard`.`db_medicine_favorites` (
    `id` int(11) not null auto_increment,
    `account_id` int(11) not null ,
    `medicine_id` int(11) not null ,
    `created_at` datetime not null default current_timestamp,
    primary key (`id`),
    unique key `account_id_medicine_id` (`account_id`,`medicine_id`),
    constraint `fk_medicine_favorites_account_id` foreign key (`account_id`) references `db_account` (`id`) on delete cascade on update cascade,
    constraint `fk_medicine_favorites_medicine_id` foreign key (`medicine_id`) references `db_medicine` (`id`) on delete cascade on update cascade
) engine=InnoDB auto_increment=1 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci row_format = dynamic;