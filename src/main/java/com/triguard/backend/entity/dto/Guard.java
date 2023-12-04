package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("db_guard")
@AllArgsConstructor
public class Guard {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer wardId;
    String wardNickname;
    Integer guardianId;
    String guardianNickname;
    Boolean isAccepted;

    public Guard() {
        this.id = null;
        this.wardId = null;
        this.wardNickname = null;
        this.guardianId = null;
        this.guardianNickname = null;
        this.isAccepted = false;
    }
}
