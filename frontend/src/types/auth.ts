// 登录接口请求参数
export type LoginRequest = {
  username: string;
  password: string;
};

// 登录成功后返回的用户信息，不包含 password
export type LoginUser = {
  id: number;
  username: string;
  nickname: string;
  role: string;
  status: number;
  createdAt: string;
  updatedAt: string;
};
