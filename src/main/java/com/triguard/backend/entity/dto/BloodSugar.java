package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 数据库中的血糖信息
 */
@Data
@TableName("db_blood_sugar")
@AllArgsConstructor
public class BloodSugar implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Float bs; // 血糖值
    Integer meal;
    Integer feeling;
    String remark;
    String date;
    String time;
    Date createTime;

    public BloodSugar() {
        this.id = null;
        this.accountId = null;
        this.bs = null;
        this.meal = null;
        this.feeling = null;
        this.remark = null;
        this.date = null;
        this.time = null;
        this.createTime = null;
    }
}
