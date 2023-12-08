package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureCreateVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureFilterVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureUpdateVO;
import com.triguard.backend.entity.vo.response.BloodPressure.BloodPressureFilteredVO;
import com.triguard.backend.service.BloodPressureService;
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
 * 用于血压相关Controller包含用户的血压信息的增删改查
 */
@Validated
@RestController
@RequestMapping("/api/blood-pressure")
@Tag(name = "血压相关接口")
public class BloodPressureController {
    @Resource
    BloodPressureService bloodPressureService;

    /**
     * 添加血压记录
     * @param vo 血压记录表单
     * @return 响应结果
     */
    @PostMapping("/create")
    @Operation(summary = "添加血压记录")
    public RestBean<BloodPressure> recordBloodPressure(@RequestBody @Valid BloodPressureCreateVO vo,
                                              HttpServletRequest request){
        Integer accountId;
        if (vo.getAccountId() != null){
            accountId = vo.getAccountId();
        } else {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        BloodPressure bloodPressure = bloodPressureService.createBloodPressure(accountId, vo);
        return bloodPressure == null ? RestBean.failure(400, "添加血压记录失败") : RestBean.success(bloodPressure);
    }

    /**
     * 删除血压记录
     * @param id 血压记录id
     * @return 响应结果
     */
    @GetMapping("/delete")
    @Operation(summary = "删除血压记录")
    public RestBean<Void> deleteBloodPressure(@RequestParam Integer id){
        String message = bloodPressureService.deleteBloodPressure(id);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 修改血压记录
     * @param vo 血压记录表单
     * @return 响应结果
     */
    @PostMapping("/update")
    @Operation(summary = "修改血压记录")
    public RestBean<Void> updateBloodPressure(@RequestBody @Valid BloodPressureUpdateVO vo){
        String message = bloodPressureService.updateBloodPressure(vo);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 获取血压记录
     * @param date 日期
     * @return 响应结果
     */
    @GetMapping("/get-by-date")
    @Operation(summary = "按日期获取血压记录")
    public RestBean<List<BloodPressure>> getBloodPressure(@RequestParam(required = false) Integer accountId,
                                                          @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date,
                                           HttpServletRequest request){
        if (accountId == null){
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<BloodPressure> bloodPressureList = bloodPressureService.getBloodPressure(accountId, date);
        return RestBean.success(bloodPressureList);
    }

    /**
     * 获取血压记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 响应结果
     */
    @GetMapping("/get-by-date-range")
    @Operation(summary = "按日期范围获取血压记录")
    public RestBean<List<BloodPressure>> getBloodPressure(@RequestParam(required = false) Integer accountId,
                                                          @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String startDate,
                                                          @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String endDate,
                                                          HttpServletRequest request){
        if (accountId == null){
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<BloodPressure> bloodPressureList = bloodPressureService.getBloodPressure(accountId, startDate, endDate);
        return RestBean.success(bloodPressureList);
    }

    /**
     * 获取血压记录
     * @param vo 数据筛选表单
     * @return 响应结果
     */
    @PostMapping("/get-by-filter")
    @Operation(summary = "按条件筛选血压记录")
    public RestBean<BloodPressureFilteredVO> getBloodPressure(@RequestBody @Valid BloodPressureFilterVO vo,
                                                              HttpServletRequest request){
        Integer accountId;
        if (vo.getAccountId() != null){
            accountId = vo.getAccountId();
        } else {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<BloodPressure> bloodPressureList = bloodPressureService.getBloodPressure(accountId, vo);
        BloodPressureFilteredVO bloodPressureFilteredVO = new BloodPressureFilteredVO(bloodPressureList);
        return RestBean.success(bloodPressureFilteredVO);
    }
}
