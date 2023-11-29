package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.BloodSugar;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarCreateVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarFilterVO;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarUpdateVO;

import java.util.List;

public interface BloodSugarService extends IService<BloodSugar> {
    BloodSugar createBloodSugar(Integer accountId, BloodSugarCreateVO vo);

    String deleteBloodSugar(Integer id);

    String updateBloodSugar(BloodSugarUpdateVO vo);

    List<BloodSugar> getBloodSugar(Integer accountId, String date);

    List<BloodSugar> getBloodSugar(Integer accountId, String startDate, String endDate);

    List<BloodSugar> getBloodSugar(Integer accountId, BloodSugarFilterVO vo);
}
