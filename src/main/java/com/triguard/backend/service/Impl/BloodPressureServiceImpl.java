package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureCreateVO;
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
}
