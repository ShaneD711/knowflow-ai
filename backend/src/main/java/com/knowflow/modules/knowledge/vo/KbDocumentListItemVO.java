package com.knowflow.modules.knowledge.vo;

import com.knowflow.modules.knowledge.entity.KbDocument;

import java.time.LocalDateTime;

/**
 * 文档列表项VO
 */
public record KbDocumentListItemVO(
        // 文档ID
        Long id,
        // 文档标题
        String title,
        // 创建人ID
        Long createdBy,
        // 创建时间
        LocalDateTime createdAt,
        // 更新时间
        LocalDateTime updatedAt
) {
    public static KbDocumentListItemVO from(KbDocument document) {
        return new KbDocumentListItemVO(
                document.getId(),
                document.getTitle(),
                document.getCreatedBy(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
