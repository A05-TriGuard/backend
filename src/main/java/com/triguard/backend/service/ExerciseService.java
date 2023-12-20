package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Exercise;
import com.triguard.backend.entity.vo.request.Sports.ExerciseFilterVO;
import com.triguard.backend.entity.vo.response.Sports.ExerciseInfoVO;

import java.util.List;

public interface ExerciseService extends IService<Exercise> {
    List<Exercise> getExerciseByDate(Integer accountId, String date);

    List<Exercise> getExerciseByDateRange(Integer accountId, String startDate, String endDate);

    List<Exercise> getExerciseList(Integer accountId);

    Exercise getCurrentExercise(Integer accountId);

    List<ExerciseInfoVO> filterExercise(List<ExerciseInfoVO> exerciseInfoVOS, ExerciseFilterVO vo);
}
