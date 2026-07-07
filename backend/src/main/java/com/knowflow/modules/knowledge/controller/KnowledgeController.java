package com.knowflow.modules.knowledge.controller;

import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.knowledge.dto.CreateDocumentRequest;
import com.knowflow.modules.knowledge.service.KnowledgeService;
import com.knowflow.modules.knowledge.vo.KbDocumentListItemVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    /**
     * 新增文档
     *
     * @param request 新增文档请求体DTO
     * @return 创建的文档ID
     */
    @PostMapping("/api/kb/documents")
    public ApiResponse<Long> createDocument(@RequestBody CreateDocumentRequest request) {
        return knowledgeService.createDocument(request);
    }

    /**
     * 查询文档列表
     *
     * @return 知识库文档列表VO列表
     */
    @GetMapping("/api/kb/documents")
    public ApiResponse<List<KbDocumentListItemVO>> listDocuments() {
        return knowledgeService.listDocuments();
    }
}
