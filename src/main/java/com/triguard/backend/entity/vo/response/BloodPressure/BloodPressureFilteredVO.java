package com.triguard.backend.entity.vo.response.BloodPressure;

import com.triguard.backend.entity.dto.BloodPressure;
import lombok.Data;

import java.util.List;

/**
 * 用于统计后血压数据和统计数据的返回
 */
@Data
public class BloodPressureFilteredVO {
    List<BloodPressure> bloodPressureList;
    List<CountedData> countedDataList;

    public BloodPressureFilteredVO(List<BloodPressure> bloodPressureList) {
        this.bloodPressureList = bloodPressureList;
        this.countedDataList = List.of(countData("sbp"), countData("dbp"), countData("heart_rate"));
    }

    private CountedData countData(String name) {
        CountedData countedData = new CountedData();
        countedData.setName(name);
        for (BloodPressure bloodPressure : bloodPressureList) {
            Integer data = switch (name) {
                case "sbp" -> bloodPressure.getSbp();
                case "dbp" -> bloodPressure.getDbp();
                case "heart_rate" -> bloodPressure.getHeartRate();
                default -> null;
            };
            if (data != null) {
                countedData.setCount(countedData.getCount() + 1);
            } else {
                continue;
            }
            if (countedData.getMin() == null || data < countedData.getMin()) {
                countedData.setMin(data);
            }
            if (countedData.getMax() == null || data > countedData.getMax()) {
                countedData.setMax(data);
            }
            countedData.setAvg(countedData.getAvg() + data);
            switch (name) {
                case "sbp" -> {
                    if (data < 90) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data < 120) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data < 140) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                }
                case "dbp" -> {
                    if (data < 60) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data < 80) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data < 90) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                }
                case "heart_rate" -> {
                    if (data < 60) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data < 100) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data < 120) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                }
            }
        }
        if (countedData.getCount() != 0) {
            countedData.setAvg(countedData.getAvg() / countedData.getCount());
        }
        return countedData;
    }

    @Data
    private static class CountedData{
        private String name;
        private Integer min;
        private Integer max;
        private Integer avg;
        private Integer high;
        private Integer medium;
        private Integer low;
        private Integer abnormal;
        private Integer count;

        public CountedData() {
            this.min = null;
            this.max = null;
            this.avg = 0;
            this.high = 0;
            this.medium = 0;
            this.low = 0;
            this.abnormal = 0;
            this.count = 0;
        }
    }
}
