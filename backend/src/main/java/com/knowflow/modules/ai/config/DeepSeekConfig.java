package com.knowflow.modules.ai.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

/**
 * DeepSeek 配置类，用于创建 DeepSeek REST 客户端
 */
@Configuration // 标识这是一个配置类，用于定义 Spring Bean 定义和配置
@EnableConfigurationProperties(DeepSeekProperties.class) // 启用 DeepSeekProperties 配置属性
public class DeepSeekConfig {

    @Bean // 标识这是一个 Bean 定义，用于创建 DeepSeek REST 客户端
    public RestClient deepSeekRestClient(RestClient.Builder builder,
                                         DeepSeekProperties properties) {
        return builder
                // 设置deepSeek 的服务器地址
                .baseUrl(properties.baseUrl())
                // 设置默认请求头为 Authorization: Bearer + DeepSeek API Key ，用于认证
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + properties.apiKey()
                )
                // 设置默认请求头为 Accept: application/json ，用于指定返回的响应格式为 JSON
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }
}