package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据库中的食物信息
 */
@Data
@TableName("db_food")
@AllArgsConstructor
public class Food implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    Integer calories;
    Integer carbohydrates;
    Integer lipids;
    Integer cholesterol;
    Integer proteins;
    Integer fiber;
    Integer sodium;

    public Food() {
        this.id = null;
        this.name = null;
        this.calories = null;
        this.carbohydrates = null;
        this.lipids = null;
        this.cholesterol = null;
        this.proteins = null;
        this.fiber = null;
        this.sodium = null;
    }
}
