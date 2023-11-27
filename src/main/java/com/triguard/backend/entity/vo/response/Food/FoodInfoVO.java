package com.triguard.backend.entity.vo.response.Food;

import lombok.Data;

/**
 * 食物信息返回VO
 */
@Data
public class FoodInfoVO {
    Integer id;
    String name;
    Integer calories;
    Integer carbohydrates;
    Integer lipids;
    Integer cholesterol;
    Integer proteins;
    Integer fiber;
    Integer sodium;
    Boolean isFavorite;

    public FoodInfoVO() {
        this.id = null;
        this.name = null;
        this.calories = null;
        this.carbohydrates = null;
        this.lipids = null;
        this.cholesterol = null;
        this.proteins = null;
        this.fiber = null;
        this.sodium = null;
        this.isFavorite = null;
    }
}
