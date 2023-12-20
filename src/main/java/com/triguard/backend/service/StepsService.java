package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Steps;
import com.triguard.backend.entity.vo.response.Sports.StepsInfoVO;

import java.util.List;

public interface StepsService extends IService<Steps> {
    List<Steps> getTodaySteps(Integer accountId);

    boolean updateTodaySteps(Integer accountId, Integer newSteps);

    List<Steps> getStepsByDate(Integer accountId, String date);

    List<Steps> getStepsByDateRange(Integer accountId, String startDate, String endDate);
}
