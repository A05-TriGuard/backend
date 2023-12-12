package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.MomentLike;
import com.triguard.backend.mapper.MomentLikeMapper;
import com.triguard.backend.service.MomentLikeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class MomentLikeServiceImpl extends ServiceImpl<MomentLikeMapper, MomentLike> implements MomentLikeService {

    /**
     * 点赞动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否点赞成功
     */
    public Boolean saveMomentLike(Integer accountId, Integer momentId) {
        MomentLike momentLike = new MomentLike();
        momentLike.setAccountId(accountId);
        momentLike.setMomentId(momentId);
        momentLike.setCreateTime(new Date());
        return this.save(momentLike);
    }

    /**
     * 取消点赞动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否取消点赞成功
     */
    public Boolean removeMomentLike(Integer accountId, Integer momentId) {
        return this.removeByMap(Map.of("account_id", accountId, "moment_id", momentId));
    }

}
