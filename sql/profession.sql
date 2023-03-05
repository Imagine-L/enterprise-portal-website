drop table if exists ms_profession;
create table ms_profession
(
    pid         bigint primary key comment '岗位编号',
    name        varchar(20) unique not null comment '岗位名称',
    description varchar(100) comment '岗位描述',
    allow_del   bit                not null default 1 comment '是否允许删除',
    create_by   bigint comment '创建者',
    create_time datetime                    default now() comment '创建时间',
    update_by   bigint comment '更新者',
    update_time datetime                    default now() comment '更新时间'
) engine = innodb
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci comment ='岗位表';

insert into ms_profession(pid, name, description)
values (1, '工作室总负责人', '负责工作室日常的管理'),
       (2, '工作室成员', '工作室成员');

select *
from ms_profession;

insert into `ms_profession` (`pid`, `name`, `description`) values (59128, 'P5', 'tF9xJ');
insert into `ms_profession` (`pid`, `name`, `description`) values (6, 'Ez7H', 'XXE');
insert into `ms_profession` (`pid`, `name`, `description`) values (24, '8jpK', 'uS17d');
insert into `ms_profession` (`pid`, `name`, `description`) values (45618738, 'qd7', 'CtBca');
insert into `ms_profession` (`pid`, `name`, `description`) values (12365109, 'QuoD', 'mE');
insert into `ms_profession` (`pid`, `name`, `description`) values (6269542, 'xgR7', 'I62I');
insert into `ms_profession` (`pid`, `name`, `description`) values (393044838, 'RNMjZ', '0bIYE');
insert into `ms_profession` (`pid`, `name`, `description`) values (781160593, '8CR', 'Caq6w');
insert into `ms_profession` (`pid`, `name`, `description`) values (482, 'ubZ', 'eEO');
insert into `ms_profession` (`pid`, `name`, `description`) values (2440626, 'Qbu', 'gNIQ');
insert into `ms_profession` (`pid`, `name`, `description`) values (23, 'PJ', 'DAD92');
insert into `ms_profession` (`pid`, `name`, `description`) values (2604201, 'LPw', 'VoSSI');
insert into `ms_profession` (`pid`, `name`, `description`) values (4, 'TH9', 'FD35');
insert into `ms_profession` (`pid`, `name`, `description`) values (6214260183, 'SJY8', 'yVHk');
insert into `ms_profession` (`pid`, `name`, `description`) values (61106002, 'sp', 'Wqv');
insert into `ms_profession` (`pid`, `name`, `description`) values (87369, 'jwjHL', '84o');
insert into `ms_profession` (`pid`, `name`, `description`) values (489169, 'fFh', 'e8Q4');
insert into `ms_profession` (`pid`, `name`, `description`) values (52242143, 'YjG', 'ZP');
insert into `ms_profession` (`pid`, `name`, `description`) values (9, 'jI', 'zncCH');
insert into `ms_profession` (`pid`, `name`, `description`) values (45707424, 'Wctl', 'QUX');