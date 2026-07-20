package com.knowflow.modules.ai.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class DeepSeekConfigTest {

    @Test
    void shouldCreateDeepSeekRestClient() {
        // 创建 DeepSeekConfig 实例
        DeepSeekConfig deepSeekConfig = new DeepSeekConfig();

        // 创建 DeepSeekProperties 实例
        DeepSeekProperties properties = new DeepSeekProperties(
                "test-key",
                "https://api.deepseek.com",
                "deepseek-v4-flash"
        );

        // 调用 DeepSeekConfig的 deepSeekRestClient 方法创建 DeepSeek REST 客户端
        RestClient restClient = deepSeekConfig.deepSeekRestClient(
                RestClient.builder(),
                properties
        );

        assertNotNull(restClient);
    }


    @Test
    void shouldAddBearerAuthorizationHeader() {
        DeepSeekConfig deepSeekConfig = new DeepSeekConfig();

        DeepSeekProperties properties = new DeepSeekProperties(
                "test-key",
                "https://api.deepseek.com",
                "deepseek-v4-flash"
        );

        RestClient.Builder builder = RestClient.builder();

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        RestClient restClient = deepSeekConfig.deepSeekRestClient(
                builder,
                properties
        );

        server.expect(requestTo("https://api.deepseek.com/models"))
                .andExpect(header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer test-key"
                ))
                .andRespond(withSuccess(
                        "{}",
                        MediaType.APPLICATION_JSON
                ));

        restClient.get()
                .uri("/models")
                .retrieve()
                .toBodilessEntity();

        server.verify();
    }

    @Test
    void shouldAddJsonAcceptHeader() {
        DeepSeekConfig deepSeekConfig = new DeepSeekConfig();

        DeepSeekProperties properties = new DeepSeekProperties(
                "test-key",
                "https://api.deepseek.com",
                "deepseek-v4-flash"
        );

        RestClient.Builder builder = RestClient.builder();

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        RestClient restClient = deepSeekConfig.deepSeekRestClient(
                builder,
                properties
        );

        server.expect(requestTo("https://api.deepseek.com/models"))
                .andExpect(header(
                        HttpHeaders.ACCEPT,
                        MediaType.APPLICATION_JSON_VALUE
                ))
                .andRespond(withSuccess(
                        "{}",
                        MediaType.APPLICATION_JSON
                ));

        restClient.get()
                .uri("/models")
                .retrieve()
                .toBodilessEntity();

        server.verify();
    }

}
