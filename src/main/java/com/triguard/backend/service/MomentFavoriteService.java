package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.MomentFavorite;

public interface MomentFavoriteService extends IService<MomentFavorite> {

    Boolean saveMomentFavorite(Integer accountId, Integer momentId);

    Boolean removeMomentFavorite(Integer accountId, Integer momentId);
}
