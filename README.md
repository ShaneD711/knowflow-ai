# KnowFlow AI

KnowFlow AI 是一个面向 SaaS 产品售后场景的知识库 AI 客服与工单协同平台。

## 当前进度

- 已创建后端 Spring Boot 项目骨架。
- 已实现统一响应结构 `ApiResponse`。
- 已实现健康检查接口 `GET /api/health`。
- 已使用 Apifox 完成接口测试。
- 已创建前端 Vue3 + TypeScript 项目骨架。
- 已配置 Vite proxy，实现前端请求转发到后端。
- 已完成前端首页调用 `GET /api/health`，并显示后端运行状态。

## 后端启动

使用 IntelliJ IDEA 打开 `backend` 项目，运行启动类：

```text
BackendApplication
```

启动后默认访问地址：

```text
http://localhost:8080
```

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

前端代理访问：

```text
GET http://localhost:5173/api/health
```

开发进度记录见：

```text
docs/progress/PROJECT_LOG.md
```
