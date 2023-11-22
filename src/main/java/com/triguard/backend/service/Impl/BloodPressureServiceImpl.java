package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureCreateVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureFilterVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureUpdateVO;
import com.triguard.backend.mapper.BloodPressureMapper;
import com.triguard.backend.service.BloodPressureService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 血压信息处理相关服务
 */
@Service
public class BloodPressureServiceImpl extends ServiceImpl<BloodPressureMapper, BloodPressure> implements BloodPressureService {

    /**
     * 添加血压记录
     * @param vo 血压记录表单
     * @return 响应结果
     */
    public BloodPressure createBloodPressure(Integer accountId, BloodPressureCreateVO vo) {
        BloodPressure bloodPressure = new BloodPressure();
        BeanUtils.copyProperties(vo, bloodPressure);
        bloodPressure.setAccountId(accountId);
        bloodPressure.setCreateTime(new Date());
        return this.save(bloodPressure) ? bloodPressure : null;
    }

    /**
     * 删除血压记录
     * @param id 血压记录id
     * @return 响应结果
     */
    public String deleteBloodPressure(Integer id) {
        return this.removeById(id) ? null : "删除血压记录失败";
    }

    /**
     * 修改血压记录
     * @param vo 血压记录表单
     * @return 响应结果
     */
    public String updateBloodPressure(BloodPressureUpdateVO vo) {
        BloodPressure bloodPressure = new BloodPressure();
        BeanUtils.copyProperties(vo, bloodPressure);
        return this.updateById(bloodPressure) ? null : "修改血压记录失败";
    }

    /**
     * 获取血压记录
     * @param accountId 用户id
     * @param date 日期
     * @return 响应结果
     */
    public List<BloodPressure> getBloodPressure(Integer accountId, String date) {
        return this.query()
                .eq("account_id", accountId)
                .likeRight("create_time", date)
                .orderByDesc("create_time")
                .list();
    }

    /**
     * 获取血压记录
     * @param accountId 用户id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 响应结果
     */
    public List<BloodPressure> getBloodPressure(Integer accountId, String startDate, String endDate) {
        return this.query()
                .eq("account_id", accountId)
                .between("date", startDate, endDate)
                .orderByDesc("date")
                .orderByDesc("time")
                .list();
    }

    /**
     * 获取血压记录
     * @param accountId 用户id
     * @param vo 条件筛选表单
     * @return 响应结果
     */
    public List<BloodPressure> getBloodPressure(Integer accountId, BloodPressureFilterVO vo) {
        QueryChainWrapper<BloodPressure> query = this.query()
                .eq("account_id", accountId)
                .between("date", vo.getStartDate(), vo.getEndDate())
                .between("time", vo.getStartTime(), vo.getEndTime())
                .ge("sbp", vo.getMinSbp())
                .le("sbp", vo.getMaxSbp())
                .ge("dbp", vo.getMinDbp())
                .le("dbp", vo.getMaxDbp())
                .ge("heart_rate", vo.getMinHeartRate())
                .le("heart_rate", vo.getMaxHeartRate());

        if (vo.getArm().endsWith("0")) {
            query = applyArmFilter(query, vo.getArm());
        }

        if (vo.getFeeling().endsWith("0")) {
            query = applyFeelingFilter(query, vo.getFeeling());
        }

        if (vo.getRemark().endsWith("0")) {
            query = applyRemarkFilter(query, vo.getRemark());
        }

        return query.orderByDesc("date")
                .orderByDesc("time")
                .list();
    }

    private QueryChainWrapper<BloodPressure> applyArmFilter(QueryChainWrapper<BloodPressure> query, String arm) {
        if (arm.charAt(0) == '0') {
            query = query.ne("arm", 0);
        }
        if (arm.charAt(1) == '0') {
            query = query.ne("arm", 1);
        }
        if (arm.charAt(2) == '0') {
            query = query.ne("arm", 2);
        }
        return query;
    }

    private QueryChainWrapper<BloodPressure> applyFeelingFilter(QueryChainWrapper<BloodPressure> query, String feeling) {
        if (feeling.charAt(0) == '0') {
            query = query.ne("feeling", 0);
        }
        if (feeling.charAt(1) == '0') {
            query = query.ne("feeling", 1);
        }
        if (feeling.charAt(2) == '0') {
            query = query.ne("feeling", 2);
        }
        return query;
    }

    private QueryChainWrapper<BloodPressure> applyRemarkFilter(QueryChainWrapper<BloodPressure> query, String remark) {
        if (remark.charAt(0) == '0') {
            query = query.isNotNull("remark");
        }
        if (remark.charAt(1) == '0') {
            query = query.isNull("remark");
        }
        return query;
    }
}
