drop table if exists ms_home_chart;
create table if not exists ms_home_chart
(
    hid   bigint primary key comment '轮播图编号',
    image varchar(200) not null comment '轮播图片',
    link  varchar(300) comment '轮播图链接',
    create_by   bigint comment '创建者',
    create_time datetime             default now() comment '创建时间',
    update_by   bigint comment '更新者',
    update_time datetime             default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='栏目表';