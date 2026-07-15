package com.knowflow.modules.knowledge.vo;

import com.knowflow.modules.knowledge.entity.KbDocumentChunk;

/**
 * 文档切片搜索结果VO
 */
public record KbDocumentChunkSearchResultVO(
        // 切片ID
        Long chunkId,
        // 所属文档ID
        Long documentId,
        // 切片序号，从1开始
        Integer chunkIndex,
        // 切片正文
        String content) {

    // 从实体类转换为VO
    public static KbDocumentChunkSearchResultVO from(KbDocumentChunk chunk) {
        return new KbDocumentChunkSearchResultVO(
                chunk.getId(),
                chunk.getDocumentId(),
                chunk.getChunkIndex(),
                chunk.getContent()
        );
    }
}
