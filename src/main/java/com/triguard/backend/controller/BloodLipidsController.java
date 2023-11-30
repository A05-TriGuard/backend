package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.BloodLipids;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsCreateVO;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsFilterVO;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsUpdateVO;
import com.triguard.backend.entity.vo.response.BloodLipids.BloodLipidsFilteredVO;
import com.triguard.backend.service.BloodLipidsService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Valid
@RestController
@RequestMapping("/api/blood-lipids")
@Tag(name = "血脂相关", description = "包括用户血脂信息的增删改查。")
public class BloodLipidsController {

    @Resource
    BloodLipidsService bloodLipidsService;

    /**
     * 添加血脂记录
     * @param vo 血脂记录表单
     * @return 响应结果
     */
    @PostMapping("/create")
    @Operation(summary = "添加血脂记录")
    public RestBean<BloodLipids> recordBloodLipids(@RequestBody @Valid BloodLipidsCreateVO vo,
                                                   HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        BloodLipids bloodLipids = bloodLipidsService.createBloodLipids(accountId, vo);
        return bloodLipids == null ? RestBean.failure(400, "添加血脂记录失败") : RestBean.success(bloodLipids);
    }

    /**
     * 删除血脂记录
     * @param id 血脂记录id
     * @return 响应结果
     */
    @GetMapping("/delete")
    @Operation(summary = "删除血脂记录")
    public RestBean<Void> deleteBloodLipids(@RequestParam Integer id){
        String message = bloodLipidsService.deleteBloodLipids(id);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 更新血脂记录
     * @param vo 血脂记录表单
     * @return 响应结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新血脂记录")
    public RestBean<BloodLipids> updateBloodLipids(@RequestBody @Valid BloodLipidsUpdateVO vo){
        String message = bloodLipidsService.updateBloodLipids(vo);
        return message == null ? RestBean.success() : RestBean.failure(400, message);
    }

    /**
     * 根据日期获取血脂记录
     * @param date 日期
     * @return 响应结果
     */
    @GetMapping("/get-by-date")
    @Operation(summary = "获取血脂记录")
    public RestBean<List<BloodLipids>> getBloodLipidsByDate(@RequestParam String date,
                                                            HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<BloodLipids> bloodLipids = bloodLipidsService.getBloodLipids(accountId, date);
        return RestBean.success(bloodLipids);
    }

    /**
     * 根据日期范围获取血脂记录
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 响应结果
     */
    @GetMapping("/get-by-date-range")
    @Operation(summary = "获取血脂记录")
    public RestBean<List<BloodLipids>> getBloodLipidsByDateRange(@RequestParam String startDate,
                                                                 @RequestParam String endDate,
                                                                 HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<BloodLipids> bloodLipids = bloodLipidsService.getBloodLipids(accountId, startDate, endDate);
        return RestBean.success(bloodLipids);
    }

    /**
     * 根据筛选条件获取血脂记录及统计数据
     * @param vo 数据筛选表单
     * @return 响应结果
     */
    @PostMapping("/get-by-filter")
    @Operation(summary = "根据筛选条件获取血脂记录及统计数据")
    public RestBean<BloodLipidsFilteredVO> getBloodLipidsByFilter(@RequestBody @Valid BloodLipidsFilterVO vo,
                                                                  HttpServletRequest request){
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<BloodLipids> bloodLipids = bloodLipidsService.getBloodLipids(accountId, vo);
        BloodLipidsFilteredVO bloodLipidsFilteredVO = new BloodLipidsFilteredVO(bloodLipids);
        return RestBean.success(bloodLipidsFilteredVO);
    }
}
