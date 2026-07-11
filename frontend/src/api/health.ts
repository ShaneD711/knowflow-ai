import type { ApiResponse } from "../types/api";
import type { HealthData } from "../types/health";

// 查询后端健康状态
export async function getHealth(): Promise<ApiResponse<HealthData>> {
  const response = await fetch("/api/health");
  return response.json();
}
