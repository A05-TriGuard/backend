package com.triguard.backend.entity.vo.response.Medicine;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 药品信息VO
 */
@Data
public class MedicineInfoVO {
    Integer id;
    String name;
    String component;
    String usage;
    String caution;
    String sideEffect;
    String interaction;
    String expiry;
    String condition;
    String image;
    Boolean isFavorite;

    public MedicineInfoVO() {
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
        this.isFavorite = null;
    }
}
