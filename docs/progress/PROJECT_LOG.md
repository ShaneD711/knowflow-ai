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

## 2026-07-07

### 今日完成

- 创建 `LoginRequest`，用于接收登录接口传入的 `username` 和 `password`。
- 创建 `AuthService`，完成用户名查询、密码校验、账号状态校验和用户信息返回逻辑。
- 创建 `AuthController`，提供登录接口 `POST /api/auth/login`。
- 使用 Apifox 保存“登录”接口，并完成登录成功、密码错误、用户不存在、空用户名和缺少密码等场景验证。
- 登录接口返回 `UserVO`，确认响应结果不包含 `password` 字段。
- 删除临时数据库查询验证接口 `GET /api/test/user`，避免保留无业务意义的测试接口。
- 前端首页改造为登录表单和登录后状态页。
- 使用 `v-model` 绑定用户名和密码输入框，使用 `fetch` 调用 `POST /api/auth/login`。
- 完成前端登录成功、登录失败、空参数提示和退出登录验证。
- 在 MySQL 中创建 `kb_document` 知识库文档表，用于保存文档标题、正文、创建人和创建/更新时间。
- 创建知识库模块后端基础结构：`KbDocument`、`KbDocumentMapper`、`CreateDocumentRequest`、`KbDocumentListItemVO`、`KnowledgeService`、`KnowledgeController`。
- 完成知识库文档新增接口 `POST /api/kb/documents`，通过 `CreateDocumentRequest` 接收标题和正文，并保存到 `kb_document` 表。
- 使用 Apifox 验证新增文档接口，确认接口返回新文档 ID，并在数据库中生成记录。
- 完成知识库文档列表接口 `GET /api/kb/documents`，按 ID 倒序返回文档列表。
- 列表接口使用 `KbDocumentListItemVO` 控制返回字段，只返回 `id`、`title`、`createdBy`、`createdAt`、`updatedAt`，不返回完整 `content`。

## 2026-07-09

### 今日完成

- 创建 `KbDocumentDetailVO`，用于知识库文档详情接口返回完整文档内容。
- 完成知识库文档详情接口 `GET /api/kb/documents/{id}`，支持按文档 ID 查询标题、正文、创建时间和更新时间。
- 完成知识库文档删除接口 `DELETE /api/kb/documents/{id}`，删除成功返回 `true`，文档不存在时返回业务错误。
- 创建 `UpdateDocumentRequest`，用于接收修改文档时传入的 `title` 和 `content`。
- 完成知识库文档修改接口 `PUT /api/kb/documents/{id}`，支持修改文档标题和正文。
- 使用 Apifox 验证知识库文档新增、列表、详情、修改和删除接口，知识库文档模块基础 CRUD 闭环完成。
- 梳理 DTO、Entity、VO 的职责边界：DTO 接收前端请求，Entity 映射数据库表，VO 控制返回给前端的数据结构。
- 前端登录后页面接入知识库文档列表，登录成功后调用 `GET /api/kb/documents` 获取文档列表。
- 在前端页面中使用 `KbDocumentListItem` 描述知识库文档列表项结构，并用 `documents` 状态保存后端返回的列表数据。
- 使用 `v-for` 渲染知识库文档标题，实现前端展示数据库文档列表的最小功能。
- 整理 `frontend/src/App.vue` 内部代码分区，将类型定义、健康检查状态、登录状态、知识库文档状态、接口请求函数和页面操作函数分开。
- 将 `loadDocuments()` 从 `handleLogin()` 内部移出，明确接口请求函数和页面操作函数的职责边界。
