package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.MedicineFavorites;
import com.triguard.backend.mapper.MedicineFavoritesMapper;
import com.triguard.backend.service.MedicineFavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药品收藏相关服务
 */
@Service
public class MedicineFavoritesServiceImpl extends ServiceImpl<MedicineFavoritesMapper, MedicineFavorites> implements MedicineFavoritesService {

    /**
     * 添加药品收藏
     * @param accountId 用户id
     * @param medicineId 药品id
     * @return 更新后的药品收藏实体类
     */
    public MedicineFavorites addMedicineFavorites(Integer accountId, Integer medicineId) {
        MedicineFavorites medicineFavorites = new MedicineFavorites();
        medicineFavorites.setAccountId(accountId);
        medicineFavorites.setMedicineId(medicineId);
        return this.save(medicineFavorites) ? medicineFavorites : null;
    }

    /**
     * 根据用户id获取药品收藏
     * @param accountId 用户id
     * @return 药品收藏
     */
    public List<MedicineFavorites> getMedicineFavorites(Integer accountId) {
        return this.query()
                .eq("account_id", accountId)
                .orderByDesc("created_at")
                .list();
    }

    /**
     * 根据用户id和药品id获取药品收藏
     * @param accountId 用户id
     * @param medicineId 药品id
     * @return 药品收藏
     */
    public MedicineFavorites getMedicineFavorites(Integer accountId, Integer medicineId) {
        return this.query()
                .eq("account_id", accountId)
                .eq("medicine_id", medicineId)
                .one();
    }

    /**
     * 根据用户id和药品id删除药品收藏
     * @param accountId 用户id
     * @param medicineId 药品id
     * @return 是否删除成功
     */
    public boolean deleteMedicineFavorites(Integer accountId, Integer medicineId) {
        return this.removeById(this.getMedicineFavorites(accountId, medicineId).getId());
    }
}
