package com.knowflow.modules.system.controller;

import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.system.dto.LoginRequest;
import com.knowflow.modules.system.service.AuthService;
import com.knowflow.modules.system.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/login")
    public ApiResponse<UserVO> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
