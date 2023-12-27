package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Food;

import java.util.List;

public interface FoodService extends IService<Food> {

    List<Food> searchFood(String keyword);

    List<Food> getByNames(List<String> list);

    Integer getCalories(String food, Integer weight);
}
