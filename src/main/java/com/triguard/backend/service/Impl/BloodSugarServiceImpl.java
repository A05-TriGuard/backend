package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.dto.BloodSugar;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarCreateVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarFilterVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarUpdateVO;
import com.triguard.backend.mapper.BloodSugarMapper;
import com.triguard.backend.service.BloodSugarService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 血糖信息处理相关服务实现
 */
@Service
public class BloodSugarServiceImpl extends ServiceImpl<BloodSugarMapper, BloodSugar> implements BloodSugarService {

    /**
     * 添加血糖记录
     * @param accountId 用户id
     * @param vo 血糖记录表单
     * @return 响应结果
     */
    public BloodSugar createBloodSugar(Integer accountId, BloodSugarCreateVO vo) {
        BloodSugar bloodSugar = new BloodSugar();
        BeanUtils.copyProperties(vo, bloodSugar);
        bloodSugar.setAccountId(accountId);
        bloodSugar.setCreateTime(new Date());
        return this.save(bloodSugar) ? bloodSugar : null;
    }

    /**
     * 删除血糖记录
     * @param id 血糖记录id
     * @return 响应结果
     */
    public String deleteBloodSugar(Integer id) {
        return this.removeById(id) ? null : "删除血糖记录失败";
    }

    /**
     * 修改血糖记录
     * @param vo 血糖记录表单
     * @return 响应结果
     */
    public String updateBloodSugar(BloodSugarUpdateVO vo) {
        BloodSugar bloodSugar = new BloodSugar();
        BeanUtils.copyProperties(vo, bloodSugar);
        return this.updateById(bloodSugar) ? null : "修改血糖记录失败";
    }

    /**
     * 获取血糖记录
     * @param accountId 用户id
     * @param date 日期
     * @return 响应结果
     */
    public List<BloodSugar> getBloodSugar(Integer accountId, String date) {
        return this.query()
                .eq("account_id", accountId)
                .eq("date", date)
                .orderByDesc("create_time")
                .list();
    }

    /**
     * 获取血糖记录
     * @param accountId 用户id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 响应结果
     */
    public List<BloodSugar> getBloodSugar(Integer accountId, String startDate, String endDate) {
        return this.query()
                .eq("account_id", accountId)
                .between("date", startDate, endDate)
                .orderByDesc("date")
                .orderByDesc("time")
                .list();
    }

    /**
     * 获取筛选后的血糖记录
     * @param accountId 用户id
     * @param vo 血糖记录表单
     * @return 响应结果
     */
    public List<BloodSugar> getBloodSugar(Integer accountId, BloodSugarFilterVO vo) {
        QueryChainWrapper<BloodSugar> query = this.query()
                .eq("account_id", accountId)
                .between("date", vo.getStartDate(), vo.getEndDate())
                .between("time", vo.getStartTime(), vo.getEndTime())
                .ge("bs", vo.getMinBs())
                .le("bs", vo.getMaxBs());
        if (vo.getMeal() != null) {
            query = applyMealFilter(query, vo.getMeal());
        }
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

    private QueryChainWrapper<BloodSugar> applyMealFilter(QueryChainWrapper<BloodSugar> query, String meal) {
        if (meal.charAt(0) == '0') {
            query = query.ne("meal", 0);
        }
        if (meal.charAt(1) == '0') {
            query = query.ne("meal", 1);
        }
        if (meal.charAt(2) == '0') {
            query = query.ne("meal", 2);
        }
        return query;
    }

    private QueryChainWrapper<BloodSugar> applyFeelingFilter(QueryChainWrapper<BloodSugar> query, String feeling) {
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

    private QueryChainWrapper<BloodSugar> applyRemarkFilter(QueryChainWrapper<BloodSugar> query, String remark) {
        if (remark.charAt(0) == '0') {
            query = query.isNotNull("remark");
        }
        if (remark.charAt(1) == '0') {
            query = query.isNull("remark");
        }
        return query;
    }
}
