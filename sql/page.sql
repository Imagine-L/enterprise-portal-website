drop table if exists ms_page_plate;
create table if not exists ms_page_plate
(
    pid         bigint primary key comment '板块编号',
    plate_name  varchar(20) comment '板块标题',
    description text comment '板块描述',
    image       varchar(200) comment '板块图片',
    link        varchar(300) comment '板块链接',
    plate_type  tinyint not null default 1 comment '板块类型(0左对齐, 1居中, 2右对齐)',
    bind_nav    bigint  not null comment '绑定的栏目编号',
    disabled    bit     not null default 0 comment '是否禁用',
    released bit not null default 0 comment '是否发布',
    order_seed  tinyint not null default 0 comment '排序种子',
    create_by   bigint comment '创建者',
    create_time datetime         default now() comment '创建时间',
    update_by   bigint comment '更新者',
    update_time datetime         default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='页面板块表';
