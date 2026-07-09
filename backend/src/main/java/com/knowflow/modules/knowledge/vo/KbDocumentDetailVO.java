package com.knowflow.modules.knowledge.vo;

import com.knowflow.modules.knowledge.entity.KbDocument;

/**
 * 知识库文档详情VO
 * 文档详情，需返回文档内容content
 */
public record KbDocumentDetailVO(
        // 文档ID
        Long id,
        // 文档标题
        String title,
        // 文档内容
        String content,
        // 创建时间
        String createdAt,
        // 更新时间
        String updatedAt
) {
    public static KbDocumentDetailVO from(KbDocument document) {
        return new KbDocumentDetailVO(
                document.getId(),
                document.getTitle(),
                document.getContent(),
                document.getCreatedAt().toString(),
                document.getUpdatedAt().toString()
        );
    }

}
