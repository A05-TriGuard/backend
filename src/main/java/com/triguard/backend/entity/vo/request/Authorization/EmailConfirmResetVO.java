package com.triguard.backend.entity.vo.request.Authorization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class EmailConfirmResetVO {
    @NotNull
    @Email
    String email;
    @Length(max = 6, min = 6)
    String code;
}