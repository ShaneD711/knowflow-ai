package com.knowflow.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.system.dto.LoginRequest;
import com.knowflow.modules.system.entity.SysUser;
import com.knowflow.modules.system.mapper.SysUserMapper;
import com.knowflow.modules.system.vo.UserVO;
import org.springframework.stereotype.Service;

// AuthService auth表示认证，负责用户登录、注册等操作
@Service
public class AuthService {

    private final SysUserMapper sysUserMapper;

    public AuthService(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 登录
     *
     * @param request 登录请求
     * @return 登录成功后的用户信息
     */
    public ApiResponse<UserVO> login(LoginRequest request) {
        if (request == null) {
            return ApiResponse.fail(400, "用户名和密码不能为空");
        }
        if (request.username() == null || request.password() == null
                || request.username().isBlank() || request.password().isBlank()) {
            return ApiResponse.fail(400, "用户名或密码不能为空");
        }
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, request.username())
        );
        if (user == null) {
            return ApiResponse.fail(401, "用户名或者密码错误");
        }
        if (!request.password().equals(user.getPassword())) {
            return ApiResponse.fail(401, "用户名或密码错误");
        }
        if (!Integer.valueOf(1).equals(user.getStatus())) {
            return ApiResponse.fail(403, "账号已被禁用");
        }
        return ApiResponse.success(UserVO.from(user));
    }
}
