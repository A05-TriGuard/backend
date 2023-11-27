package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_food_favorites")
@AllArgsConstructor
public class FoodFavorites {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer foodId;
    Date createdAt;

    public FoodFavorites() {
        this.id = null;
        this.accountId = null;
        this.foodId = null;
        this.createdAt = new Date();
    }
}
