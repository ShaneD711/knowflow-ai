# KnowFlow AI 开发进度日志

## 2026-07-06

### 今日完成

- 使用 IntelliJ IDEA 的 Spring Initializr 创建后端 Spring Boot 项目骨架。
- 后端项目使用 Java 17、Maven、Spring Boot，并成功在 IDEA 中启动。
- 创建 `HealthController`，实现 `GET /api/health` 健康检查接口。
- 创建 `ApiResponse` 统一响应结构，让接口返回包含 `code`、`message`、`data`。
- 使用 Apifox 调试 `GET /api/health`，确认接口返回 `200`。
- 在 Apifox 中保存“健康检查”接口，建立后续接口调试流程。
- 使用 Vite 创建前端 Vue3 + TypeScript 项目骨架。
- 配置 Vite proxy，将前端 `/api` 请求转发到 Spring Boot 后端。
- 修改前端首页，调用 `GET /api/health` 并展示后端状态和服务名。
- 完成前后端健康检查联调，确认前端可以读取后端接口返回。
- 使用 GitHub Desktop 提交并推送后端骨架、健康检查接口和前端联调代码到 GitHub 仓库。
- 修订 `AGENTS.md`，明确项目采用现代、日常、快速、常用的开发方式：IDEA、Spring Initializr、VS Code、Vite、Apifox、GitHub Desktop 等。
- 完成登录模块最小设计，明确第一阶段先做用户名密码登录、用户表、DTO、VO、Service、Controller 和 Apifox 验证。
- 在 MySQL 中创建 `knowflow` 数据库、`sys_user` 表，并插入 admin 测试用户。
- 创建数据库专用账号 `knowflow_user`，授权访问 `knowflow` 数据库，避免后端直接使用 root 账号连接数据库。
- 后端引入 MyBatis-Plus 和 MySQL Connector 依赖。
- 配置 Spring Boot 数据源，使用 `${DB_PASSWORD}` 从本地环境变量读取数据库密码。
- 创建本地 `backend/.env.local` 保存开发环境数据库账号密码，并将 `.env`、`.env.local` 加入 `.gitignore`，避免敏感信息提交到 Git。
- 创建 `SysUser` 实体、`SysUserMapper`、`UserVO` 和临时验证接口 `GET /api/test/user`。
- 使用 Apifox 验证后端可以从 MySQL 查询 admin 用户。
- 调整接口返回结构，使用 `UserVO` 过滤 `password` 字段，避免密码返回给前端。
