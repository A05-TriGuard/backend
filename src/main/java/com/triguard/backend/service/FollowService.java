package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Follow;

public interface FollowService extends IService<Follow> {

    Boolean saveFollow(Integer accountId, Integer momentAccountId);

    Boolean removeFollow(Integer accountId, Integer momentAccountId);
}
