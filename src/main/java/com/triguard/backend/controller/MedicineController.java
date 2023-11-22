package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Medicine;
import com.triguard.backend.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用于药品相关Controller包含用户的药品信息的查找，管理员可以进行增删改查
 */
@Validated
@RestController
@RequestMapping("/api/medicine")
@Tag(name = "药品相关", description = "包括用户药品信息的查找，管理员可以进行增删改查。")
public class MedicineController {

    @Resource
    MedicineService medicineService;

    /**
     * 查找药品记录
     * @param keyword 关键词
     * @return 响应结果
     */
    @GetMapping("/search")
    @Operation(summary = "查找药品记录")
    public RestBean<List<Medicine>> searchMedicine(@RequestParam String keyword){
        List<Medicine> medicines = medicineService.searchMedicine(keyword);
        return medicines == null ? RestBean.failure(400, "查找药品记录失败") : RestBean.success(medicines);
    }
}
