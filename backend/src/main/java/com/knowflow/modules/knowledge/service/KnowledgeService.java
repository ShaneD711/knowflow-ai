package com.knowflow.modules.knowledge.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.knowledge.dto.CreateDocumentRequest;
import com.knowflow.modules.knowledge.entity.KbDocument;
import com.knowflow.modules.knowledge.mapper.KbDocumentMapper;
import com.knowflow.modules.knowledge.vo.KbDocumentListItemVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeService {
    private final KbDocumentMapper kbDocumentMapper;

    public KnowledgeService(KbDocumentMapper kbDocumentMapper) {
        this.kbDocumentMapper = kbDocumentMapper;
    }

    /**
     * 新增文档
     *
     * @param request 新增文档请求体DTO
     * @return 创建的文档ID
     */
    public ApiResponse<Long> createDocument(CreateDocumentRequest request) {
        if (request == null || request.title() == null || request.content() == null || request.title().isBlank() || request.content().isBlank()) {
            return ApiResponse.fail(400, "标题和内容不能为空");
        }
        KbDocument document = new KbDocument();
        document.setTitle(request.title());
        document.setContent(request.content());
        // createdBy = 1L 是临时写死管理员用户
        document.setCreatedBy(1L);

        kbDocumentMapper.insert(document);

        // 返回文档ID
        return ApiResponse.success(document.getId());

    }

    /**
     * 查询文档列表
     *
     * @return 知识库文档列表VO
     */
    public ApiResponse<List<KbDocumentListItemVO>> listDocuments() {

        // KbDocument 是 Entity 类，查询数据库
        // kbDocumentMapper.selectList(...)意思是：查询多条数据，返回一个 List。
        List<KbDocument> documents = kbDocumentMapper.selectList(
                // LambdaQueryWrapper是MyBatis-Plus 的查询条件构造器
                // orderByDesc(KbDocument::getCreatedAt)意思是：按创建时间降序排序。
                new LambdaQueryWrapper<KbDocument>().orderByDesc(KbDocument::getCreatedAt));

        // documents 是 Entity 类的 List，需要转换为 KbDocumentListItemVO 类的 List，名字叫做 result
        List<KbDocumentListItemVO> result = documents.stream()
                // map() 方法意思是：将每个元素映射为另一个元素
                // 调用 KbDocumentListItemVO.from() 方法，将每个 KbDocument 转换为 KbDocumentListItemVO
                .map(KbDocumentListItemVO::from).toList();

        return ApiResponse.success(result);
    }
}
