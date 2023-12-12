package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.MomentLike;

public interface MomentLikeService extends IService<MomentLike> {

    Boolean saveMomentLike(Integer accountId, Integer momentId);

    Boolean removeMomentLike(Integer accountId, Integer momentId);
}
