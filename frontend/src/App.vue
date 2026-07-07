<script setup lang="ts">
import { onMounted, ref } from "vue";

// 后端统一响应格式：所有接口都会返回 code、message、data
// T 表示 data 里面的具体数据类型，比如 HealthData 或 LoginUser
type ApiResponse<T> = {
  code: number;
  message: string;
  data: T | null;
};

// /api/health 返回的业务数据结构
type HealthData = {
  status: string;
  service: string;
};

// /api/auth/login 登录成功后返回的用户信息结构
// 注意：这里没有 password，前端不应该拿到密码字段
type LoginUser = {
  id: number;
  username: string;
  nickname: string;
  role: string;
  status: number;
  createdAt: string;
  updatedAt: string;
};

// 后端健康检查状态，用来显示后端是否正常运行
const backendStatus = ref("检测中...");
const backendService = ref("-");

// 登录表单状态：输入框内容、错误提示、按钮加载状态、当前登录用户
const username = ref("");
const password = ref("");
const loginError = ref("");
const loginLoading = ref(false);
const currentUser = ref<LoginUser | null>(null);

// 页面加载完成后自动请求健康检查接口
onMounted(async () => {
  const response = await fetch("/api/health");
  const result: ApiResponse<HealthData> = await response.json();

  if (result.data) {
    backendStatus.value = result.data.status;
    backendService.value = result.data.service;
  }
});

// 登录按钮触发的函数：把用户名和密码发给后端登录接口
async function handleLogin() {
  loginError.value = "";
  loginLoading.value = true;

  // fetch 负责发 HTTP 请求；这里通过 Vite proxy 转发到 Spring Boot 后端
  const response = await fetch("/api/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: username.value,
      password: password.value,
    }),
  });

  const result: ApiResponse<LoginUser> = await response.json();

  // 后端业务 code 不是 200，说明登录失败，把错误信息显示到页面上
  if (result.code !== 200) {
    loginError.value = result.message;
    currentUser.value = null;
    loginLoading.value = false;
    return;
  }

  // 登录成功：保存当前用户。currentUser 有值后，template 会自动切换到登录后页面
  currentUser.value = result.data;
  password.value = "";
  loginLoading.value = false;
}

// 退出登录：清空当前用户，页面会自动回到登录表单
function handleLogout() {
  currentUser.value = null;
  username.value = "";
  password.value = "";
  loginError.value = "";
}
</script>

<template>
  <main>
    <!-- currentUser 为空时，说明还没登录，显示登录表单 -->
    <section v-if="!currentUser" class="login-panel">
      <h1>KnowFlow AI</h1>
      <p>AI 客服与知识库工单协同平台</p>

      <!-- submit 表单时调用 handleLogin；prevent 表示不要让浏览器刷新页面 -->
      <form class="login-form" @submit.prevent="handleLogin">
        <label>
          用户名
          <!-- v-model 会把输入框内容同步到 username 变量 -->
          <input v-model="username" type="text" placeholder="请输入用户名" />
        </label>

        <label>
          密码
          <!-- v-model 会把输入框内容同步到 password 变量 -->
          <input v-model="password" type="password" placeholder="请输入密码" />
        </label>

        <p v-if="loginError" class="error-message">{{ loginError }}</p>

        <button type="submit" :disabled="loginLoading">
          {{ loginLoading ? "登录中..." : "登录" }}
        </button>
      </form>
    </section>

    <!-- currentUser 有值时，说明登录成功，显示登录后页面 -->
    <section v-else class="dashboard">
      <h1>KnowFlow AI</h1>
      <p>欢迎回来，{{ currentUser.nickname }}（{{ currentUser.username }}）</p>
      <button type="button" class="logout-button" @click="handleLogout">
        退出登录
      </button>

      <section>
        <h2>当前开发进度</h2>
        <ul>
          <li>后端 Spring Boot 项目已启动</li>
          <li>后端状态：{{ backendStatus }}</li>
          <li>后端服务：{{ backendService }}</li>
          <li>前端 Vue 项目已启动</li>
          <li>登录接口已完成</li>
        </ul>
      </section>
    </section>
  </main>
</template>

<style scoped>
main {
  min-height: 100vh;
  padding: 80px 24px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background: #111827;
  color: #f9fafb;
}

.login-panel,
.dashboard {
  width: 100%;
  max-width: 480px;
  text-align: center;
}

h1 {
  margin: 0 0 12px;
  font-size: 44px;
}

p {
  color: #9ca3af;
}

.login-form {
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  text-align: left;
}

label {
  display: flex;
  flex-direction: column;
  gap: 8px;
  color: #d1d5db;
  font-size: 14px;
}

input {
  height: 44px;
  padding: 0 12px;
  border: 1px solid #374151;
  border-radius: 6px;
  background: #020617;
  color: #f9fafb;
  font-size: 16px;
}

input:focus {
  outline: none;
  border-color: #60a5fa;
}

button {
  height: 44px;
  border: none;
  border-radius: 6px;
  background: #2563eb;
  color: #ffffff;
  font-size: 16px;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.6;
}

.error-message {
  margin: 0;
  color: #f87171;
}

.dashboard section {
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

.logout-button {
  width: auto;
  min-width: 96px;
  margin-top: 16px;
  padding: 0 18px;
  background: #374151;
}

.logout-button:hover {
  background: #4b5563;
}
</style>
