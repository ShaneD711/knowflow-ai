package com.knowflow.modules.system.vo;

import com.knowflow.modules.system.entity.SysUser;

import java.time.LocalDateTime;

public record UserVO(
        Long id,
        String username,
        String nickname,
        String role,
        Integer status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static UserVO from(SysUser user) {
        return new UserVO(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
