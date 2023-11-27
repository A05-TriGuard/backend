package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Medicine;
import com.triguard.backend.entity.dto.MedicineFavorites;
import com.triguard.backend.entity.vo.response.Medicine.MedicineInfoVO;
import com.triguard.backend.entity.vo.response.Medicine.SimpleMedicineInfoVO;
import com.triguard.backend.service.MedicineFavoritesService;
import com.triguard.backend.service.MedicineService;
import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.HistoryUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
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

    @Resource
    MedicineFavoritesService medicineFavoritesService;

    @Resource
    HistoryUtils historyUtils;

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
            BeanUtils.copyProperties(medicine, simpleMedicineInfoVO);
            return simpleMedicineInfoVO;
        }).toList();
        historyUtils.saveStringHistory(ConstUtils.SEARCH_MEDICINE_HISTORY + accountId, keyword);
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
        return RestBean.success(historyUtils.getStringHistory(ConstUtils.SEARCH_MEDICINE_HISTORY + accountId));
    }

    /**
     * 获取药品信息
     * @param id 药品id
     * @return 响应结果
     */
    @GetMapping("/info")
    @Operation(summary = "获取药品信息", description = "根据药品id获取药品信息。")
    public RestBean<MedicineInfoVO> getMedicineInfo(@RequestParam @NotNull Integer id,
                                              HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        historyUtils.saveIntegerHistory(ConstUtils.GET_MEDICINE_INFO_HISTORY + accountId, id);
        Medicine medicine = medicineService.getById(id);
        MedicineInfoVO medicineInfoVO = new MedicineInfoVO();
        BeanUtils.copyProperties(medicine, medicineInfoVO);
        medicineInfoVO.setIsFavorite(medicineFavoritesService.getMedicineFavorites(accountId, id) != null);
        return RestBean.success(medicineInfoVO);
    }

    /**
     * 获取当前用户查看药品信息记录
     * @return 响应结果
     */
    @GetMapping("/info-history")
    @Operation(summary = "获取当前用户查看药品信息记录", description = "获取当前用户查看药品信息记录。")
    public RestBean<List<SimpleMedicineInfoVO>> getGetMedicineInfoHistory(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Integer> medicineIds = historyUtils.getIntegerHistory(ConstUtils.GET_MEDICINE_INFO_HISTORY + accountId);
        if (medicineIds == null || medicineIds.isEmpty()) {
            return RestBean.success(new java.util.ArrayList<>());
        }
        List<Medicine> medicines = medicineService.listByIds(medicineIds);
        List<SimpleMedicineInfoVO> simpleMedicineInfoVOS = new java.util.ArrayList<>(medicines.stream().map(medicine -> {
            SimpleMedicineInfoVO simpleMedicineInfoVO = new SimpleMedicineInfoVO();
            BeanUtils.copyProperties(medicine, simpleMedicineInfoVO);
            return simpleMedicineInfoVO;
        }).toList());
        simpleMedicineInfoVOS.sort((o1, o2) -> o2.getId() - o1.getId());
        return RestBean.success(simpleMedicineInfoVOS);
    }

    /**
     * 添加药品收藏
     * @param medicineId 药品id
     * @return 响应结果
     */
    @PostMapping("/favorites/add")
    @Operation(summary = "添加药品收藏", description = "添加药品收藏。")
    public RestBean<MedicineFavorites> addMedicineFavorites(@RequestParam @NotNull Integer medicineId,
                                                            HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        MedicineFavorites medicineFavorites = medicineFavoritesService.addMedicineFavorites(accountId, medicineId);
        if (medicineFavorites != null) {
            return RestBean.success(medicineFavorites);
        }
        return RestBean.failure(400, "添加失败");
    }

    /**
     * 删除药品收藏
     * @param medicineId 药品id
     * @return 响应结果
     */
    @GetMapping("/favorites/delete")
    @Operation(summary = "删除药品收藏", description = "删除药品收藏。")
    public RestBean<MedicineFavorites> deleteMedicineFavorites(@RequestParam @NotNull Integer medicineId,
                                                               HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        boolean isSuccess = medicineFavoritesService.deleteMedicineFavorites(accountId, medicineId);
        return isSuccess ? RestBean.success() : RestBean.failure(400, "删除失败");
    }

    /**
     * 获取药品收藏列表
     * @return 响应结果
     */
    @GetMapping("/favorites/list")
    @Operation(summary = "获取药品收藏列表", description = "获取药品收藏列表。")
    public RestBean<List<SimpleMedicineInfoVO>> getMedicineFavoritesList(HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<MedicineFavorites> medicineFavorites = medicineFavoritesService.getMedicineFavorites(accountId);
        List<SimpleMedicineInfoVO> simpleMedicineInfoVOS = new java.util.ArrayList<>(medicineFavorites.stream().map(medicineFavorite -> {
            SimpleMedicineInfoVO simpleMedicineInfoVO = new SimpleMedicineInfoVO();
            BeanUtils.copyProperties(medicineService.getById(medicineFavorite.getMedicineId()), simpleMedicineInfoVO);
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
