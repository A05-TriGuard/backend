package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.BodyIndex;
import com.triguard.backend.entity.vo.request.BodyIndex.BodyIndexSetVO;
import com.triguard.backend.mapper.BodyIndexMapper;
import com.triguard.backend.service.BodyIndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BodyIndexServiceImpl extends ServiceImpl<BodyIndexMapper, BodyIndex> implements BodyIndexService {

    /**
     * 设置身体指标
     * @param accountId 用户id
     * @param bodyIndexSetVO 身体指标VO
     * @return 身体指标
     */
    public BodyIndex setBodyIndex(Integer accountId, BodyIndexSetVO bodyIndexSetVO) {
        BodyIndex bodyIndex = this.query().eq("account_id", accountId).one();
        if (bodyIndex == null) {
            bodyIndex = new BodyIndex();
            bodyIndex.setAccountId(accountId);
            BeanUtils.copyProperties(bodyIndexSetVO, bodyIndex);
            return this.save(bodyIndex) ? bodyIndex : null;
        } else {
            BeanUtils.copyProperties(bodyIndexSetVO, bodyIndex);
            return this.updateById(bodyIndex) ? bodyIndex : null;
        }
    }
}
