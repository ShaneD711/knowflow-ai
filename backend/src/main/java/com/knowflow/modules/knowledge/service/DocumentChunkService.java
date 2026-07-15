package com.knowflow.modules.knowledge.service;

import com.knowflow.modules.knowledge.entity.KbDocumentChunk;
import com.knowflow.modules.knowledge.mapper.KbDocumentChunkMapper;
import org.springframework.stereotype.Service;

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
     * @param documentId
     * @param content
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
        for(int start =0;start<content.length();start = start + CHUNK_SIZE){

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
}
