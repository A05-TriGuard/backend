package com.triguard.backend.entity.vo.response.Sports;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExerciseFilteredVO {
    List<StepsInfoVO> stepsList;
    List<ExerciseInfoVO> exerciseList;
    List<CountedData> countedDataList;

    public ExerciseFilteredVO(List<StepsInfoVO> stepsList, List<ExerciseInfoVO> exerciseList, String startDate, String endDate) {
        this.stepsList = stepsList;
        this.exerciseList = exerciseList;
        this.countedDataList = new ArrayList<>();
        countData(startDate, endDate);
    }

    private void countData(String startDate, String endDate) {
        CountedData countedStepsData = new CountedData();
        countedStepsData.setName("steps");
        for (StepsInfoVO stepsInfoVO : stepsList) {
            if (stepsInfoVO.getSteps() < 500) {
                countedStepsData.setLess(countedStepsData.getLess() + 1);
            } else if (stepsInfoVO.getSteps() < 1000) {
                countedStepsData.setFewer(countedStepsData.getFewer() + 1);
            } else if (stepsInfoVO.getSteps() < 3000) {
                countedStepsData.setMedium(countedStepsData.getMedium() + 1);
            } else {
                countedStepsData.setMore(countedStepsData.getMore() + 1);
            }
        }
        countedDataList.add(countedStepsData);
        CountedData countedExerciseData = new CountedData();
        countedExerciseData.setName("exercise");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            int duration = 0;
            for (ExerciseInfoVO exerciseInfoVO : exerciseList) {
                if (exerciseInfoVO.getStartTime().contains(date.toString())) {
                    duration += exerciseInfoVO.getDuration();
                }
            }
            if (duration < 30) {
                countedExerciseData.setLess(countedExerciseData.getLess() + 1);
            } else if (duration < 60) {
                countedExerciseData.setFewer(countedExerciseData.getFewer() + 1);
            } else if (duration < 120) {
                countedExerciseData.setMedium(countedExerciseData.getMedium() + 1);
            } else {
                countedExerciseData.setMore(countedExerciseData.getMore() + 1);
            }
        }
        countedDataList.add(countedExerciseData);
    }

    @Data
    public static class CountedData {
        String name;
        Integer less = 0;
        Integer fewer = 0;
        Integer medium = 0;
        Integer more = 0;
    }
}
