package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 用户收藏的药品
 */
@Data
@TableName("db_medicine_favorites")
@AllArgsConstructor
public class MedicineFavorites {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer medicineId;
    Date createdAt;

    public MedicineFavorites() {
        this.id = null;
        this.accountId = null;
        this.medicineId = null;
        this.createdAt = new Date();
    }
}
