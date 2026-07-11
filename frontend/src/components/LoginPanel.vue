<script setup lang="ts">
import { ref } from "vue";
import type { LoginRequest } from "../types/auth";

defineProps<{
  error: string;
  loading: boolean;
}>();

const emit = defineEmits<{
  login: [request: LoginRequest];
}>();

const username = ref("");
const password = ref("");

function handleSubmit() {
  emit("login", {
    username: username.value,
    password: password.value,
  });
}
</script>

<template>
  <section class="login-panel">
    <h1>KnowFlow AI</h1>
    <p>AI 客服与知识库工单协同平台</p>

    <form class="login-form" @submit.prevent="handleSubmit">
      <label>
        用户名
        <input v-model="username" type="text" placeholder="请输入用户名" />
      </label>

      <label>
        密码
        <input v-model="password" type="password" placeholder="请输入密码" />
      </label>

      <p v-if="error" class="error-message">{{ error }}</p>

      <button type="submit" :disabled="loading">
        {{ loading ? "登录中..." : "登录" }}
      </button>
    </form>
  </section>
</template>
