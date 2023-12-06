package com.triguard.backend.controller;


import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.BloodSugar;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureUpdateVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarCreateVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarFilterVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarUpdateVO;
import com.triguard.backend.entity.vo.response.BloodSugar.BloodSugarFilteredVO;
import com.triguard.backend.service.BloodSugarService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用于血糖相关Controller包含用户的血糖信息的增删改查
 */
@Validated
@RestController
@RequestMapping("/api/blood-sugar")
@Tag(name = "血糖相关接口")
public class BloodSugarController {

    @Resource
    BloodSugarService bloodSugarService;

    /**
     * 添加血糖记录
     * @param vo 血糖记录表单
     * @return 响应结果
     */
    @PostMapping("/create")
    @Operation(summary = "添加血糖记录")
    public RestBean<BloodSugar> recordBloodSugar(@RequestBody @Valid BloodSugarCreateVO vo,
                                                 HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        BloodSugar bloodSugar = bloodSugarService.createBloodSugar(accountId, vo);
        return bloodSugar == null ? RestBean.failure(400, "添加血糖记录失败") : RestBean.success(bloodSugar);
    }

    /**
     * 删除血糖记录
     * @param id 血糖记录id
     * @return 响应结果
     */
    @GetMapping("/delete")
    @Operation(summary = "删除血糖记录")
    public RestBean<Void> deleteBloodSugar(@RequestParam Integer id){
        String message = bloodSugarService.deleteBloodSugar(id);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 修改血糖记录
     * @param vo 血糖记录表单
     * @return 响应结果
     */
    @PostMapping("/update")
    @Operation(summary = "修改血糖记录")
    public RestBean<Void> updateBloodSugar(@RequestBody @Valid BloodSugarUpdateVO vo){
        String message = bloodSugarService.updateBloodSugar(vo);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 获取血糖记录
     * @param date 日期
     * @return 响应结果
     */
    @GetMapping("/get-by-date")
    @Operation(summary = "按日期获取血糖记录")
    public RestBean<List<BloodSugar>> getBloodSugarByDate(@RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date,
                                              HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<BloodSugar> bloodSugarList = bloodSugarService.getBloodSugar(accountId, date);
        return RestBean.success(bloodSugarList);
    }

    /**
     * 获取血糖记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 响应结果
     */
    @GetMapping("/get-by-date-range")
    @Operation(summary = "按日期范围获取血糖记录")
    public RestBean<List<BloodSugar>> getBloodSugarByDateRange(@RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String startDate,
                                                     @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String endDate,
                                                     HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<BloodSugar> bloodSugarList = bloodSugarService.getBloodSugar(accountId, startDate, endDate);
        return RestBean.success(bloodSugarList);
    }

    /**
     * 获取血糖记录
     * @param vo 数据筛选表单
     * @return 响应结果
     */
    @PostMapping("/get-by-filter")
    @Operation(summary = "按筛选条件获取血糖记录")
    public RestBean<BloodSugarFilteredVO> getBloodSugarByFilter(@RequestBody @Valid BloodSugarFilterVO vo,
                                                   HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<BloodSugar> bloodSugarList = bloodSugarService.getBloodSugar(accountId, vo);
        BloodSugarFilteredVO filteredVO = new BloodSugarFilteredVO(bloodSugarList);
        return RestBean.success(filteredVO);
    }

}
