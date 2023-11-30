package com.triguard.backend.entity.vo.response.BloodSugar;

import com.triguard.backend.entity.dto.BloodSugar;
import lombok.Data;

import java.util.List;

/**
 * 用于统计后血糖数据和统计数据的返回
 */
@Data
public class BloodSugarFilteredVO {
    List<BloodSugar> bloodSugarList;
    List<CountedData> countedDataList;

    public BloodSugarFilteredVO(List<BloodSugar> bloodSugarList) {
        this.bloodSugarList = bloodSugarList;
        this.countedDataList = List.of(countData("bs"));
    }

    private CountedData countData(String name) {
        CountedData countedData = new CountedData();
        countedData.setName(name);
        for (BloodSugar bloodSugar : bloodSugarList) {
            // TODO: 与血压统计数据的代码重复，可以考虑抽象出来
            Float data = switch (name) {
                case "bs" -> bloodSugar.getBs();
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
            switch (bloodSugar.getMeal()) {
                case 0:
                    if (data < 3.9) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data <= 5.6) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data <= 7.0) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                    break;
                case 1, 2:
                    if (data < 4.4) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data <= 7.9) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data <= 11.0) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                    break;
            }
        }
        countedData.setAvg(countedData.getAvg() / countedData.getCount());
        return countedData;
    }

    @Data
    private static class CountedData {
        private String name;
        private Float min;
        private Float max;
        private Float avg;
        private Integer high;
        private Integer medium;
        private Integer low;
        private Integer abnormal;
        private Integer count;

        public CountedData() {
            this.name = null;
            this.min = null;
            this.max = null;
            this.avg = 0.0f;
            this.high = 0;
            this.medium = 0;
            this.low = 0;
            this.abnormal = 0;
            this.count = 0;
        }
    }
}
