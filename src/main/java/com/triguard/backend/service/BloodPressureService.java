package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureCreateVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureFilterVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureUpdateVO;

import java.util.List;

public interface BloodPressureService extends IService<BloodPressure> {
    BloodPressure createBloodPressure(Integer accountId, BloodPressureCreateVO vo);

    String deleteBloodPressure(Integer id);

    String updateBloodPressure(BloodPressureUpdateVO vo);

    List<BloodPressure> getBloodPressure(Integer accountId, String date);

    List<BloodPressure> getBloodPressure(Integer accountId, String startDate, String endDate);

    List<BloodPressure> getBloodPressure(Integer accountId, BloodPressureFilterVO vo);
}
