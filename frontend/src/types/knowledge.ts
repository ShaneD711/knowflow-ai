// 知识库文档列表项结构
export type KbDocumentListItem = {
  id: number;
  title: string;
  createdBy: number;
  createdAt: string;
  updatedAt: string;
};

// 知识库文档详情结构
export type KbDocumentDetail = {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
};
