<script setup lang="ts">
import type { LoginUser } from "../types/auth";

defineProps<{
  user: LoginUser;
  isDarkMode: boolean;
}>();

defineEmits<{
  "toggle-theme": [];
  logout: [];
}>();
</script>

<template>
  <header class="dashboard-header">
    <div>
      <h1>KnowFlow AI</h1>
      <p>欢迎回来，{{ user.nickname }}（{{ user.username }}）</p>
    </div>

    <button type="button" class="theme-button" @click="$emit('toggle-theme')">
      {{ isDarkMode ? "浅色模式" : "深色模式" }}
    </button>

    <button type="button" class="logout-button" @click="$emit('logout')">
      退出登录
    </button>
  </header>
</template>
