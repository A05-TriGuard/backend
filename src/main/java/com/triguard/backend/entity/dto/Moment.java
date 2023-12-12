package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_moment")
@AllArgsConstructor
public class Moment implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    String profile;
    String username;
    String date;
    String content;
    String images;
    String video;
    @TableField("`class`")
    String classification;
    String category;
    Integer commentCount = 0;
    Integer likeCount = 0;
    Integer favoriteCount = 0;
    Date createdAt;

    public Moment() {
        this.createdAt = new Date();
    }
}
