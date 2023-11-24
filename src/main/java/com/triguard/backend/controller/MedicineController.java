package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Medicine;
import com.triguard.backend.entity.vo.response.Medicine.SimpleMedicineInfoVO;
import com.triguard.backend.service.MedicineService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 查找药品
     * @param keyword 关键词
     * @return 响应结果
     */
    @GetMapping("/search")
    @Operation(summary = "查找药品", description = "根据关键词查找药品。")
    public RestBean<List<SimpleMedicineInfoVO>> searchMedicine(@RequestParam @NotNull String keyword,
                                                               HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Medicine> medicines = medicineService.searchMedicine(keyword);
        List<SimpleMedicineInfoVO> simpleMedicineInfoVOS = medicines.stream().map(medicine -> {
            SimpleMedicineInfoVO simpleMedicineInfoVO = new SimpleMedicineInfoVO();
            simpleMedicineInfoVO.setId(medicine.getId());
            simpleMedicineInfoVO.setName(medicine.getName());
            return simpleMedicineInfoVO;
        }).toList();
        medicineService.saveSearchHistory(accountId, keyword);
        return RestBean.success(simpleMedicineInfoVOS);
    }

    /**
     * 获取当前用户查找药品记录
     * @return 响应结果
     */
    @GetMapping("/search-history")
    @Operation(summary = "获取当前用户查找药品记录", description = "获取当前用户查找药品记录。")
    public RestBean<List<String>> getSearchHistory(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return RestBean.success(medicineService.getSearchHistory(accountId));
    }

    /**
     * 获取药品信息
     * @param id 药品id
     * @return 响应结果
     */
    @GetMapping("/info")
    @Operation(summary = "获取药品信息", description = "根据药品id获取药品信息。")
    public RestBean<Medicine> getMedicineInfo(@RequestParam @NotNull Integer id,
                                              HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        medicineService.saveGetMedicineInfoHistory(accountId, id);
        return RestBean.success(medicineService.getById(id));
    }

    /**
     * 获取当前用户查看药品信息记录
     * @return 响应结果
     */
    @GetMapping("/info-history")
    @Operation(summary = "获取当前用户查看药品信息记录", description = "获取当前用户查看药品信息记录。")
    public RestBean<List<SimpleMedicineInfoVO>> getGetMedicineInfoHistory(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Integer> medicineIds = medicineService.getGetMedicineInfoHistory(accountId);
        List<Medicine> medicines = medicineService.listByIds(medicineIds);
        List<SimpleMedicineInfoVO> simpleMedicineInfoVOS = new java.util.ArrayList<>(medicines.stream().map(medicine -> {
            SimpleMedicineInfoVO simpleMedicineInfoVO = new SimpleMedicineInfoVO();
            simpleMedicineInfoVO.setId(medicine.getId());
            simpleMedicineInfoVO.setName(medicine.getName());
            return simpleMedicineInfoVO;
        }).toList());
        simpleMedicineInfoVOS.sort((o1, o2) -> o2.getId() - o1.getId());
        return RestBean.success(simpleMedicineInfoVOS);
    }

    // TODO: 2021/10/12 管理员操作
    /**
     * 添加药品信息
     * @return 响应结果
     */
    @PostMapping("/create")
    @Operation(summary = "添加药品信息", description = "添加药品信息。")
    public RestBean<Medicine> createMedicine(@RequestBody Medicine medicine){
        medicineService.save(medicine);
        return RestBean.success(medicine);
    }
}
