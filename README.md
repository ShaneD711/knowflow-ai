# KnowFlow AI

KnowFlow AI 是一个面向 SaaS 产品售后场景的知识库 AI 客服与工单协同平台。

## 当前进度

- 已创建后端 Spring Boot 项目骨架。
- 已实现统一响应结构 `ApiResponse`。
- 已实现健康检查接口 `GET /api/health`。
- 已使用 Apifox 完成接口测试。
- 已接入 MySQL 和 MyBatis-Plus。
- 已创建 `sys_user` 用户表，并完成 admin 测试用户查询验证。
- 已完成 `POST /api/auth/login` 登录接口，并通过 Apifox 验证成功、失败和空参数场景。
- 已将数据库密码改为通过本地环境变量读取，避免账号密码提交到 Git。
- 已创建前端 Vue3 + TypeScript 项目骨架。
- 已配置 Vite proxy，实现前端请求转发到后端。
- 已完成前端首页调用 `GET /api/health`，并显示后端运行状态。
- 已完成前端登录表单，支持调用 `POST /api/auth/login`。
- 已完成登录成功展示当前用户、登录失败提示错误信息、退出登录。
- 已创建 `kb_document` 知识库文档表。
- 已创建知识库文档模块基础结构：`entity`、`mapper`、`dto`、`vo`、`service`、`controller`。
- 已完成知识库文档新增接口 `POST /api/kb/documents`，并通过 Apifox 验证可以写入 MySQL。
- 已完成知识库文档列表接口 `GET /api/kb/documents`，列表返回 `KbDocumentListItemVO`，不返回完整正文内容。
- 已完成知识库文档详情接口 `GET /api/kb/documents/{id}`，详情返回 `KbDocumentDetailVO`，包含正文内容 `content`。
- 已完成知识库文档删除接口 `DELETE /api/kb/documents/{id}`，支持删除存在的文档，并对不存在的文档返回业务错误。
- 已完成知识库文档修改接口 `PUT /api/kb/documents/{id}`，通过 `UpdateDocumentRequest` 接收新标题和正文。
- 已完成前端登录后加载知识库文档列表，调用 `GET /api/kb/documents` 并展示文档标题和更新时间。
- 已将登录后页面整理为知识库文档和系统状态双栏布局，并补充文档数量、空状态和列表项悬停反馈。
- 已完成深色/浅色主题切换，并调整浅色模式下标题、正文、输入框、按钮和文档列表的配色。
- 已增加移动端单栏适配和页面底部说明，页面在窄屏下可以正常排列。
- 已整理 `frontend/src/App.vue` 内部结构，将类型定义、页面状态、接口请求函数和页面操作函数分区；下一阶段将继续拆分类型、接口请求和页面组件。

## 后端启动

使用 IntelliJ IDEA 打开 `backend` 项目，运行启动类：

```text
BackendApplication
```

启动后默认访问地址：

```text
http://localhost:8080
```

后端数据库连接需要本地环境变量：

```text
DB_USERNAME=knowflow_user
DB_PASSWORD=你的本地数据库密码
```

本地可以使用 `backend/.env.local` 记录这些值。该文件只保存在本机，已加入 `.gitignore`，不要提交到 Git。

## 前端启动

进入前端目录：

```bash
cd frontend
```

启动开发服务器：

```bash
npm run dev
```

启动后默认访问地址：

```text
http://localhost:5173
```

## 接口测试

健康检查接口：

```text
GET http://localhost:8080/api/health
```

登录接口：

```text
POST http://localhost:8080/api/auth/login
```

请求体示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

新增知识库文档接口：

```text
POST http://localhost:8080/api/kb/documents
```

请求体示例：

```json
{
  "title": "第一篇知识库文档",
  "content": "这是 KnowFlow AI 的第一篇知识库文档，用来测试文档保存功能。"
}
```

查询知识库文档列表接口：

```text
GET http://localhost:8080/api/kb/documents
```

查询知识库文档详情接口：

```text
GET http://localhost:8080/api/kb/documents/1
```

修改知识库文档接口：

```text
PUT http://localhost:8080/api/kb/documents/1
```

请求体示例：

```json
{
  "title": "第一篇知识库文档（已修改）",
  "content": "这是修改后的文档内容，用来测试修改文档接口。"
}
```

删除知识库文档接口：

```text
DELETE http://localhost:8080/api/kb/documents/1
```

前端当前已通过 Vite proxy 调用后端接口：

```text
GET http://localhost:5173/api/health
GET http://localhost:5173/api/kb/documents
```

登录成功后，页面会请求知识库文档列表，展示文档标题、更新时间和文档数量，并支持深色/浅色主题切换。

## 下一步

在继续增加知识库详情、编辑和删除等前端功能前，先逐步拆分 `frontend/src/App.vue`：

- `types`：管理前后端数据结构。
- `api`：管理接口请求函数。
- `components`：管理登录、顶部栏、知识库列表、系统状态和页脚等页面组件。

拆分会分阶段进行，每次只移动一类代码，并在每一步后确认页面功能没有变化。

## 数据库

当前本地开发使用 MySQL：

```text
database: knowflow
tables: sys_user, kb_document
```

`sys_user` 当前用于登录模块前置验证。接口返回用户信息时会通过 `UserVO` 过滤掉 `password` 字段，避免把密码返回给前端。

`kb_document` 当前用于保存知识库文档标题、正文、创建人和创建/更新时间。知识库文档模块已完成基础 CRUD：新增、查询列表、查询详情、修改和删除。列表接口通过 `KbDocumentListItemVO` 返回摘要信息，不直接返回长正文内容；详情接口通过 `KbDocumentDetailVO` 返回完整正文内容。

开发进度记录见：

```text
docs/progress/PROJECT_LOG.md
```
