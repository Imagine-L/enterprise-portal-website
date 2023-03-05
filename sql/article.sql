drop table if exists ms_article;
create table if not exists ms_article
(
    aid          bigint primary key comment '文章编号',
    title        varchar(20) comment '文章标题',
    image        varchar(200) comment '文章图片',
    summary      varchar(200) comment '文章摘要',
    html_content text comment '文章html格式正文',
    nav_id       bigint  not null comment '文章发布的栏目',
    order_seed   tinyint not null default 0 comment '排序种子',
    released bit not null default 0 comment '是否发布',
    disabled bit         not null default 0 comment '是否禁用',
    create_by    bigint comment '创建者',
    create_time  datetime         default now() comment '创建时间',
    update_by    bigint comment '更新者',
    update_time  datetime         default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='文章表';