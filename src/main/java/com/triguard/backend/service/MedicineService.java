package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Medicine;

import java.util.List;

public interface MedicineService extends IService<Medicine> {

    List<Medicine> searchMedicine(String keyword);
}
