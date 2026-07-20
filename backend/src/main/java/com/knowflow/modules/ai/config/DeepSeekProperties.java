package com.knowflow.modules.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 封装配置属性，包括 apiKey、baseUrl 和 model
 */
@ConfigurationProperties(prefix = "ai.deepseek")
public record DeepSeekProperties(
        String apiKey,
        String baseUrl,
        String model
) {
    public DeepSeekProperties {
        requireText(
                apiKey,
                "DeepSeek API Key 不能为空"
        );
        requireText(
                baseUrl,
                "DeepSeek Base URL 不能为空"
        );
        requireText(
                model,
                "DeepSeek 模型名称不能为空"
        );
    }

    private static void requireText(
            String value,
            String message
    ) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

}