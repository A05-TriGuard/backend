package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("db_article")
@AllArgsConstructor
@NoArgsConstructor
public class Article implements BaseData{
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
