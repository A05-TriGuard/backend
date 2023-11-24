package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据库中的药物信息
 */
@Data
@TableName("db_medicine")
@AllArgsConstructor
public class Medicine implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String component;
    @TableField("`usage`")
    String usage;
    String caution;
    String sideEffect;
    String interaction;
    String expiry;
    @TableField("`condition`")
    String condition;
    @TableField("`image`")
    String image;

    public Medicine() {
        this.id = null;
        this.name = null;
        this.component = null;
        this.usage = null;
        this.caution = null;
        this.sideEffect = null;
        this.interaction = null;
        this.expiry = null;
        this.condition = null;
        this.image = null;
    }
}
