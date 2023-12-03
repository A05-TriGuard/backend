create table `triguard`.`db_article_favorites` (
    `id` int(11) not null auto_increment,
    `account_id` int(11) not null,
    `article_id` int(11) not null,
    `article_type` varchar(255) null default null,
    `created_at` datetime not null default current_timestamp,
    primary key (`id`),
    unique key `account_id_article_id` (`account_id`,`article_id`),
    constraint `fk_article_favorites_account_id` foreign key (`account_id`) references `db_account` (`id`) on delete cascade on update cascade,
    constraint `fk_article_favorites_article_id` foreign key (`article_id`) references `db_article` (`id`) on delete cascade on update cascade
) engine=InnoDB auto_increment=1 default charset=utf8mb4 collate=utf8mb4_unicode_ci row_format = dynamic;