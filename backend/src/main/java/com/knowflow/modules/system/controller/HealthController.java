package com.knowflow.modules.system.controller;

import com.knowflow.common.result.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 健康检查controller
 */
@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.success(Map.of(
                "service", "knowflow-backend",
                "status", "UP",
                "time", LocalDateTime.now().toString()
        ));
    }
}
