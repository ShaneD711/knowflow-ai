package com.knowflow.modules.knowledge.dto;

/**
 * 修改文档请求体DTO
 *
 * @param title   文档标题
 * @param content 文档内容
 */
public record UpdateDocumentRequest(
        //文档标题
        String title,
        //文档内容
        String content) {




}
