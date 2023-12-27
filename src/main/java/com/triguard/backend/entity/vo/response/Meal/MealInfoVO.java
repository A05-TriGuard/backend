package com.triguard.backend.entity.vo.response.Meal;

import lombok.Data;

import java.util.List;

@Data
public class MealInfoVO {
    List<SimpleMealInfo> mealList;
    Integer calories = 0;
    Integer carbohydrates = 0;
    Integer lipids = 0;
    Integer cholesterol = 0;
    Integer proteins = 0;
    Integer fiber = 0;
    Integer sodium = 0;

    @Data
    public static class SimpleMealInfo {
        String category;
        String food;
        Integer weight;
        Integer calories;
    }
}
