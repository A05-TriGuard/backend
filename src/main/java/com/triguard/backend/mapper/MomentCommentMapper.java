package com.triguard.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.triguard.backend.entity.dto.MomentComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MomentCommentMapper extends BaseMapper<MomentComment> {
}
