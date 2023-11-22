package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 数据库中的血压信息
 */
@Data
@TableName("db_blood_pressure")
@AllArgsConstructor
public class BloodPressure implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer sbp;
    Integer dbp;
    Integer heartRate;
    Integer arm;
    Integer feeling;
    Integer accountId;
    String remark;
    String date;
    String time;
    Date createTime;

    public BloodPressure() {
        this.id = null;
        this.sbp = null;
        this.dbp = null;
        this.heartRate = null;
        this.arm = null;
        this.feeling = null;
        this.accountId = null;
        this.remark = null;
        this.date = null;
        this.time = null;
        this.createTime = null;
    }
}
