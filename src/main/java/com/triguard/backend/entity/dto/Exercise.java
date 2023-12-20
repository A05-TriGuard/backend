package com.triguard.backend.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.triguard.backend.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("db_exercise")
@AllArgsConstructor
@NoArgsConstructor
public class Exercise implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer type;
    String startTime;
    String endTime;
    Integer duration;
    Integer feelings;
    String remark;
}
