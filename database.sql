#hdms_user 用户信息表
create table hdms_user(
    `id` int unsigned not null auto_increment comment '用户id',
    `email` varchar(25) not null comment '用户邮箱',
    `name` varchar(100) not null comment '用户昵称',
    `password` varchar(200) not null comment '密码',
    `role` int unsigned not null comment '用户角色 1=网格员，2=网格长，3=安监办，4=安监长，5=系统管理员',
    `live` smallint  default 0 not null comment '账号是否激活',
    `mobile` varchar(11) null comment '手机号',
    `remark` varchar(200) null comment '个人备注',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (id),
    unique index (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#hdms_type 隐患类型表
create table hdms_type(
    `id` int unsigned not null auto_increment comment '分组id',
    `name` varchar(15) not null comment '分组名',
    `description` varchar(100) not null comment '隐患分类说明',
    `process_id` varchar(64) not null comment '流程定义id',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

#hdms_role 系统角色表
create table hdms_role(
    `id` int unsigned not null auto_increment comment 'id',
    `name` varchar(30) not null comment '角色名',
    `description` varchar(100) not null comment '说明',
    `question` smallint default 1  not null comment '是否参与系统问题类型分配(1=参与，0=不参与)',
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

#用户-隐患类型绑定表
create table `hdms_user_type` (
    `id` int unsigned not null auto_increment comment 'id',
    `user_id` int unsigned not null comment '用户id',
    `type_id` int unsigned not null comment '类型id',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;