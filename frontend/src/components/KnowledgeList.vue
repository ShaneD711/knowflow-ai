<script setup lang="ts">
import type { KbDocumentListItem } from "../types/knowledge";

defineProps<{
  documents: KbDocumentListItem[];
}>();

defineEmits<{ "select-document": [id: number] }>();
</script>

<template>
  <section>
    <div class="section-header">
      <div>
        <h2>知识库文档</h2>
        <p>共 {{ documents.length }} 篇文档</p>
      </div>
    </div>

    <p v-if="documents.length === 0" class="empty-message">暂无文档</p>

    <ul v-else class="document-list">
      <li v-for="document in documents" :key="document.id">
        <button
          type="button"
          class="document-item"
          @click="$emit('select-document', document.id)"
        >
          <strong>{{ document.title }}</strong>
          <span>更新时间：{{ document.updatedAt }}</span>
        </button>
      </li>
    </ul>
  </section>
</template>
