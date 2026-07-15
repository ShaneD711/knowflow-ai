package com.knowflow.modules.knowledge.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.knowflow.common.result.ApiResponse;
import com.knowflow.modules.knowledge.entity.KbDocumentChunk;
import com.knowflow.modules.knowledge.mapper.KbDocumentChunkMapper;
import com.knowflow.modules.knowledge.vo.KbDocumentChunkSearchResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知识库文档切片服务类
 */
@Service
public class DocumentChunkService {

    // 全部对象共同使用一个固定的切片长度 500，业务运行期间不允许修改。
    private static final int CHUNK_SIZE = 500;

    private final KbDocumentChunkMapper kbDocumentChunkMapper;

    public DocumentChunkService(KbDocumentChunkMapper kbDocumentChunkMapper) {
        this.kbDocumentChunkMapper = kbDocumentChunkMapper;
    }

    /**
     * 为指定的文档生成切片
     *
     * @param documentId 文档ID
     * @param content    文档内容
     */
    public void createChunks(Long documentId, String content) {
        if (documentId == null) {
            // IllegalArgumentException 是 Java 自带的“参数不合法异常”。
            throw new IllegalArgumentException("文档ID不能为空");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("文档内容不能为空");
        }

        int chunkIndex = 1;
        for (int start = 0; start < content.length(); start = start + CHUNK_SIZE) {

            int end = Math.min(start + CHUNK_SIZE, content.length());
            String chunkContent = content.substring(start, end);

            KbDocumentChunk chunk = new KbDocumentChunk();
            chunk.setDocumentId(documentId);
            chunk.setChunkIndex(chunkIndex);
            chunk.setContent(chunkContent);
            chunk.setCharCount(chunkContent.length());
            kbDocumentChunkMapper.insert(chunk);

            chunkIndex++;
        }

    }

    /**
     * 根据关键词搜索文档切片
     *
     * @param keyword 搜索关键词
     * @return 匹配的文档切片VO
     */
    public ApiResponse<List<KbDocumentChunkSearchResultVO>> searchChunks(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return ApiResponse.fail(400, "搜索关键词不能为空");
        }
        // 对关键词进行标准化处理，trim() 会删除字符串首尾的空格。
        String normalizedKeyword = keyword.trim();

        List<KbDocumentChunk> chunks = kbDocumentChunkMapper.selectList(
                new LambdaQueryWrapper<KbDocumentChunk>()
                        .like(KbDocumentChunk::getContent, normalizedKeyword)
                        .orderByAsc(KbDocumentChunk::getDocumentId)
                        .orderByAsc(KbDocumentChunk::getChunkIndex));

        // 把数据库实体list转换为VO list
        List<KbDocumentChunkSearchResultVO> result = chunks.stream()
                // map 的作用是：把流中的每一个元素转换成另一种元素。
                .map(KbDocumentChunkSearchResultVO::from)
                .toList();

        return ApiResponse.success(result);
    }
}
