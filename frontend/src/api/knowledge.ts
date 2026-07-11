import type { ApiResponse } from "../types/api";
import type { KbDocumentListItem } from "../types/knowledge";

// 查询知识库文档列表
export async function getDocuments(): Promise<
  ApiResponse<KbDocumentListItem[]>
> {
  const response = await fetch("/api/kb/documents");
  return response.json();
}
