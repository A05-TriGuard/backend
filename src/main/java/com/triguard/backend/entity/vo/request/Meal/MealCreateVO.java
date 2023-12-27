package com.triguard.backend.entity.vo.request.Meal;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MealCreateVO {
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String date;
    String category;
    String food;
    Integer weight;
}
