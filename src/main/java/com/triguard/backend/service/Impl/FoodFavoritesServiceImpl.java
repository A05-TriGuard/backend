package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.FoodFavorites;
import com.triguard.backend.mapper.FoodFavoritesMapper;
import com.triguard.backend.service.FoodFavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 食物收藏相关服务
 */
@Service
public class FoodFavoritesServiceImpl extends ServiceImpl<FoodFavoritesMapper, FoodFavorites> implements FoodFavoritesService {

    /**
     * 添加食物收藏
     * @param accountId 用户id
     * @param foodId 食物id
     * @return 更新后的食物收藏实体类
     */
    public FoodFavorites addFoodFavorites(Integer accountId, Integer foodId) {
        FoodFavorites foodFavorites = new FoodFavorites();
        foodFavorites.setAccountId(accountId);
        foodFavorites.setFoodId(foodId);
        return this.save(foodFavorites) ? foodFavorites : null;
    }

    /**
     * 根据用户id获取食物收藏列表
     * @param accountId 用户id
     * @return 食物收藏列表
     */
    public List<FoodFavorites> getFoodFavorites(Integer accountId) {
        return this.query()
                .eq("account_id", accountId)
                .orderByDesc("created_at")
                .list();
    }

    /**
     * 根据用户id和食物id获取食物收藏
     * @param accountId 用户id
     * @param foodId 食物id
     * @return 食物收藏
     */
    public FoodFavorites getFoodFavorites(Integer accountId, Integer foodId) {
        return this.query()
                .eq("account_id", accountId)
                .eq("food_id", foodId)
                .one();
    }

    /**
     * 根据用户id和食物id删除食物收藏
     * @param accountId 用户id
     * @param foodId 食物id
     * @return 是否删除成功
     */
    public boolean deleteFoodFavorites(Integer accountId, Integer foodId) {
        return this.removeById(this.getFoodFavorites(accountId, foodId).getId());
    }
}
