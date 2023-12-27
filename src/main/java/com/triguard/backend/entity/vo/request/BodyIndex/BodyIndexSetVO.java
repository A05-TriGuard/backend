package com.triguard.backend.entity.vo.request.BodyIndex;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BodyIndexSetVO {
    String sex;
    @Min(value = 0, message = "年龄不能小于0")@Max(value = 150, message = "年龄不能大于150")
    Integer age;
    @Min(value = 0, message = "身高不能小于0")@Max(value = 300, message = "身高不能大于300")
    Integer height;
    @Min(value = 0, message = "体重不能小于0")@Max(value = 300, message = "体重不能大于300")
    Integer weight;
    @Min(0)@Max(4)
    Integer level;
}
