package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.MealGoal;

public interface MealGoalService extends IService<MealGoal> {
    Boolean setMealGoal(Integer accountId, MealGoal mealGoal);
}
