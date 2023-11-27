package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.MedicineFavorites;

import java.util.List;

public interface MedicineFavoritesService extends IService<MedicineFavorites> {

    MedicineFavorites addMedicineFavorites(Integer accountId, Integer medicineId);

    List<MedicineFavorites> getMedicineFavorites(Integer accountId);

    MedicineFavorites getMedicineFavorites(Integer accountId, Integer medicineId);

    boolean deleteMedicineFavorites(Integer accountId, Integer medicineId);
}
