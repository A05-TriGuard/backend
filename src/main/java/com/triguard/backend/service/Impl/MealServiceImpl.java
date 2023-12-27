package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Meal;
import com.triguard.backend.entity.vo.request.Meal.MealCreateVO;
import com.triguard.backend.mapper.MealMapper;
import com.triguard.backend.service.FoodService;
import com.triguard.backend.service.MealService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MealServiceImpl extends ServiceImpl<MealMapper, Meal> implements MealService {

    @Resource
    FoodService foodService;

    /**
     * 添加饮食记录
     * @param accountId 用户ID
     * @param mealCreateVO 饮食记录VO
     * @return 饮食记录
     */
    public Meal createMeal(Integer accountId, MealCreateVO mealCreateVO) {
        Meal meal = new Meal();
        meal.setAccountId(accountId);
        BeanUtils.copyProperties(mealCreateVO, meal);
        meal.setCreateTime(new Date());
        meal.setCalories(foodService.getCalories(meal.getFood(), meal.getWeight()));
        return save(meal) ? meal : null;
    }

    /**
     * 删除饮食记录
     * @param accountId 用户ID
     * @param id 饮食记录ID
     * @return 饮食记录
     */
    public Boolean deleteMeal(Integer accountId, Integer id) {
        Meal meal = getById(id);
        if (meal == null || !meal.getAccountId().equals(accountId)) {
            return false;
        }
        return removeById(id);
    }

    /**
     * 获取饮食记录
     * @param accountId 用户ID
     * @param date 日期
     * @param category 类别
     * @return 饮食记录
     */
    public List<Meal> getMeals(Integer accountId, String date, String category) {
        if (category.equals("全部")) {
            return this.query()
                    .eq("account_id", accountId)
                    .eq("date", date)
                    .list();
        } else {
            return this.query()
                    .eq("account_id", accountId)
                    .eq("date", date)
                    .eq("category", category)
                    .list();
        }
    }
}
