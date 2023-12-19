create table `triguard`.`db_guard` (
    `id` int(11) not null auto_increment,
    `ward_id` int(11) not null,
    `ward_nickname` varchar(255) null default null,
    `guardian_id` int(11) not null,
    `guardian_nickname` varchar(255) null default null,
    `is_accepted` int(11) not null default 0,
    `created_at` timestamp null default current_timestamp,
    primary key (`id`)
) engine=innodb auto_increment=1 default charset=utf8mb4 collate=utf8mb4_0900_ai_ci row_format = dynamic;