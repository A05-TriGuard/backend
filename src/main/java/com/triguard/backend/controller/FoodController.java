package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Food;
import com.triguard.backend.entity.dto.FoodFavorites;
import com.triguard.backend.entity.vo.response.Food.FoodInfoVO;
import com.triguard.backend.entity.vo.response.Food.SimpleFoodInfoVO;
import com.triguard.backend.service.FoodFavoritesService;
import com.triguard.backend.service.FoodService;
import com.triguard.backend.service.MedicineService;
import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.HistoryUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 用于食物相关Controller包含用户的食物信息的查找，管理员可以进行增删改查
 */
@Validated
@RestController
@RequestMapping("/api/food")
@Tag(name = "食物相关接口")
public class FoodController {

    @Resource
    FoodService foodService;

    @Resource
    FoodFavoritesService foodFavoritesService;

    @Resource
    HistoryUtils historyUtils;

    /**
     * 根据关键词搜索食物
     * @param keyword 关键词
     * @return 响应结果
     */
    @GetMapping("/search")
    @Operation(summary = "根据关键词搜索食物")
    public RestBean<List<SimpleFoodInfoVO>> searchFood(@RequestParam @NotNull String keyword,
                                                       HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Food> foods = foodService.searchFood(keyword);
        List<SimpleFoodInfoVO> simpleFoodInfoVOS = foods.stream().map(food -> {
            SimpleFoodInfoVO simpleFoodInfoVO = new SimpleFoodInfoVO();
            BeanUtils.copyProperties(food, simpleFoodInfoVO);
            return simpleFoodInfoVO;
        }).toList();
        historyUtils.saveStringHistory(ConstUtils.SEARCH_FOOD_HISTORY + accountId, keyword);
        return RestBean.success(simpleFoodInfoVOS);
    }

    /**
     * 获取当前用户搜索食物的历史记录
     * @return 响应结果
     */
    @GetMapping("/search-history")
    @Operation(summary = "获取当前用户搜索食物的历史记录")
    public RestBean<List<String>> getSearchHistory(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return RestBean.success(historyUtils.getStringHistory(ConstUtils.SEARCH_FOOD_HISTORY + accountId));
    }

    /**
     * 获取食物信息
     * @param id 食物id
     * @return 响应结果
     */
    @GetMapping("/info")
    @Operation(summary = "获取食物信息")
    public RestBean<FoodInfoVO> getFoodInfo(@RequestParam @NotNull Integer id,
                                            HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        historyUtils.saveIntegerHistory(ConstUtils.GET_FOOD_INFO_HISTORY + accountId, id);
        Food food = foodService.getById(id);
        FoodInfoVO foodInfoVO = new FoodInfoVO();
        BeanUtils.copyProperties(food, foodInfoVO);
        foodInfoVO.setIsFavorite(foodFavoritesService.getFoodFavorites(accountId, id) != null);
        return RestBean.success(foodInfoVO);
    }

    /**
     * 获取食物信息历史记录
     * @return 响应结果
     */
    @GetMapping("/info-history")
    @Operation(summary = "获取当前用户查看食物详细信息的历史记录")
    public RestBean<List<SimpleFoodInfoVO>> getFoodInfoHistory(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Integer> foodIds = historyUtils.getIntegerHistory(ConstUtils.GET_FOOD_INFO_HISTORY + accountId);
        if (foodIds == null || foodIds.isEmpty()) {
            return RestBean.success(new ArrayList<>());
        }
        List<Food> foods = foodService.listByIds(foodIds);
        List<SimpleFoodInfoVO> simpleFoodInfoVOS = foods.stream()
                .sorted(Comparator.comparingInt(m -> foodIds.indexOf(m.getId())))
                .map(food -> {
                    SimpleFoodInfoVO simpleFoodInfoVO = new SimpleFoodInfoVO();
                    BeanUtils.copyProperties(food, simpleFoodInfoVO);
                    return simpleFoodInfoVO;
                }).toList();
        return RestBean.success(simpleFoodInfoVOS);
    }

    /**
     * 添加食物收藏
     * @return 响应结果
     */
    @PostMapping("/favorites/add")
    @Operation(summary = "收藏食物")
    public RestBean<FoodFavorites> addFoodFavorites(@RequestParam @NotNull Integer foodId,
                                                    HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        FoodFavorites foodFavorites = foodFavoritesService.addFoodFavorites(accountId, foodId);
        if (foodFavorites != null) {
            return RestBean.success(foodFavorites);
        }
        return RestBean.failure(400, "添加失败");
    }

    /**
     * 删除食物收藏
     * @return 响应结果
     */
    @GetMapping("/favorites/delete")
    @Operation(summary = "取消收藏食物")
    public RestBean<Void> deleteFoodFavorites(@RequestParam @NotNull Integer foodId,
                                              HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        boolean isSuccess = foodFavoritesService.deleteFoodFavorites(accountId, foodId);
        return isSuccess ? RestBean.success() : RestBean.failure(400, "删除失败");
    }

    /**
     * 获取食物收藏列表
     * @return 响应结果
     */
    @GetMapping("/favorites/list")
    @Operation(summary = "获取食物收藏列表")
    public RestBean<List<SimpleFoodInfoVO>> getFoodFavoritesList(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<FoodFavorites> foodFavorites = foodFavoritesService.getFoodFavorites(accountId);
        List<SimpleFoodInfoVO> simpleFoodInfoVOS = new java.util.ArrayList<>(foodFavorites.stream().map(foodFavorite -> {
            SimpleFoodInfoVO simpleFoodInfoVO = new SimpleFoodInfoVO();
            BeanUtils.copyProperties(foodService.getById(foodFavorite.getFoodId()), simpleFoodInfoVO);
            return simpleFoodInfoVO;
        }).toList());
        simpleFoodInfoVOS.sort((o1, o2) -> o2.getId() - o1.getId());
        return RestBean.success(simpleFoodInfoVOS);
    }

    // TODO: 管理员操作
    /**
     * 添加食物
     * @param food 食物信息
     * @return 响应结果
     */
    @PostMapping("/create")
    @Operation(summary = "添加食物", description = "添加食物。")
    public RestBean<Food> createFood(@RequestBody @NotNull Food food){
        foodService.save(food);
        return RestBean.success(food);
    }
}
