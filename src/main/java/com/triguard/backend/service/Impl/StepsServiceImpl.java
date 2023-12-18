package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Steps;
import com.triguard.backend.mapper.StepsMapper;
import com.triguard.backend.service.StepsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class StepsServiceImpl extends ServiceImpl<StepsMapper, Steps> implements StepsService {

    /**
     * 获取今日步数
     *
     * @param accountId 用户id
     * @return 今日步数
     */
    public Integer getTodaySteps(Integer accountId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(System.currentTimeMillis());
        Steps steps = this.query()
                .eq("account_id", accountId)
                .eq("date", today)
                .one();
        if (steps == null) {
            return 0;
        } else {
            return steps.getSteps();
        }
    }

    /**
     * 更新今日步数
     *
     * @param accountId 用户id
     * @param newSteps     步数
     * @return 是否成功
     */
    public boolean updateTodaySteps(Integer accountId, Integer newSteps) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(System.currentTimeMillis());
        Steps steps = this.query()
                .eq("account_id", accountId)
                .eq("date", today)
                .one();
        if (steps == null) {
            steps = new Steps();
            steps.setAccountId(accountId);
            steps.setSteps(newSteps);
            steps.setDate(today);
            return this.save(steps);
        } else {
            steps.setSteps(steps.getSteps() + newSteps);
            return this.updateById(steps);
        }
    }
}
