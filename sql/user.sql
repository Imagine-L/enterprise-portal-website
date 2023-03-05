drop table if exists ms_user;
create table if not exists ms_user
(
    uid           bigint primary key comment '用户编号',
    username      varchar(20) unique not null comment '用户名',
    nickname      varchar(30) comment '昵称',
    password      char(60)           not null comment '密码',
    gender        char                        default 0 comment '性别(0男, 1女)',
    age           tinyint comment '年龄',
    phone         char(11) comment '手机号',
    qq_number     varchar(15) comment 'qq号',
    email         varchar(30)        not null unique comment '邮箱',
    admin         bit                not null default 0 comment '是否为管理员',
    description   varchar(100) comment '描述',
    profession_id bigint             not null comment '用户岗位编号',
    allow_del     bit                not null default 1 comment '是否允许删除',
    locked        bit                not null default 0 comment '是否锁定',
    create_by     bigint comment '创建者',
    create_time   datetime                    default now() comment '创建时间',
    update_by     bigint comment '更新者',
    update_time   datetime                    default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='用户表';

insert into ms_user(uid, username, nickname, password, description, profession_id, allow_del)
values (1, 'admin', '工作室管理员', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', '系统管理员', 1, 0);

select *
from ms_user;

select username, name
from ms_user m,
     ms_profession p
where m.profession_id = p.pid;


insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (637845326, '2zph1', '何博超', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '18362965626', '1552495742', 'micaela.nolan@gmail.com', 0, 'bI', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (4, 'AC', '于煜祺', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '15608497190', '1552495742', 'lisandra.ledner@yahoo.com', 0, 'Lhe', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (77868009, 'GFet', '蒋雪松', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '14523514208', '1552495742', 'stacy.runolfsson@yahoo.com', 0, 'i8z0i', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (5, 'eOk', '于明哲', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '15671712955', '1552495742', 'mauro.satterfield@gmail.com', 0, 'QLE', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (196, 'gD9x', '江乐驹', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '17268511075', '1552495742', 'reyes.jerde@hotmail.com', 0, 'C1T', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (28, 'lOdh', '吕昊然', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '17724186705', '1552495742', 'leigh.ward@gmail.com', 0, '3yLT', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (3236, 'IU4q', '龚博文', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '15391062017', '1552495742', 'joellen.fadel@gmail.com', 0, 'IhNaX', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (357374, '7TrH', '于志泽', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '17075530149', '1552495742', 'catalina.bashirian@yahoo.com', 0, 'tkWWg', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (42877329, '4V1lU', '赖烨伟', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '15608763946', '1552495742', 'vena.rodriguez@hotmail.com', 0, 'Fg57Z', 2, 1, 0);
insert into `ms_user` (`uid`, `username`, `nickname`, `password`, `gender`, `age`, `phone`, `qq_number`, `email`, `admin`, `description`, `profession_id`, `allow_del`, `locked`) values (68882, '4Ov', '谢烨磊', '$2a$10$aiBx0V.PUULOxUqLvgwKe.6U3lIz.QU9U.3NDG7tHLQ4uEixQF.3i', 0, 20, '17780042966', '1552495742', 'corey.bauch@hotmail.com', 0, 'G4W', 2, 1, 0);
