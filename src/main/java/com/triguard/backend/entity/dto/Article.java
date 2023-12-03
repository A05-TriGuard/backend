package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_article")
@AllArgsConstructor
public class Article implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    String type;
    String title;
    String subtitle;
    String cover;
    String content;
    Date createdAt;
    Date updatedAt;
}
