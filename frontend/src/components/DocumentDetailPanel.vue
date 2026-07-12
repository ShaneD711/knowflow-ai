<script setup lang="ts">
import type { KbDocumentDetail } from "../types/knowledge";

defineProps<{
  document: KbDocumentDetail;
}>();
</script>

<template>
  <section class="document-detail-panel">
    <header class="document-detail-header">
      <h2>{{ document.title }}</h2>
      <p>更新时间：{{ document.updatedAt }}</p>
    </header>

    <article class="document-content">
      {{ document.content }}
    </article>
  </section>
</template>
