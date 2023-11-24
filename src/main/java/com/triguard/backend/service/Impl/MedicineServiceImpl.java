package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Medicine;
import com.triguard.backend.mapper.MedicineMapper;
import com.triguard.backend.service.MedicineService;
import com.triguard.backend.utils.ConstUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 药物信息处理相关服务
 */
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 查找药品记录
     * @param keyword 关键词
     * @return 响应结果
     */
    public List<Medicine> searchMedicine(String keyword) {
        return this.query()
                .select("id", "name")
                .like("name", keyword)
                .or()
                .like("component", keyword)
                .list();
    }

    /**
     * 保存用户查找药品记录
     * @param keyword 关键词
     */
    public void saveSearchHistory(Integer accountId , String keyword) {
        String key = ConstUtils.SEARCH_HISTORY + accountId;
        List<String> history = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (history != null && history.contains(keyword)) {
            stringRedisTemplate.opsForList().remove(key, 0, keyword);
        }
        stringRedisTemplate.opsForList().leftPush(key, keyword);
        stringRedisTemplate.opsForList().trim(key, 0, 9);
    }

    /**
     * 获取用户查找药品记录
     * @param accountId 用户id
     * @return 响应结果
     */
    public List<String> getSearchHistory(Integer accountId) {
        String key = ConstUtils.SEARCH_HISTORY + accountId;
        return stringRedisTemplate.opsForList().range(key, 0, 9);
    }

    /**
     * 保存用户查看药品信息记录
     * @param accountId 用户id
     * @param medicineId 药品id
     */
    public void saveGetMedicineInfoHistory(Integer accountId, Integer medicineId) {
        String key = ConstUtils.GET_MEDICINE_INFO_HISTORY + accountId;
        List<String> history = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (history != null && history.contains(medicineId.toString())) {
            stringRedisTemplate.opsForList().remove(key, 0, medicineId.toString());
        }
        stringRedisTemplate.opsForList().leftPush(key, medicineId.toString());
        stringRedisTemplate.opsForList().trim(key, 0, 9);
    }

    /**
     * 获取用户查看药品信息记录
     * @param accountId 用户id
     * @return 响应结果
     */
    public List<Integer> getGetMedicineInfoHistory(Integer accountId) {
        String key = ConstUtils.GET_MEDICINE_INFO_HISTORY + accountId;
        return Objects.requireNonNull(stringRedisTemplate.opsForList().range(key, 0, 9)).stream().map(Integer::parseInt).toList();
    }
}
