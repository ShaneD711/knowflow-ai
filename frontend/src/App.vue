<script setup lang="ts">
import { onMounted, ref } from "vue";

// ==================== 类型定义 ====================

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

// 知识库文档列表项结构
type KbDocumentListItem = {
  id: number;
  title: string;
  createdBy: number;
  createdAt: string;
  updatedAt: string;
};

// ==================== 健康检查状态 ====================

// 后端健康检查状态，用来显示后端是否正常运行
const backendStatus = ref("检测中...");
const backendService = ref("-");

// ==================== 登录状态 ====================

// 登录表单状态：输入框内容、错误提示、按钮加载状态、当前登录用户
const username = ref("");
const password = ref("");
const loginError = ref("");
const loginLoading = ref(false);
const currentUser = ref<LoginUser | null>(null);

// ==================== 主题状态 ====================

// 深色模式开关
const isDarkMode = ref(true);

// ==================== 知识库文档状态 ====================

// 知识库文档列表
const documents = ref<KbDocumentListItem[]>([]);

// ==================== 页面初始化 ====================

// 页面加载完成后自动请求健康检查接口
onMounted(async () => {
  const response = await fetch("/api/health");
  const result: ApiResponse<HealthData> = await response.json();

  if (result.data) {
    backendStatus.value = result.data.status;
    backendService.value = result.data.service;
  }
});

// ==================== 接口请求函数 ====================

// 查询知识库文档列表
async function loadDocuments() {
  const response = await fetch("/api/kb/documents");
  const result: ApiResponse<KbDocumentListItem[]> = await response.json();

  if (result.data) {
    documents.value = result.data;
  }
}

// ==================== 页面操作函数 ====================

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

  // 登录成功后，查询文档列表
  await loadDocuments();
}

// 退出登录：清空当前用户，页面会自动回到登录表单
function handleLogout() {
  currentUser.value = null;
  username.value = "";
  password.value = "";
  loginError.value = "";
  documents.value = [];
}

// 切换主题：深色模式/浅色模式
function toggleTheme() {
  isDarkMode.value = !isDarkMode.value;
}
</script>

<template>
  <main :class="{ 'light-mode': !isDarkMode }">
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
      <header class="dashboard-header">
        <div>
          <h1>KnowFlow AI</h1>
          <p>
            欢迎回来，{{ currentUser.nickname }}（{{ currentUser.username }}）
          </p>
        </div>

        <button type="button" class="theme-button" @click="toggleTheme">
          {{ isDarkMode ? "浅色模式" : "深色模式" }}
        </button>

        <button type="button" class="logout-button" @click="handleLogout">
          退出登录
        </button>
      </header>

      <div class="dashboard-grid">
        <section>
          <div class="section-header">
            <div>
              <h2>知识库文档</h2>
              <p>共 {{ documents.length }} 篇文档</p>
            </div>
          </div>

          <p v-if="documents.length === 0" class="empty-message">暂无文档</p>

          <ul v-else class="document-list">
            <li
              v-for="document in documents"
              :key="document.id"
              class="document-item"
            >
              <strong>{{ document.title }}</strong>
              <span>更新时间：{{ document.updatedAt }}</span>
            </li>
          </ul>
        </section>

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
      </div>
    </section>

    <footer class="app-footer">
      KnowFlow AI · 知识库客服工单协同平台 · 本地开发环境
    </footer>
  </main>
</template>

<style scoped>
main {
  min-height: 100vh;
  padding: 80px 24px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  background: #111827;
  color: #f9fafb;
}

.login-panel {
  width: 100%;
  max-width: 480px;
  text-align: center;
}

.dashboard {
  width: 100%;
  max-width: 1180px;
  text-align: left;
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

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 24px;
  margin-bottom: 32px;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: 1.4fr 0.8fr;
  gap: 24px;
}

.dashboard-grid > section {
  padding: 24px;
  border: 1px solid #1f2937;
  border-radius: 8px;
  background: #020617;
}

.dashboard section {
  margin-top: 0;
}

ul {
  display: inline-block;
  text-align: left;
  padding-left: 24px;
}

.document-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding-left: 0;
  list-style: none;
}

.document-item {
  margin: 0;
  padding: 14px 16px;
  border: 1px solid #1f2937;
  border-radius: 8px;
  background: #111827;
}

.document-item strong,
.document-item span {
  display: block;
}

.document-item strong {
  margin-bottom: 8px;
  color: #f9fafb;
}

.empty-message {
  margin: 24px 0 0;
  padding: 20px;
  border: 1px dashed #374151;
  border-radius: 8px;
  color: #9ca3af;
  text-align: center;
}

.document-item span {
  color: #9ca3af;
  font-size: 13px;
}

.document-item:hover {
  border-color: #2563eb;
  background: #0f172a;
}

li {
  margin: 8px 0;
}

.theme-button {
  width: auto;
  min-width: 96px;
  margin-right: 12px;
  padding: 0 18px;
  background: #1f2937;
}

.theme-button:hover {
  background: #374151;
}

.logout-button {
  width: auto;
  min-width: 96px;
  padding: 0 18px;
  background: #374151;
}

.logout-button:hover {
  background: #4b5563;
}

@media (max-width: 760px) {
  main {
    padding: 40px 16px;
  }

  .dashboard-header {
    flex-direction: column;
  }

  .dashboard-grid {
    grid-template-columns: 1fr;
  }
}

.light-mode {
  background: #f3f4f6;
  color: #111827;
}

.light-mode p {
  color: #4b5563;
}

.light-mode input {
  border-color: #d1d5db;
  background: #ffffff;
  color: #111827;
}

.light-mode .dashboard-grid > section,
.light-mode .document-item {
  border-color: #e5e7eb;
  background: #ffffff;
}

.light-mode .document-item {
  background: #f9fafb;
}

.light-mode .document-item strong {
  color: #111827;
}

.light-mode .document-item span {
  color: #6b7280;
}

.light-mode .theme-button,
.light-mode .logout-button {
  background: #e5e7eb;
  color: #111827;
}

.light-mode .theme-button:hover,
.light-mode .logout-button:hover {
  background: #d1d5db;
}

.light-mode h1,
.light-mode h2 {
  color: #111827;
}

.app-footer {
  width: 100%;
  margin-top: 48px;
  color: #6b7280;
  font-size: 13px;
  text-align: center;
}

.light-mode .app-footer {
  color: #6b7280;
}
</style>
