drop table if exists ms_navigation;
create table if not exists ms_navigation
(
    nid         bigint primary key comment '栏目编号',
    nav_name    varchar(10) not null comment '栏目名',
    level       tinyint     not null default 1 comment '栏目级别',
    parent_id   bigint comment '父栏目编号',
    image       varchar(200) comment '栏目图片路径',
    description text comment '栏目描述',
    allow_del   bit         not null default 1 comment '是否允许删除',
    used     bit         not null default 0 comment '是否可用',
    disabled bit         not null default 0 comment '是否禁用',
    showed   bit         not null default 0 comment '是否首页展示',
    nav_type    tinyint     not null comment '栏目类型(0父栏目, 1页面, 2文章)',
    order_seed  tinyint     not null default 0 comment '排序种子',
    create_by   bigint comment '创建者',
    create_time datetime             default now() comment '创建时间',
    update_by   bigint comment '更新者',
    update_time datetime             default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='栏目表';

insert into ms_navigation(nid, nav_name, level, parent_id, image, description, allow_del, used, disabled,
                          showed, nav_type)
values (1, '通知公告', 1, null, '/www/data/img/default.png', '在这里我们会发布工作室的日常通知', 0, 1, 0, 0, 0);

select *
from ms_navigation;