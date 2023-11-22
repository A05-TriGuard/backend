package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Medicine;
import com.triguard.backend.mapper.MedicineMapper;
import com.triguard.backend.service.MedicineService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药物信息处理相关服务
 */
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

    /**
     * 查找药品记录
     * @param keyword 关键词
     * @return 响应结果
     */
    public List<Medicine> searchMedicine(String keyword) {
        return this.query()
                .like("name", keyword)
                .or()
                .like("component", keyword)
                .list();
    }
}
