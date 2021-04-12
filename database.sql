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

#流程节点-用户id绑定表
create table `hdms_process_node_role` (
    `id` int unsigned not null auto_increment comment 'id',
    `node_id` varchar(128) not null comment '流程节点id',
    `process_id` varchar(64) not null comment '流程定义id',
    `name` varchar(64) default '' not null comment '节点名称',
    `role_id` int unsigned not null default 0 comment '角色id，默认0代表仅发起人',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#问题表
create table `hdms_problem` (
    `id` int unsigned not null auto_increment comment 'id',
    `name` varchar(128) not null comment '问题名称',
    `description` varchar(1024) not null comment '问题描述',
    `priority` int not null default 2 comment '优先级',
    `type_id` int not null comment '问题类型',
    `instance_id` varchar(64) not null comment '流程实例id',
    `user_id` int not null comment '创建人id',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#流程变量-用户节点绑定表
create table `hdms_process_variable` (
     `id` int unsigned not null auto_increment comment 'id',
     `node_id` varchar(128) not null comment '流程节点id',
     `node_name` varchar(100) not null comment '流程节点名',
     `process_id` varchar(64) not null comment '流程定义id',
     `name` varchar(64) default '' not null comment '变量名',
     `type` int unsigned not null default 0 comment '变量类型，1=二元型，2=数值',
     `title` varchar(200) not null comment '流程变量提示文字',
     `tip0` varchar(200) null comment '二元型 value=0提示文字',
     `tip1` varchar(200) null comment '二元型value=1提示文字',
     `begin_variable` int default 0 not null comment '是否开始事件需要的变量，0=不是 1=是',
     `create` timestamp not null comment '创建时间',
     `modify` timestamp not null comment '修改时间',
     primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#问题信息表
create table `hdms_problem_info` (
    `id` int unsigned not null auto_increment comment 'id',
    `problem_id` int not null comment '问题id',
    `context` longtext not null comment '内容',
    `type` int unsigned not null default 0 comment '变量类型，1=文字，2=图片路径，3=文件路径',
    `user_id` int not null comment '添加者id',
    `username` varchar(100) null comment '用户名称',
    `email` varchar(25) null comment '用户邮箱',
    `create` timestamp not null comment '创建时间',
    `modify` timestamp not null comment '修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;