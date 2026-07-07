package com.knowflow.modules.system.vo;

import com.knowflow.modules.system.entity.SysUser;

import java.time.LocalDateTime;

/**
 * 用户VO
 * @param id 用户ID
 * @param username 用户名
 * @param nickname 昵称
 * @param role 角色
 * @param status 状态
 * @param createdAt 创建时间
 * @param updatedAt 更新时间
 */
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
