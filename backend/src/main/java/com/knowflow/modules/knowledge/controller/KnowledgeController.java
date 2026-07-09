package com.knowflow.modules.knowledge.controller;

import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.knowledge.dto.CreateDocumentRequest;
import com.knowflow.modules.knowledge.dto.UpdateDocumentRequest;
import com.knowflow.modules.knowledge.service.KnowledgeService;
import com.knowflow.modules.knowledge.vo.KbDocumentDetailVO;
import com.knowflow.modules.knowledge.vo.KbDocumentListItemVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/kb")
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
    @PostMapping("/documents")
    public ApiResponse<Long> createDocument(@RequestBody CreateDocumentRequest request) {
        return knowledgeService.createDocument(request);
    }

    /**
     * 查询文档列表
     *
     * @return 知识库文档列表VO列表
     */
    @GetMapping("/documents")
    public ApiResponse<List<KbDocumentListItemVO>> listDocuments() {
        return knowledgeService.listDocuments();
    }

    /**
     * 查询文档详情
     *
     * @param id 文档ID
     * @return 文档详情VO
     */
    @GetMapping("/documents/{id}")
    public ApiResponse<KbDocumentDetailVO> getDocumentDetail(@PathVariable Long id) {
        return knowledgeService.getDocumentDetail(id);
    }

    /**
     * 删除文档
     *
     * @param id 文档ID
     * @return 删除是否成功
     */
    @DeleteMapping("/documents/{id}")
    public ApiResponse<Boolean> deleteDocument(@PathVariable Long id) {
        return knowledgeService.deleteDocument(id);
    }

    /**
     * 修改文档
     *
     * @param id      文档ID
     * @param request 修改文档请求体DTO
     * @return 修改是否成功
     */
    @PutMapping("/documents/{id}")
    public ApiResponse<Boolean> updateDocument(@PathVariable Long id, @RequestBody UpdateDocumentRequest request) {
        return knowledgeService.updateDocument(id, request);
    }
}
