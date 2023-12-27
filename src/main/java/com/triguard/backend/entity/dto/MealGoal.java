package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("db_meal_goal")
@AllArgsConstructor
@NoArgsConstructor
public class MealGoal {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer calories;
    Integer carbohydrates;
    Integer lipids;
    Integer cholesterol;
    Integer proteins;
    Integer fiber;
    Integer sodium;
}
