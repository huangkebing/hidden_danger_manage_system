#hdms_user 用户信息表
create table hdms_user(
    `id` int unsigned not null auto_increment comment '用户id',
    `email` varchar(25) not null comment '用户邮箱',
    `name` varchar(15) not null comment '用户昵称',
    `password` varchar(20) not null comment '密码',
    `role` int unsigned not null comment '用户角色 1=网格员，2=网格长，3=安监办，4=安监长，5=系统管理员',
    `group` varchar(30) not null comment '用户分组',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (id),
    unique index (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#hdms_group 分组表
create table hdms_group(
    `id` int unsigned not null auto_increment comment '分组id',
    `owner` varchar(25) not null comment '分组创建人邮箱',
    `name` varchar(15) not null comment '分组名',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (id),
    unique index (owner)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#hdms_role 系统角色表
create table hdms_role(
    `id` int unsigned not null auto_increment comment 'id',
    `name` varchar(15) not null comment '角色名',
    `description` varchar(15) not null comment '说明',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#hdms_system_menu 系统资源表
create table `hdms_system_menu` (
    `id` int unsigned not null auto_increment comment 'id',
    `pid` int unsigned not null default '0' comment '父id',
    `title` varchar(100) not null default '' comment '名称',
    `icon` varchar(100) not null default '' comment '菜单图标',
    `href` varchar(100) not null default '' comment '链接',
    `target` varchar(20) not null default '_self' comment '链接打开方式',
    `sort` int default '0' comment '菜单排序',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#角色-资源绑定表
create table `hdms_role_menu` (
    `id` int unsigned not null auto_increment comment 'id',
    `role_id` int unsigned not null comment '角色id',
    `menu_id` int unsigned not null comment '资源id',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;