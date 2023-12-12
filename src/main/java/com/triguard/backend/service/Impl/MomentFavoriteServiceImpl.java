package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.MomentFavorite;
import com.triguard.backend.mapper.MomentFavoriteMapper;
import com.triguard.backend.service.MomentFavoriteService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class MomentFavoriteServiceImpl extends ServiceImpl<MomentFavoriteMapper, MomentFavorite> implements MomentFavoriteService {

    /**
     * 收藏动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否收藏成功
     */
    public Boolean saveMomentFavorite(Integer accountId, Integer momentId) {
        MomentFavorite momentFavorite = new MomentFavorite();
        momentFavorite.setAccountId(accountId);
        momentFavorite.setMomentId(momentId);
        momentFavorite.setCreateTime(new Date());
        return this.save(momentFavorite);
    }

    /**
     * 取消收藏动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否取消收藏成功
     */
    public Boolean removeMomentFavorite(Integer accountId, Integer momentId) {
        return this.removeByMap(Map.of("account_id", accountId, "moment_id", momentId));
    }

}
