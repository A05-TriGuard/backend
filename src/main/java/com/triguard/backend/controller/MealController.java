package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Food;
import com.triguard.backend.entity.dto.Meal;
import com.triguard.backend.entity.dto.MealGoal;
import com.triguard.backend.entity.vo.request.Meal.MealCreateVO;
import com.triguard.backend.entity.vo.response.Meal.MealInfoVO;
import com.triguard.backend.service.FoodService;
import com.triguard.backend.service.MealGoalService;
import com.triguard.backend.service.MealService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/meal")
@Tag(name = "饮食模块相关接口")
public class MealController {

    @Resource
    MealService mealService;

    @Resource
    MealGoalService mealGoalService;

    @Resource
    FoodService foodService;

    /**
     * 添加饮食记录
     * @param mealCreateVO 饮食记录VO
     * @param request 请求
     * @return 饮食记录
     */
    @PostMapping("/create")
    @Operation(summary = "添加饮食记录")
    public RestBean<Meal> createMeal(@RequestBody @Valid MealCreateVO mealCreateVO,
                                     HttpServletRequest request) {
        Integer accountId = mealCreateVO.getAccountId();
        if (mealCreateVO.getAccountId() == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        Meal meal = mealService.createMeal(accountId, mealCreateVO);
        if (meal == null) {
            return RestBean.failure(400, "添加饮食记录失败");
        }
        return RestBean.success(meal);
    }

    /**
     * 删除饮食记录
     * @param id 饮食记录ID
     * @param request 请求
     * @return 饮食记录
     */
    @GetMapping("/delete")
    @Operation(summary = "删除饮食记录")
    public RestBean<Void> deleteMeal(@RequestParam Integer id,
                                     HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        Boolean isSuccess = mealService.deleteMeal(accountId, id);
        return isSuccess ? RestBean.success() : RestBean.failure(400, "删除饮食记录失败");
    }

    /**
     * 获取饮食记录
     * @param date 日期
     * @param request 请求
     * @return 饮食记录
     */
    @GetMapping("/get")
    @Operation(summary = "获取饮食记录")
    public RestBean<MealInfoVO> getMeal(@RequestParam(required = false) Integer accountId,
                                        @RequestParam String date,
                                        @RequestParam String category,
                                        HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<Meal> mealList = mealService.getMeals(accountId, date, category);
        if (mealList == null) {
            return RestBean.success();
        }
        MealInfoVO mealInfoVO = new MealInfoVO();
        mealInfoVO.setMealList(mealList.stream().map(m -> {
            MealInfoVO.SimpleMealInfo simpleMealInfo = new MealInfoVO.SimpleMealInfo();
            BeanUtils.copyProperties(m, simpleMealInfo);
            return simpleMealInfo;
        }).toList());
        List<Food> foodList = foodService.getByNames(mealList.stream().map(Meal::getFood).toList());
        for (Meal meal : mealList) {
            for (Food food : foodList) {
                if (meal.getFood().equals(food.getName())) {
                    mealInfoVO.setCalories(mealInfoVO.getCalories() + food.getCalories() * meal.getWeight() / 100);
                    mealInfoVO.setCarbohydrates(mealInfoVO.getCarbohydrates() + food.getCarbohydrates() * meal.getWeight() / 100);
                    mealInfoVO.setLipids(mealInfoVO.getLipids() + food.getLipids() * meal.getWeight() / 100);
                    mealInfoVO.setCholesterol(mealInfoVO.getCholesterol() + food.getCholesterol() * meal.getWeight() / 100);
                    mealInfoVO.setProteins(mealInfoVO.getProteins() + food.getProteins() * meal.getWeight() / 100);
                    mealInfoVO.setFiber(mealInfoVO.getFiber() + food.getFiber() * meal.getWeight() / 100);
                    mealInfoVO.setSodium(mealInfoVO.getSodium() + food.getSodium() * meal.getWeight() / 100);
                    break;
                }
            }
        }
        return RestBean.success(mealInfoVO);
    }

    /**
     * 设置饮食目标
     * @param mealGoal 饮食目标
     * @param request 请求
     * @return 饮食目标
     */
    @PostMapping("/set-goal")
    @Operation(summary = "设置饮食目标")
    public RestBean<MealGoal> setMealGoal(@RequestBody MealGoal mealGoal,
                                      HttpServletRequest request) {
        Integer accountId = mealGoal.getAccountId();
        if (mealGoal.getAccountId() == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        Boolean isSuccess = mealGoalService.setMealGoal(accountId, mealGoal);
        return isSuccess ? RestBean.success() : RestBean.failure(400, "设置饮食目标失败");
    }

    /**
     * 获取饮食目标
     * @param request 请求
     * @return 饮食目标
     */
    @GetMapping("/get-goal")
    @Operation(summary = "获取饮食目标")
    public RestBean<MealGoal> getMealGoal(@RequestParam(required = false) Integer accountId,
                                          HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        MealGoal mealGoal = mealGoalService.query().eq("account_id", accountId).one();
        return RestBean.success(mealGoal);
    }


}
