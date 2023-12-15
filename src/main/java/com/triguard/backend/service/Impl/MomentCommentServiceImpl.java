package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.MomentComment;
import com.triguard.backend.mapper.MomentCommentMapper;
import com.triguard.backend.service.MomentCommentService;
import org.springframework.stereotype.Service;

@Service
public class MomentCommentServiceImpl extends ServiceImpl<MomentCommentMapper, MomentComment> implements MomentCommentService {
}
