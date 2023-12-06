package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_guard_group_member")
@AllArgsConstructor
public class GuardGroupMember {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer groupId;
    Integer accountId;
    String role;
    String nickname;
    Date createdAt;

    public GuardGroupMember() {
        this.id = null;
        this.groupId = null;
        this.accountId = null;
        this.role = null;
        this.nickname = null;
        this.createdAt = null;
    }
}
