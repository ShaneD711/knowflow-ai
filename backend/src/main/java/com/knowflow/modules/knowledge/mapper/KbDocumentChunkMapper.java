package com.knowflow.modules.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knowflow.modules.knowledge.entity.KbDocumentChunk;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文档切片Mapper接口
 */
@Mapper
public interface KbDocumentChunkMapper extends BaseMapper<KbDocumentChunk> {
}
