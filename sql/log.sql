drop table if exists ms_log;
create table if not exists ms_log
(
    lid            bigint primary key comment '日志编号',
    module         varchar(20) comment '操作的模块',
    request_mode   varchar(6) comment '请求方式',
    request_addr   varchar(100) comment '请求地址',
    request_json   text comment '请求json参数',
    request_user   bigint comment '请求用户编号',
    handler_method varchar(100) comment '处理的方法',
    success        bit      default 1 comment '请求是否成功',
    oper_time      datetime default now() comment '操作时间',
    runtime      datetime default now() comment '执行时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='日志表';