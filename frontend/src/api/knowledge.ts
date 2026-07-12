import type { ApiResponse } from "../types/api";
import type { KbDocumentListItem, KbDocumentDetail } from "../types/knowledge";

// 查询知识库文档列表
export async function getDocuments(): Promise<
  ApiResponse<KbDocumentListItem[]>
> {
  const response = await fetch("/api/kb/documents");
  return response.json();
}

// 根据文档 ID 查询文档详情
export async function getDocumentDetail(
  id: number,
): Promise<ApiResponse<KbDocumentDetail>> {
  const response = await fetch(`/api/kb/documents/${id}`);
  return response.json();
}
