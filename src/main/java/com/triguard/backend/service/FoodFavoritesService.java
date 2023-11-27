package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.FoodFavorites;

import java.util.List;

public interface FoodFavoritesService extends IService<FoodFavorites> {
    FoodFavorites addFoodFavorites(Integer accountId, Integer foodId);

    List<FoodFavorites> getFoodFavorites(Integer accountId);

    FoodFavorites getFoodFavorites(Integer accountId, Integer foodId);

    boolean deleteFoodFavorites(Integer accountId, Integer foodId);
}
