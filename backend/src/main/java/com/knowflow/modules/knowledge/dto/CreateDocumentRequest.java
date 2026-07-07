package com.knowflow.modules.knowledge.dto;

/**
 * 新增文档请求体DTO
 *
 */
public record CreateDocumentRequest(
        // 文档标题
        String title,
        // 文档内容
        String content
) {

}
