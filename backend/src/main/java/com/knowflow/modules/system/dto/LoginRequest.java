package com.knowflow.modules.system.dto;

// record适合java17及以上版本 是一种“数据类”写法 适合用来定义 DTO / VO 这类只负责传递数据的对象。
public record LoginRequest(
        String username,
        String password
) {
}
