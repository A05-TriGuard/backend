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

    /**
     * 根据食物名称列表获取食物记录
     * @param list 食物名称列表
     * @return 响应结果
     */
    public List<Food> getByNames(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.query()
                .in("name", list)
                .list();
    }

    /**
     * 获取食物热量
     * @param food 食物名称
     * @param weight 食物重量
     * @return 食物热量
     */
    public Integer getCalories(String food, Integer weight) {
        Food food1 = this.query()
                .select("calories")
                .eq("name", food)
                .one();
        if (food1 == null) {
            return 0;
        } else {
            return food1.getCalories() * weight / 100;
        }
    }

}
