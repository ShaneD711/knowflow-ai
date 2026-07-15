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

## 2026-07-11

### 今日完成

- 调整登录后页面结构，增加顶部用户区域，并将知识库文档和系统状态组织为双栏仪表盘布局。
- 调整页面最大宽度和左右栏比例，减少宽屏下内容过窄、两侧留白过多的问题。
- 完善知识库文档列表展示，增加文档数量、更新时间、空列表提示和列表项悬停反馈。
- 增加窄屏响应式样式，在屏幕宽度小于 `760px` 时将双栏布局切换为单栏。
- 增加深色/浅色主题切换，并修正浅色模式下标题、正文、输入框、按钮和文档列表的颜色显示。
- 增加页面底部说明，并通过调整主容器的 Flex 布局方向修复页脚显示在页面右侧的问题。
- 继续保持 `App.vue` 内部按类型、状态、请求函数和页面操作函数分区，方便当前阶段阅读。
- 将 TypeScript 类型定义拆分到 `frontend/src/types`，分别管理统一响应、健康检查、登录用户和知识库文档类型。
- 将健康检查、登录和文档列表请求拆分到 `frontend/src/api`，让页面组件不再直接编写 `fetch` 请求。
- 将登录表单、顶部栏、知识库列表、系统状态和页脚拆分为独立 Vue 组件。
- 将原有页面样式提取到 `frontend/src/styles/app.css`，保持深色/浅色主题和响应式布局不变。
- 将 `frontend/src/App.vue` 从 465 行精简到 104 行，目前只负责页面状态、页面操作和组件组装。
- 运行 `npm run build`，确认 TypeScript 检查和 Vite 生产构建通过。

### 下一步

- 在浏览器中手动验证登录、退出登录、文档列表和深色/浅色主题切换。
- 为知识库文档列表增加点击操作，调用文档详情接口并展示完整正文。

## 2026-07-12

### 今日完成

- 在前端增加 `KbDocumentDetail` 类型，用于描述详情接口返回的标题、正文、创建时间和更新时间。
- 在 `frontend/src/api/knowledge.ts` 中增加 `getDocumentDetail(id)`，调用 `GET /api/kb/documents/{id}` 查询指定文档详情。
- 在 `App.vue` 中增加 `selectedDocument` 响应式状态和 `handleSelectDocument(id)` 页面操作函数，负责查询并保存当前文档详情。
- 在 `KnowledgeList.vue` 中使用 `defineEmits` 声明 `select-document` 事件，并通过可点击按钮将文档 ID 传给父组件。
- 创建 `DocumentDetailPanel.vue`，通过 `props` 接收并展示文档标题、更新时间和完整正文。
- 使用 `v-if / v-else` 控制右侧区域：未选择文档时显示系统状态，选择文档后显示文档详情。
- 在退出登录时清空 `selectedDocument`，避免重新登录后显示上一次查看的旧文档。
- 完善文档详情样式，增加标题分隔线、正文行距、原始换行保留、长内容换行以及深色/浅色模式配色。
- 在浏览器中验证点击文档后可以正确显示详情，并验证退出后重新登录会恢复系统状态面板。
- 运行 `npm run build`，确认 TypeScript 检查和 Vite 生产构建通过。

### 下一步

- 在前端增加新建文档表单，调用 `POST /api/kb/documents` 创建文档。
- 创建成功后重新查询文档列表，并在页面中显示新文档。

## 2026-07-13

### 今日完成

- 新增知识库文档上传接口 `POST /api/kb/documents/upload`。
- 使用 `MultipartFile` 接收 `multipart/form-data` 中名为 `file` 的文件字段。
- 完成空文件、空文件名、文件类型和空正文校验，目前仅支持 UTF-8 编码的 `txt` 和 `md` 文件。
- 使用原文件名生成文档标题，读取文件正文后保存到 `kb_document` 表。
- 使用 Apifox 的 `form-data` 上传 Markdown 文件，并在 DataGrip 中确认文档保存成功。

## 2026-07-14

### 今日完成

- 在 MySQL 中创建 `kb_document_chunk` 知识库文档切片表，保存原文档 ID、切片序号、切片内容、字符数和创建时间。
- 创建 `KbDocumentChunk` Entity 和 `KbDocumentChunkMapper`，完成 Java 对象与切片表的映射及数据写入。
- 创建 `DocumentChunkService`，将文档正文按每 500 字切分，按顺序设置 `chunk_index` 并逐条写入数据库。
- 在 `KnowledgeService.uploadDocument()` 中调用切片服务，使一次上传同时完成原文保存和切片保存。
- 为上传方法增加 `@Transactional`，保证原文和切片作为一个整体提交；切片写入失败时，原文也会回滚。
- 新增 1227 字的 `账号登录故障处理指南.md` 测试文档，通过 Apifox 完成上传验证。
- 在 DataGrip 中确认文档 ID `11` 生成 3 条切片，`chunk_index` 按 `1、2、3` 正确排列。
- 通过 Maven 完成项目构建，测试结果为 1 个通过、0 个失败，构建结果为 `BUILD SUCCESS`。

### 下一步

- 实现文档切片的关键词检索，根据用户输入的关键词返回匹配的切片内容。

## 2026-07-15

### 今日完成

- 在项目根目录创建 `database/schema.sql`，将 DataGrip 中的数据库结构整理为可提交、可重新执行的 SQL 脚本。
- 将 `sys_user`、`kb_document` 和 `kb_document_chunk` 三张表的 DDL 按业务依赖顺序写入脚本。
- 为数据库和三张表增加 `IF NOT EXISTS`，避免重复执行时因对象已存在而中断。
- 在 DataGrip 中连续两次执行完整脚本，确认已存在的数据库和表只会产生提示，不会删除或覆盖已有数据。
- 在 README 中补充数据库结构脚本位置和初始化使用说明，便于在新电脑上恢复项目数据库结构。

### 下一步

- 实现文档切片的关键词检索，让后端能够根据关键词返回匹配的切片。
