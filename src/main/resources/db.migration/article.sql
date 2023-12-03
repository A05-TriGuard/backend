create table `triguard`.`db_article` (
    `id` int(11) not null auto_increment,
    `type` varchar(255) not null ,
    `title` varchar(255) not null ,
    `subtitle` varchar(255) null default null,
    `cover` varchar(255) null default null,
    `content` text null default null,
    `created_at` datetime null default current_timestamp,
    `updated_at` datetime null default current_timestamp on update current_timestamp,
    primary key (`id`)
) engine=InnoDB auto_increment=1 default charset=utf8;