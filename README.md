# KnowFlow AI

KnowFlow AI 是一个面向 SaaS 产品售后场景的知识库 AI 客服与工单协同平台。

## 当前进度

- 已创建后端 Spring Boot 项目骨架。
- 已实现统一响应结构 `ApiResponse`。
- 已实现健康检查接口 `GET /api/health`。
- 已使用 Apifox 完成接口测试。

## 后端启动

使用 IntelliJ IDEA 打开 `backend` 项目，运行启动类：

```text
BackendApplication
```

启动后默认访问地址：

```text
http://localhost:8080
```

## 接口测试

健康检查接口：

```text
GET http://localhost:8080/api/health
```

开发进度记录见：

```text
docs/progress/PROJECT_LOG.md
```
