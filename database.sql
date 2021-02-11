#hdms_user 用户信息表
create table hdms_user(
    `id` int unsigned not null auto_increment comment '用户id',
    `email` varchar(25) not null comment '用户邮箱',
    `name` varchar(15) not null comment '用户昵称',
    `password` varchar(20) not null comment '密码',
    `role` int unsigned not null comment '用户角色 0=技术人员，1=资深人员，2=主管',
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

