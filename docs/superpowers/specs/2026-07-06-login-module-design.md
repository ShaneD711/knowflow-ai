# 登录模块最小设计

## 目标

先做一个真实 MySQL 登录闭环，让 KnowFlow AI 具备最基础的用户登录能力。

本阶段只要求后端能够连接 MySQL，并通过数据库中的用户完成登录校验。暂时不做复杂权限系统。

## 技术方案

- 数据库：MySQL
- 持久层：MyBatis-Plus
- 后端接口：Spring Boot Controller + Service
- 前端调用：后续由 Vue 登录页调用 `/api/auth/login`

## 本阶段范围

本阶段实现：

- 添加 MySQL 和 MyBatis-Plus 依赖。
- 配置后端数据库连接。
- 创建 `sys_user` 用户表。
- 插入一个测试用户。
- 实现 `POST /api/auth/login` 登录接口。
- 使用 Apifox 测试登录接口。

本阶段暂不实现：

- JWT
- Spring Security
- 注册功能
- 验证码
- 权限菜单
- 密码加密
- 复杂角色权限

## 数据表

表名：`sys_user`

字段：

```text
id          用户 ID
username    登录账号
password    登录密码
nickname    昵称
role        角色，比如 admin / user
status      状态，1 正常，0 禁用
created_at  创建时间
updated_at  更新时间
```

## 登录接口

接口：

```text
POST /api/auth/login
```

请求示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

成功响应示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "管理员",
    "role": "admin"
  }
}
```

## 实现顺序

1. 准备 MySQL 数据库和 `sys_user` 表。
2. 后端添加数据库相关依赖。
3. 配置 `application.properties`。
4. 创建实体、Mapper、Service、Controller。
5. 使用 Apifox 调试登录接口。
6. 登录接口通过后，再接入前端登录页。
