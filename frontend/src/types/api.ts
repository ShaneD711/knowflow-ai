// 后端统一响应格式：所有接口都会返回 code、message、data
export type ApiResponse<T> = {
  code: number;
  message: string;
  data: T | null;
};
