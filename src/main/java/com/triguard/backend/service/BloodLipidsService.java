package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.BloodLipids;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsCreateVO;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsFilterVO;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsUpdateVO;

import java.util.List;

public interface BloodLipidsService extends IService<BloodLipids> {
    BloodLipids createBloodLipids(Integer accountId, BloodLipidsCreateVO vo);

    String deleteBloodLipids(Integer id);

    String updateBloodLipids(BloodLipidsUpdateVO vo);

    List<BloodLipids> getBloodLipids(Integer accountId, String date);

    List<BloodLipids> getBloodLipids(Integer accountId, String startDate, String endDate);

    List<BloodLipids> getBloodLipids(Integer accountId, BloodLipidsFilterVO vo);
}
