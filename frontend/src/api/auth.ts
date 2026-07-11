import type { ApiResponse } from "../types/api";
import type { LoginRequest, LoginUser } from "../types/auth";

// 提交用户名和密码进行登录
export async function login(
  request: LoginRequest,
): Promise<ApiResponse<LoginUser>> {
  const response = await fetch("/api/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(request),
  });

  return response.json();
}
