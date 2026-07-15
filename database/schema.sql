-- KnowFlow AI 数据库结构脚本
CREATE DATABASE IF NOT EXISTS knowflow
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE knowflow;
-- 系统用户表：保存登录账号、密码、角色和账号状态
create table if not exists sys_user
(
    id         bigint auto_increment
        primary key,
    username   varchar(50)                        not null,
    password   varchar(100)                       not null,
    nickname   varchar(50)                        not null,
    role       varchar(20)                        not null,
    status     tinyint  default 1                 not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint username
        unique (username)
);

-- 知识库文档表：保存文档标题、完整正文和创建信息
create table if not exists kb_document
(
    id         bigint auto_increment
        primary key,
    title      varchar(100)                       not null comment '文档标题',
    content    text                               not null comment '文档正文内容',
    created_by bigint                             null comment '创建用户ID',
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

-- 知识库文档切片表：按文档和切片序号保存用于检索的切片内容
create table if not exists kb_document_chunk
(
    id          bigint auto_increment comment '切片ID'
        primary key,
    document_id bigint                             not null comment '所属文档ID',
    chunk_index int                                not null comment '切片序号，从1开始',
    content     text                               not null comment '切片正文',
    char_count  int                                not null comment '切片字符数',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_document_chunk
        unique (document_id, chunk_index)
)
    comment '知识库文档切片表' charset = utf8mb4;
