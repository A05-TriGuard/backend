create table `triguard`.`db_moment` (
    `id` int(11) not null auto_increment,
    `account_id` int(11) not null,
    `profile` varchar(255) not null,
    `username` varchar(255) not null,
    `date` varchar(255) not null,
    `content` text null default null,
    `images` text null default null,
    `video` varchar(255) null default null,
    `class` varchar(255) null default null,
    `category` varchar(255) null default null,
    `like_count` int(11) not null default 0,
    `comment_count` int(11) not null default 0,
    `favorite_count` int(11) not null default 0,
    `created_at` timestamp not null default current_timestamp,
    primary key (`id`),
    key `account_id` (`account_id`),
    constraint `db_moment_account_id_fk_1` foreign key (`account_id`) references `db_account` (`id`) on delete cascade on update cascade
)