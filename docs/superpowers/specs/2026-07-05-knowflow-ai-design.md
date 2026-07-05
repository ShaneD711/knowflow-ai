# KnowFlow AI 项目设计文档

## 1. 项目名称

**KnowFlow AI：SaaS 产品售后知识库 AI 客服与工单协同平台**

## 2. 一句话介绍

KnowFlow AI 是一个面向 SaaS 产品售后和企业内部支持场景的知识库 AI 客服平台，支持企业文档管理、RAG 知识库问答、无法解决问题转人工工单、客服处理和数据统计。

## 3. 项目定位

本项目用于 Java 后端、全栈和 AI 应用方向求职。项目目标不是做一个简单的 ChatGPT 套壳，而是围绕真实企业客服场景建立一条完整业务链路：

```text
企业上传产品/售后文档
-> 系统解析并切片
-> 用户向 AI 提问
-> 系统检索知识片段
-> DeepSeek 基于资料回答
-> 用户仍未解决
-> 自动生成工单
-> 客服人工处理
-> 问题沉淀回知识库
```

项目开发方式以学习和理解为优先。每个阶段只实现一个小目标，保持系统可运行、可测试、可扩展。

## 4. 真实业务场景

目标场景先收敛为 **SaaS 产品售后知识库**。

企业有产品使用手册、功能 FAQ、错误码说明、售后处理流程、内部制度等资料。普通用户或客服人员遇到问题时，可以向 AI 提问。系统先从企业知识库检索相关文档片段，再把检索结果和用户问题一起发送给大模型，让 AI 基于企业资料生成回答。

如果 AI 无法解决问题，或者用户反馈“仍未解决”，系统会根据会话内容自动生成工单，带上问题摘要、关联文档片段、AI 已回答内容和用户原始问题，交给人工客服继续处理。

## 5. 核心功能模块

### 5.1 用户与权限模块

- 用户注册和登录。
- JWT 鉴权。
- 管理员、客服、普通用户三类角色。
- 后期扩展 RBAC 菜单权限、按钮权限和接口权限。

### 5.2 知识库模块

- 知识库空间管理。
- 文档上传。
- 文档解析状态管理。
- 文档切片。
- 文档片段检索。
- 后期扩展 MinIO 文件存储、异步解析和向量检索。

### 5.3 AI 问答模块

- 用户创建问答会话。
- 保存用户问题和 AI 回答。
- 检索知识库片段。
- 拼装 Prompt。
- 调用 DeepSeek API。
- 返回回答和引用来源。
- 记录 AI 调用日志。

### 5.4 工单协同模块

- 用户从 AI 会话创建工单。
- 系统自动生成工单标题和问题摘要。
- 客服查看、认领、处理和关闭工单。
- 保存工单处理记录。
- 记录状态流转。

### 5.5 后台管理模块

- 用户管理。
- 角色管理。
- 知识库管理。
- 文档管理。
- 工单管理。
- AI 调用日志查询。
- 后期扩展数据看板。

## 6. MVP 最小可行版本范围

MVP 目标是跑通核心业务闭环，而不是堆完整功能。

MVP 包含：

- 后端 Spring Boot 项目和前端 Vue 项目可本地运行。
- 登录、注册、JWT 鉴权。
- 管理员上传文本、Markdown 或简单 PDF 文档。
- 后端解析文档并切片入库。
- 用户发起 AI 问答。
- 系统基于关键词或简单相似度检索文档片段。
- 后端调用 DeepSeek API 生成回答。
- AI 回答带引用来源。
- 用户点击“仍未解决”生成工单。
- 客服后台处理工单。
- 记录 AI 调用日志。

MVP 暂不包含：

- 多租户企业空间的完整隔离。
- 复杂菜单权限和按钮权限。
- RabbitMQ 异步任务。
- MinIO 文件存储。
- PostgreSQL + pgvector 向量检索。
- OCR 图片识别。
- 完整数据看板。
- 复杂 SLA 工单规则。

## 7. 完整版功能范围

完整版在 MVP 基础上逐步增加：

- 多租户企业空间。
- RBAC 菜单、按钮和接口权限。
- MinIO 存储原始文档。
- RabbitMQ 异步处理文档解析、切片和摘要。
- Embedding 模型接入。
- PostgreSQL + pgvector 向量检索。
- 混合检索：关键词检索 + 向量检索。
- AI 工单摘要、分类和优先级建议。
- 相似问题和相似工单推荐。
- 工单 SLA、转派、评论、操作日志。
- AI 调用限流、重试、降级和成本统计。
- ECharts 数据看板。
- Docker Compose 一键启动。
- Nginx 部署前端静态资源和反向代理后端接口。

## 8. 推荐技术栈及理由

### 8.1 后端

- **Java 17**：稳定，生态成熟，适合 Spring Boot 3。
- **Spring Boot 3**：主流 Java 后端框架，适合快速构建 REST API。
- **Spring Security**：实现认证和授权，面试价值高。
- **JWT**：适合前后端分离登录态。
- **MyBatis-Plus**：降低 CRUD 成本，同时保留 SQL 理解空间。
- **MySQL 8**：核心业务数据存储。
- **Redis**：后期用于登录态缓存、热点数据缓存、AI 调用限流。
- **RabbitMQ**：后期用于文档解析、Embedding、摘要等异步任务。
- **MinIO**：后期用于存储原始文档。
- **PostgreSQL + pgvector**：后期用于向量检索。

### 8.2 前端

- **Vue 3**：国内后台管理项目常用。
- **TypeScript**：提升接口类型约束和可维护性。
- **Vite**：开发体验好，启动快。
- **Element Plus**：适合快速搭建企业后台界面。
- **Pinia**：管理用户信息、Token 和权限状态。
- **Axios**：封装前后端接口请求。
- **ECharts**：后期用于数据看板。

### 8.3 AI

- **DeepSeek API**：作为主要大模型调用服务。
- **Prompt 模板**：将业务规则、引用片段和用户问题组织成稳定输入。
- **RAG**：让 AI 基于企业资料回答，而不是只靠模型通用知识。
- **Embedding 抽象接口**：先预留接口，后期可替换具体向量模型。
- **AI 调用日志**：记录模型、Prompt、耗时、状态、错误和成本估算。

## 9. 12 周开发计划

默认节奏：每天 2-3 小时，每周 5-6 天。

| 周次 | 学习目标 | 开发产出 |
| --- | --- | --- |
| 第 1 周 | Spring Boot 3、Vue 3、REST、Git 基础 | 后端/前端骨架、健康检查接口、README |
| 第 2 周 | MySQL、MyBatis-Plus、统一响应、异常处理 | 用户表、基础 CRUD、统一异常处理 |
| 第 3 周 | Spring Security、JWT、登录流程 | 注册、登录、JWT 鉴权、登录态前后端联调 |
| 第 4 周 | Vue 后台布局、路由、Pinia、Axios | 登录页、后台布局、路由守卫、用户信息展示 |
| 第 5 周 | 知识库建模、文档上传、文件校验 | 知识库、文档上传、文档列表、文档状态 |
| 第 6 周 | 文档解析、切片策略、检索基础 | 文档切片入库、关键词检索接口 |
| 第 7 周 | DeepSeek 调用、Prompt、AI 日志 | AI 问答接口、会话记录、AI 调用日志 |
| 第 8 周 | RAG 闭环、引用来源、无法回答策略 | 用户问答页面、引用片段展示、MVP AI 闭环 |
| 第 9 周 | 工单模型、状态机、人工处理流程 | 工单创建、认领、处理、关闭、状态日志 |
| 第 10 周 | Redis 缓存、限流、登录态优化 | 缓存热点数据、AI 调用限流、Redis 实战 |
| 第 11 周 | 工程增强：RabbitMQ/MinIO/pgvector 三选一到二 | 异步文档处理、文件存储或向量检索增强 |
| 第 12 周 | Docker、Nginx、测试、简历材料 | Docker Compose、部署文档、README、面试讲稿 |

## 10. 每周阶段性面试沉淀

- 第 1-2 周：项目工程结构、REST API、统一响应、异常处理。
- 第 3-4 周：JWT 登录流程、Spring Security 过滤器链、前后端权限联调。
- 第 5-6 周：知识库建模、文档状态流转、文档切片策略。
- 第 7-8 周：RAG 问答链路、Prompt 设计、AI 调用日志和失败处理。
- 第 9 周：工单状态机、人工协同流程、会话转工单设计。
- 第 10 周：Redis 缓存、限流和热点数据设计。
- 第 11 周：异步任务、对象存储或向量检索增强。
- 第 12 周：部署、Nginx 反向代理、项目演示和简历表达。

## 11. 后端项目目录结构建议

```text
backend/
  pom.xml
  src/main/java/com/knowflow/
    KnowFlowApplication.java
    common/
      result/
      exception/
      constants/
      utils/
    config/
      SecurityConfig.java
      RedisConfig.java
      AiConfig.java
    security/
      jwt/
      filter/
      handler/
    modules/
      auth/
        controller/
        service/
        dto/
        vo/
      system/
        user/
        role/
        permission/
      knowledge/
        controller/
        service/
        mapper/
        entity/
        dto/
        vo/
      chat/
        controller/
        service/
        mapper/
        entity/
        dto/
        vo/
      ai/
        client/
        prompt/
        service/
        entity/
      ticket/
        controller/
        service/
        mapper/
        entity/
        dto/
        vo/
      dashboard/
        controller/
        service/
    infrastructure/
      file/
      vector/
      mq/
  src/main/resources/
    mapper/
    application.yml
```

目录原则：

- `common` 放通用响应、异常、工具类。
- `config` 放配置类。
- `security` 放认证授权相关代码。
- `modules` 按业务模块组织。
- `infrastructure` 放文件存储、向量检索、消息队列等基础设施适配代码。

## 12. 前端项目目录结构建议

```text
frontend/
  package.json
  src/
    api/
      auth.ts
      user.ts
      knowledge.ts
      chat.ts
      ticket.ts
      health.ts
    assets/
    components/
    layouts/
      AdminLayout.vue
    router/
      index.ts
    stores/
      auth.ts
      user.ts
    types/
      api.ts
      user.ts
      knowledge.ts
      chat.ts
      ticket.ts
    utils/
      request.ts
      token.ts
    views/
      login/
      dashboard/
      knowledge/
      chat/
      ticket/
      system/
    styles/
    main.ts
```

目录原则：

- `api` 只封装接口请求。
- `stores` 存放全局状态，例如 Token、用户信息。
- `views` 按页面组织。
- `types` 定义接口返回和业务实体类型。
- `utils/request.ts` 统一处理 Axios、Token 和错误提示。

## 13. 初步数据库表设计

### 13.1 系统权限

| 表名 | 作用 |
| --- | --- |
| `sys_user` | 用户账号、密码、昵称、状态、创建时间 |
| `sys_role` | 角色，例如管理员、客服、普通用户 |
| `sys_user_role` | 用户和角色关联 |
| `sys_permission` | 菜单、按钮或接口权限，MVP 可后期启用 |
| `sys_role_permission` | 角色和权限关联，MVP 可后期启用 |

### 13.2 知识库

| 表名 | 作用 |
| --- | --- |
| `kb_space` | 知识库空间，后期支持多企业 |
| `kb_document` | 文档元信息、文件类型、解析状态、上传人 |
| `kb_document_chunk` | 文档切片内容、段落序号、页码、token 长度 |
| `kb_chunk_embedding` | 文档切片向量信息，MVP 先预留 |

### 13.3 AI 问答

| 表名 | 作用 |
| --- | --- |
| `chat_session` | 用户问答会话 |
| `chat_message` | 用户问题和 AI 回答 |
| `chat_reference` | AI 回答引用的文档片段 |
| `ai_call_log` | 模型、Prompt、耗时、状态、错误、token 消耗 |

### 13.4 工单

| 表名 | 作用 |
| --- | --- |
| `ticket` | 工单标题、问题摘要、状态、优先级、负责人 |
| `ticket_comment` | 工单处理记录和客服回复 |
| `ticket_status_log` | 工单状态变更日志 |

### 13.5 统计和审计

| 表名 | 作用 |
| --- | --- |
| `audit_log` | 管理员操作日志 |
| `dashboard_daily_stats` | 每日统计数据，后期由定时任务生成 |

## 14. 关键设计取舍

### 14.1 先模块化单体，不做微服务

本项目优先保证学习效率和完整业务闭环。模块化单体已经足够覆盖 Controller、Service、Mapper、权限、事务、缓存和 AI 调用。微服务会引入注册中心、网关、配置中心和链路追踪，超出当前求职阶段的主要目标。

### 14.2 MVP 先关键词检索，后期升级向量检索

RAG 的关键是让 AI 基于文档回答。第一版可以先用标题、关键词、内容模糊匹配实现可运行链路。后期再接 Embedding 和 pgvector，形成更完整的向量检索能力。

### 14.3 文档格式先收敛

MVP 只支持 `.txt`、`.md` 和简单 PDF 文本提取。DOCX、复杂 PDF、图片 OCR、表格解析都后置，避免第一阶段陷入文件解析细节。

### 14.4 权限先角色级，后期再做细粒度 RBAC

MVP 只区分管理员、客服、普通用户。完整菜单权限、按钮权限和接口权限后期补充，避免权限系统吞掉主线开发时间。

### 14.5 AI 模型名配置化

DeepSeek 模型和 API 参数可能变化，项目中不把模型名写死在业务代码里，而是放到配置文件或环境变量中，便于后期调整。

## 15. 主要风险与应对

| 风险 | 应对 |
| --- | --- |
| 范围太大导致半成品 | 只围绕上传文档、AI 问答、转工单主链路推进 |
| AI 能力变成套壳 | 必须实现检索、引用来源、调用日志、无法回答策略 |
| 文档解析复杂度过高 | MVP 限定格式，后期再增强 |
| 权限系统过度设计 | 先角色级权限，后期补 RBAC |
| 技术栈过多学不透 | 每周只引入一个核心技术点 |
| 面试讲不清楚 | 每周沉淀“为什么这样设计”和“遇到什么问题” |

## 16. 第一阶段目标

第一阶段只做项目地基，不写 AI、不写工单、不写复杂权限。

目标：

- 初始化 Git 仓库。
- 创建后端 Spring Boot 项目骨架。
- 创建前端 Vue 3 项目骨架。
- 后端提供 `/api/health` 健康检查接口。
- 前端调用 `/api/health` 并展示结果。
- 写 README，说明项目定位、启动方式和当前进度。
- 保证本地开发环境可运行。

## 17. 第一个具体开发任务

任务名：**搭建项目骨架与健康检查接口**

### 17.1 这个任务解决什么问题

先证明后端、前端、开发环境和接口调用链路能跑通。后面登录、权限、知识库、AI 问答都会依赖这条基础链路。

### 17.2 为什么要这样设计

如果第一天就写登录、AI 或文档解析，问题会混在一起：是 Spring Boot 配置错了、前端请求错了、跨域错了，还是业务代码错了，很难判断。

健康检查接口是最小闭环：

```text
浏览器页面
-> Vue 前端
-> Axios 请求
-> Spring Boot Controller
-> JSON 响应
-> 前端展示结果
```

### 17.3 涉及技术点

- Spring Boot 3 项目结构。
- REST Controller。
- 统一响应对象。
- Vue 3 项目结构。
- Axios 请求封装。
- 前后端本地联调。
- Git 初始提交。

### 17.4 要修改哪些文件

后端：

- `backend/pom.xml`
- `backend/src/main/java/com/knowflow/KnowFlowApplication.java`
- `backend/src/main/java/com/knowflow/common/result/Result.java`
- `backend/src/main/java/com/knowflow/modules/system/controller/HealthController.java`

前端：

- `frontend/package.json`
- `frontend/src/api/health.ts`
- `frontend/src/views/dashboard/index.vue`
- `frontend/src/utils/request.ts`

文档：

- `README.md`

### 17.5 如何运行和测试

后端：

```bash
cd backend
./mvnw spring-boot:run
```

或在已安装 Maven 的情况下：

```bash
cd backend
mvn spring-boot:run
```

测试后端：

```bash
curl http://localhost:8080/api/health
```

前端：

```bash
cd frontend
npm install
npm run dev
```

浏览器访问：

```text
http://localhost:5173
```

### 17.6 完成后应该理解什么

- Spring Boot 应用从入口类到 Controller 的请求链路。
- 为什么要统一响应格式。
- Vue 页面如何通过 Axios 调用后端。
- 为什么前后端分离开发阶段会涉及跨域或代理配置。
- 为什么项目一开始就要定目录结构。

## 18. 下一步执行顺序

1. 用户确认本设计文档。
2. 创建第一个开发任务计划。
3. 只实现项目骨架与健康检查接口。
4. 本地运行后端和前端。
5. 提交 Git。
6. 总结第一阶段可写进简历和面试的技术点。
