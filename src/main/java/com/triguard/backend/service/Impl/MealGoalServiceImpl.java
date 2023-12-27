package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.MealGoal;
import com.triguard.backend.mapper.MealGoalMapper;
import com.triguard.backend.service.MealGoalService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MealGoalServiceImpl extends ServiceImpl<MealGoalMapper, MealGoal> implements MealGoalService {

    /**
     * 设置饮食目标
     * @param accountId 用户ID
     * @param mealGoal 饮食目标
     * @return 是否成功
     */
    public Boolean setMealGoal(Integer accountId, MealGoal mealGoal) {
        MealGoal mealGoalInDB = this.query().eq("account_id", accountId).one();
        if (mealGoalInDB == null) {
            mealGoal.setAccountId(accountId);
            return this.save(mealGoal);
        } else {
            mealGoal.setId(mealGoalInDB.getId());
            return this.updateById(mealGoal);
        }
    }
}
