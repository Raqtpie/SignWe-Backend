CREATE DATABASE IF NOT EXISTS TuringTeam;

USE TuringTeam;

DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user
(
    id         varchar(35) primary key comment '主键, 用户微信的openid',
    class_name varchar(30) not null comment '用户所在班级',
    name       varchar(15) not null comment '用户真实姓名',
    avatar_url varchar(100) comment '用户头像url',
    permission int         not null comment '用户权限'
);

DROP TABLE IF EXISTS chair;
CREATE TABLE IF NOT EXISTS chair
(
    id      int auto_increment primary key comment '主键, 座位号',
    status  bool comment '可用状态',
    time    long comment '坐下时间',
    user_id varchar(30) comment '对应用户id'
);

DROP TABLE IF EXISTS record;
CREATE TABLE IF NOT EXISTS record
(
    id      int auto_increment primary key comment '主键',
    user_id varchar(35) comment '打卡记录对应用户id',
    time    int comment '时长',
    chair_id int comment '座位号',
    date    date comment '对应日期'
);

DROP TABLE IF EXISTS user_record;
CREATE TABLE IF NOT EXISTS user_record
(
    user_id     varchar(35) primary key comment '打卡记录对应用户id',
    total_count int comment '总次数',
    total_time  int comment '总时长'
);

DROP TABLE IF EXISTS laboratory_status;
CREATE TABLE IF NOT EXISTS laboratory_status
(
    id       int primary key comment '主键',
    status   bool not null comment '实验室开门状态',
    operator varchar(35) comment '操作者'
);

INSERT INTO laboratory_status (id, status, operator)
    VALUE (1, false, null);

INSERT INTO chair (id, status, time, user_id)
VALUES (1, false, null, null),
       (2, false, null, null),
       (3, false, null, null),
       (4, false, null, null),
       (5, false, null, null),
       (6, false, null, null),
       (7, false, null, null),
       (8, false, null, null),
       (9, false, null, null),
       (10, false, null, null),
       (11, false, null, null),
       (12, false, null, null);