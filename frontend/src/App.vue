<script setup lang="ts">
import { onMounted, ref } from "vue";
import { login } from "./api/auth";
import { getHealth } from "./api/health";
import { getDocuments } from "./api/knowledge";
import AppFooter from "./components/AppFooter.vue";
import DashboardHeader from "./components/DashboardHeader.vue";
import KnowledgeList from "./components/KnowledgeList.vue";
import LoginPanel from "./components/LoginPanel.vue";
import SystemStatusPanel from "./components/SystemStatusPanel.vue";
import type { LoginRequest, LoginUser } from "./types/auth";
import type { KbDocumentListItem } from "./types/knowledge";

// ==================== 页面状态 ====================

const backendStatus = ref("检测中...");
const backendService = ref("-");
const loginError = ref("");
const loginLoading = ref(false);
const currentUser = ref<LoginUser | null>(null);
const isDarkMode = ref(true);
const documents = ref<KbDocumentListItem[]>([]);

// ==================== 页面初始化 ====================

onMounted(async () => {
  const result = await getHealth();

  if (result.data) {
    backendStatus.value = result.data.status;
    backendService.value = result.data.service;
  }
});

// ==================== 页面操作函数 ====================

async function loadDocuments() {
  const result = await getDocuments();

  if (result.data) {
    documents.value = result.data;
  }
}

async function handleLogin(request: LoginRequest) {
  loginError.value = "";
  loginLoading.value = true;

  const result = await login(request);

  if (result.code !== 200 || !result.data) {
    loginError.value = result.message;
    currentUser.value = null;
    loginLoading.value = false;
    return;
  }

  currentUser.value = result.data;
  loginLoading.value = false;
  await loadDocuments();
}

function handleLogout() {
  currentUser.value = null;
  loginError.value = "";
  documents.value = [];
}

function toggleTheme() {
  isDarkMode.value = !isDarkMode.value;
}
</script>

<template>
  <main :class="{ 'light-mode': !isDarkMode }">
    <LoginPanel
      v-if="!currentUser"
      :error="loginError"
      :loading="loginLoading"
      @login="handleLogin"
    />

    <section v-else class="dashboard">
      <DashboardHeader
        :user="currentUser"
        :is-dark-mode="isDarkMode"
        @toggle-theme="toggleTheme"
        @logout="handleLogout"
      />

      <div class="dashboard-grid">
        <KnowledgeList :documents="documents" />
        <SystemStatusPanel
          :backend-status="backendStatus"
          :backend-service="backendService"
        />
      </div>
    </section>

    <AppFooter />
  </main>
</template>

<style src="./styles/app.css"></style>
