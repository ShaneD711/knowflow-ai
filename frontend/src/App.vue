<script setup lang="ts">
import { onMounted, ref } from "vue";

// ref 用来保存会变化的数据；数据变化后，页面会自动更新
const backendStatus = ref("检测中...");
const backendService = ref("-");

// onMounted 会在页面加载完成后执行，适合做初始化请求
onMounted(async () => {
  // 请求 /api/health，会通过 Vite proxy 转发到 Spring Boot 后端
  const response = await fetch("/api/health");

  // 将后端返回的 JSON 字符串转换成前端可读取的对象
  const result = await response.json();

  // 后端返回结构是 { code, message, data }，真实业务数据在 data 中
  backendStatus.value = result.data.status;
  backendService.value = result.data.service;
});
</script>

<template>
  <!-- 页面主体结构 -->
  <main>
    <h1>KnowFlow AI</h1>
    <p>AI 客服与知识库工单协同平台</p>

    <!-- 当前运行状态：静态说明 + 后端接口返回结果 -->
    <section>
      <h2>当前开发进度</h2>
      <ul>
        <li>后端 Spring Boot 项目已启动</li>
        <li>后端状态：{{ backendStatus }}</li>
        <li>后端服务：{{ backendService }}</li>
        <li>前端 Vue 项目已启动</li>
      </ul>
    </section>
  </main>
</template>

<style scoped>
/* 页面样式 */
main {
  min-height: 100vh;
  padding: 80px 24px;
  text-align: center;
  background: #111827;
  color: #f9fafb;
}

p {
  color: #9ca3af;
}

section {
  margin-top: 32px;
}

ul {
  display: inline-block;
  text-align: left;
  padding-left: 24px;
}

li {
  margin: 8px 0;
}
</style>
