package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Meal;
import com.triguard.backend.entity.vo.request.Meal.MealCreateVO;

import java.util.List;

public interface MealService extends IService<Meal> {
    Meal createMeal(Integer accountId, MealCreateVO mealCreateVO);

    Boolean deleteMeal(Integer accountId, Integer id);

    List<Meal> getMeals(Integer accountId, String date, String category);
}
