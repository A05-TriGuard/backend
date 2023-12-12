package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("db_moment_favorite")
@AllArgsConstructor
@NoArgsConstructor
public class MomentFavorite implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer momentId;
    Date createTime;
}
