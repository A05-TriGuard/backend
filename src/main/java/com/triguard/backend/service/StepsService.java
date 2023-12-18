package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Steps;

public interface StepsService extends IService<Steps> {
    Integer getTodaySteps(Integer accountId);

    boolean updateTodaySteps(Integer accountId, Integer newSteps);
}
