package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Follow;
import com.triguard.backend.mapper.FollowMapper;
import com.triguard.backend.service.FollowService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    /**
     * 关注用户
     *
     * @param accountId       用户ID
     * @param momentAccountId 被关注用户ID
     * @return 是否关注成功
     */
    public Boolean saveFollow(Integer accountId, Integer momentAccountId) {
        Follow follow = new Follow();
        follow.setAccountId(accountId);
        follow.setFollowId(momentAccountId);
        return this.save(follow);
    }

    /**
     * 取消关注用户
     *
     * @param accountId       用户ID
     * @param momentAccountId 被关注用户ID
     * @return 是否取消关注成功
     */
    public Boolean removeFollow(Integer accountId, Integer momentAccountId) {
        return this.removeByMap(Map.of("account_id", accountId, "follow_id", momentAccountId));
    }

}
