drop table if exists ms_upload_file;
create table ms_upload_file
(
    fid          bigint primary key comment '文件编号',
    filename     varchar(50)  not null default 'default' comment '文件名',
    network_path varchar(200) not null comment '网络路径',
    local_path   varchar(200) not null comment '本地路径',
    size         int          not null default 0 comment '文件大小(MB)',
    create_by    bigint comment '创建者',
    create_time  datetime              default now() comment '创建时间',
    update_by    bigint comment '更新者',
    update_time  datetime              default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='文件表';