package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_guard_group")
@AllArgsConstructor
public class GuardGroup {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String createdBy;
    Date createdAt;

    public GuardGroup() {
        this.id = null;
        this.name = null;
        this.createdBy = null;
        this.createdAt = null;
    }
}
