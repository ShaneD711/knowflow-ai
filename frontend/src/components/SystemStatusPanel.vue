<script setup lang="ts">
defineProps<{
  backendStatus: string;
  backendService: string;
}>();
</script>

<template>
  <section>
    <h2>系统状态</h2>
    <ul>
      <li>后端 Spring Boot 项目已启动</li>
      <li>后端状态：{{ backendStatus }}</li>
      <li>后端服务：{{ backendService }}</li>
      <li>前端服务：Vue 已启动</li>
      <li>登录状态：已登录</li>
    </ul>
  </section>
</template>
