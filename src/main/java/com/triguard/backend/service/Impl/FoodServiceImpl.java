package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Food;
import com.triguard.backend.mapper.FoodMapper;
import com.triguard.backend.service.FoodService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 食物相关服务实现
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

    /**
     * 查找食物记录
     * @param keyword 关键词
     * @return 响应结果
     */
    public List<Food> searchFood(String keyword) {
        return this.query()
                .select("id", "name")
                .like("name", keyword)
                .list();
    }

}
