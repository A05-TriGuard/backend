package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.BodyIndex;
import com.triguard.backend.entity.vo.request.BodyIndex.BodyIndexSetVO;

public interface BodyIndexService extends IService<BodyIndex> {
    BodyIndex setBodyIndex(Integer accountId, BodyIndexSetVO bodyIndexSetVO);
}
