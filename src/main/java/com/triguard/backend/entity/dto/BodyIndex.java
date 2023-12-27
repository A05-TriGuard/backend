package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("db_body_index")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyIndex {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    String sex;
    Integer age;
    Integer height;
    Integer weight;
    Integer level;
}
