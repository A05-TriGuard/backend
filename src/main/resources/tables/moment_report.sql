create table `triguard`.`db_moment_report` (
    `id` int(11) not null auto_increment,
    `account_id` int(11) not null,
    `moment_id` int(11) not null,
    `reason` varchar(255) not null,
    `created_at` datetime not null default current_timestamp,
    primary key (`id`) using btree,
    key `account_id` (`account_id`),
    key `moment_id` (`moment_id`),
    constraint `db_moment_report_ibfk_1` foreign key (`account_id`) references `db_account` (`id`) on delete cascade on update cascade,
    constraint `db_moment_report_ibfk_2` foreign key (`moment_id`) references `db_moment` (`id`) on delete cascade on update cascade
) engine = innodb auto_increment = 1 character set = utf8mb4 collate = utf8mb4_0900_ai_ci row_format = dynamic;