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
@TableName("db_moment_comment")
@AllArgsConstructor
@NoArgsConstructor
public class MomentComment implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer momentId;
    Integer accountId;
    String content;
    Integer quoteCommentId;
    Date createTime;
}
