package com.knowflow.modules.ai.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DeepSeekPropertiesTest {

    @Test
    void shouldRejectBlankApiKey() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DeepSeekProperties(
                        " ",
                        "https://api.deepseek.com",
                        "deepseek-v4-flash"
                )
        );
    }

    @Test
    void shouldRejectBlankBaseUrl() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DeepSeekProperties(
                        "test-key",
                        " ",
                        "deepseek-v4-flash"
                )
        );
    }

    @Test
    void shouldRejectBlankModel() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DeepSeekProperties(
                        "test-key",
                        "https://api.deepseek.com",
                        " "
                )
        );
    }
}