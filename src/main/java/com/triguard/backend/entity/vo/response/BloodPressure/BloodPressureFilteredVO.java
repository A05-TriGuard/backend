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
        this.countedDataList = List.of(countSbp(), countDbp(), countHeartRate());
    }

    private CountedData countSbp() {
        CountedData countedData = new CountedData();
        countedData.setName("sbp");
        for (BloodPressure bloodPressure : bloodPressureList) {
            Integer sbp = bloodPressure.getSbp();
            if (sbp != null) {
                countedData.setCount(countedData.getCount() + 1);
            } else {
                continue;
            }
            if (countedData.getMin() == null || sbp < countedData.getMin()) {
                countedData.setMin(sbp);
            }
            if (countedData.getMax() == null || sbp > countedData.getMax()) {
                countedData.setMax(sbp);
            }
            countedData.setAvg(countedData.getAvg() + sbp);
            if (sbp < 90) {
                countedData.setLow(countedData.getLow() + 1);
            } else if (sbp < 120) {
                countedData.setMedium(countedData.getMedium() + 1);
            } else if (sbp < 140) {
                countedData.setHigh(countedData.getHigh() + 1);
            } else {
                countedData.setAbnormal(countedData.getAbnormal() + 1);
            }
        }
        if (countedData.getCount() != 0) {
            countedData.setAvg(countedData.getAvg() / countedData.getCount());
        }
        return countedData;
    }

    private CountedData countDbp() {
        CountedData countedData = new CountedData();
        countedData.setName("dbp");
        for (BloodPressure bloodPressure : bloodPressureList) {
            Integer dbp = bloodPressure.getDbp();
            if (dbp != null) {
                countedData.setCount(countedData.getCount() + 1);
            } else {
                continue;
            }
            if (countedData.getMin() == null || dbp < countedData.getMin()) {
                countedData.setMin(dbp);
            }
            if (countedData.getMax() == null || dbp > countedData.getMax()) {
                countedData.setMax(dbp);
            }
            countedData.setAvg(countedData.getAvg() + dbp);
            if (dbp < 60) {
                countedData.setLow(countedData.getLow() + 1);
            } else if (dbp < 80) {
                countedData.setMedium(countedData.getMedium() + 1);
            } else if (dbp < 90) {
                countedData.setHigh(countedData.getHigh() + 1);
            } else {
                countedData.setAbnormal(countedData.getAbnormal() + 1);
            }
        }
        if (countedData.getCount() != 0) {
            countedData.setAvg(countedData.getAvg() / countedData.getCount());
        }
        return countedData;
    }

    private CountedData countHeartRate() {
        CountedData countedData = new CountedData();
        countedData.setName("heart_rate");
        for (BloodPressure bloodPressure : bloodPressureList) {
            Integer heartRate = bloodPressure.getHeartRate();
            if (heartRate != null) {
                countedData.setCount(countedData.getCount() + 1);
            } else {
                continue;
            }
            if (countedData.getMin() == null || heartRate < countedData.getMin()) {
                countedData.setMin(heartRate);
            }
            if (countedData.getMax() == null || heartRate > countedData.getMax()) {
                countedData.setMax(heartRate);
            }
            countedData.setAvg(countedData.getAvg() + heartRate);
            if (heartRate < 60) {
                countedData.setLow(countedData.getLow() + 1);
            } else if (heartRate < 100) {
                countedData.setMedium(countedData.getMedium() + 1);
            } else if (heartRate < 120) {
                countedData.setHigh(countedData.getHigh() + 1);
            } else {
                countedData.setAbnormal(countedData.getAbnormal() + 1);
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
