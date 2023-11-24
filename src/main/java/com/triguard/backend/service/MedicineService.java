package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Medicine;

import java.util.List;

public interface MedicineService extends IService<Medicine> {

    List<Medicine> searchMedicine(String keyword);

    void saveSearchHistory(Integer accountId, String keyword);

    List<String> getSearchHistory(Integer accountId);

    void saveGetMedicineInfoHistory(Integer accountId, Integer medicineId);

    List<Integer> getGetMedicineInfoHistory(Integer accountId);
}
