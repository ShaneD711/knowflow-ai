package com.knowflow.modules.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.knowflow.modules.knowledge.entity.KbDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文档Mapper接口
 */
@Mapper
public interface KbDocumentMapper extends BaseMapper<KbDocument> {
}
