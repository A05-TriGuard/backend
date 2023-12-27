package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("db_meal")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meal {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    String date;
    String category;
    String food;
    Integer calories;
    Integer weight;
    Date createTime;
}
