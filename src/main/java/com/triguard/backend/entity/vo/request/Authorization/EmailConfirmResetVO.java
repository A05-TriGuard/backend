package com.triguard.backend.entity.vo.request.Authorization;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class EmailConfirmResetVO {
    @Email
    String email;
    @Length(max = 6, min = 6)
    String code;
}