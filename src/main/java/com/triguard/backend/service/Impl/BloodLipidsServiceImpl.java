package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.BloodLipids;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsCreateVO;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsFilterVO;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsUpdateVO;
import com.triguard.backend.mapper.BloodLipidsMapper;
import com.triguard.backend.service.BloodLipidsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 血脂信息处理相关服务
 */
@Service
public class BloodLipidsServiceImpl extends ServiceImpl<BloodLipidsMapper, BloodLipids> implements BloodLipidsService {

    /**
     * 添加血脂记录
     * @param vo 血脂记录表单
     * @return 响应结果
     */
    public BloodLipids createBloodLipids(Integer accountId, BloodLipidsCreateVO vo) {
        BloodLipids bloodLipids = new BloodLipids();
        BeanUtils.copyProperties(vo, bloodLipids);
        bloodLipids.setAccountId(accountId);
        bloodLipids.setCreateTime(new Date());
        return this.save(bloodLipids) ? bloodLipids : null;
    }

    /**
     * 删除血脂记录
     * @param id 血脂记录id
     * @return 响应结果
     */
    public String deleteBloodLipids(Integer id) {
        return this.removeById(id) ? null : "删除血脂记录失败";
    }

    /**
     * 修改血脂记录
     * @param vo 血脂记录表单
     * @return 响应结果
     */
    public String updateBloodLipids(BloodLipidsUpdateVO vo) {
        BloodLipids bloodLipids = new BloodLipids();
        BeanUtils.copyProperties(vo, bloodLipids);
        return this.updateById(bloodLipids) ? null : "修改血脂记录失败";
    }

    /**
     * 获取血脂记录
     * @param accountId 用户id
     * @param date 日期
     * @return 响应结果
     */
    public List<BloodLipids> getBloodLipids(Integer accountId, String date) {
        return this.query()
                .eq("account_id", accountId)
                .eq("date", date)
                .orderByDesc("create_time")
                .list();
    }

    /**
     * 获取血脂记录
     * @param accountId 用户id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 响应结果
     * TODO: 血压血糖血脂的查询条件都是一样的，可以抽象出来
     */
    public List<BloodLipids> getBloodLipids(Integer accountId, String startDate, String endDate) {
        return this.query()
                .eq("account_id", accountId)
                .between("date", startDate, endDate)
                .orderByDesc("date")
                .orderByDesc("time")
                .list();
    }

    /**
     * 获取血脂记录
     * @param accountId 用户id
     * @param vo 血脂筛选条件表单
     * @return 响应结果
     */
    public List<BloodLipids> getBloodLipids(Integer accountId, BloodLipidsFilterVO vo) {
        QueryChainWrapper<BloodLipids> query = this.query()
                .eq("account_id", accountId)
                .between("date", vo.getStartDate(), vo.getEndDate())
                .between("time", vo.getStartTime(), vo.getEndTime())
                .ge("tc", vo.getMinTc())
                .le("tc", vo.getMaxTc())
                .ge("tg", vo.getMinTg())
                .le("tg", vo.getMaxTg())
                .ge("hdl", vo.getMinHdl())
                .le("hdl", vo.getMaxHdl())
                .ge("ldl", vo.getMinLdl())
                .le("ldl", vo.getMaxLdl());
        if (vo.getFeeling() != null) {
            query = applyFeelingFilter(query, vo.getFeeling());
        }
        if (vo.getRemark() != null) {
            query = applyRemarkFilter(query, vo.getRemark());
        }
        return query.orderByDesc("date")
                .orderByDesc("time")
                .list();
    }

    private QueryChainWrapper<BloodLipids> applyFeelingFilter(QueryChainWrapper<BloodLipids> query, String feeling) {
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

    private QueryChainWrapper<BloodLipids> applyRemarkFilter(QueryChainWrapper<BloodLipids> query, String remark) {
        if (remark.charAt(0) == '0') {
            query = query.isNotNull("remark");
        }
        if (remark.charAt(1) == '0') {
            query = query.isNull("remark");
        }
        return query;
    }
}
