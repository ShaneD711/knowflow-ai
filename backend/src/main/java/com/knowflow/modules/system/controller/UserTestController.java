package com.knowflow.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.system.entity.SysUser;
import com.knowflow.modules.system.mapper.SysUserMapper;
import com.knowflow.modules.system.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTestController {

    private final SysUserMapper sysUserMapper;

    // Controller 需要 Mapper（Controller 依赖 Mapper），Spring 负责把 Mapper 传进来，通过构造方法的参数传给成员变量（构造器注入）
    // 所以才能直接用 sysUserMapper.selectOne() 查数据库。
    public UserTestController(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @GetMapping("/api/test/user")
    public ApiResponse<UserVO> getUser() {
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, "admin")
        );

        return ApiResponse.success(UserVO.from(user));
    }
}