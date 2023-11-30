package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_blood_lipids")
@AllArgsConstructor
public class BloodLipids implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Float tc;
    Float tg;
    Float hdl;
    Float ldl;
    Integer feeling;
    String remark;
    String date;
    String time;
    Date createTime;

    public BloodLipids() {
        this.id = null;
        this.accountId = null;
        this.tc = null;
        this.tg = null;
        this.hdl = null;
        this.ldl = null;
        this.feeling = null;
        this.remark = null;
        this.date = null;
        this.time = null;
        this.createTime = null;
    }
}
