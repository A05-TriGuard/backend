package com.triguard.backend.entity.vo.response.BloodLipids;

import com.triguard.backend.entity.dto.BloodLipids;
import lombok.Data;

import java.util.List;

/**
 * 用于统计后血脂数据和统计数据的返回
 */
@Data
public class BloodLipidsFilteredVO {
    List<BloodLipids> bloodLipidsList;
    List<CountedData> countedDataList;

    public BloodLipidsFilteredVO(List<BloodLipids> bloodLipidsList) {
        this.bloodLipidsList = bloodLipidsList;
        this.countedDataList = List.of(countData("tc"), countData("tg"), countData("hdl"), countData("ldl"));
    }

    private CountedData countData(String name) {
        CountedData countedData = new CountedData();
        countedData.setName(name);
        for (BloodLipids bloodLipids : bloodLipidsList) {
            Float data = switch (name) {
                case "tc" -> bloodLipids.getTc();
                case "tg" -> bloodLipids.getTg();
                case "hdl" -> bloodLipids.getHdl();
                case "ldl" -> bloodLipids.getLdl();
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
                case "tc" -> {
                    if (data < 2.8) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data <= 5.2) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data <= 6.5) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                }
                case "tg" -> {
                    if (data < 0.5) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data <= 1.7)
                        countedData.setMedium(countedData.getMedium() + 1);
                    else if (data <= 2.2) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                }
                case "hdl" -> {
                    if (data < 1.0) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data <= 1.5) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data <= 3.0) {
                        countedData.setHigh(countedData.getHigh() + 1);
                    } else {
                        countedData.setAbnormal(countedData.getAbnormal() + 1);
                    }
                }
                case "ldl" -> {
                    if (data < 2.0) {
                        countedData.setLow(countedData.getLow() + 1);
                    } else if (data <= 3.3) {
                        countedData.setMedium(countedData.getMedium() + 1);
                    } else if (data <= 4.1) {
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
    private static class CountedData {
        String name;
        Integer count = 0;
        Float min = null;
        Float max = null;
        Float avg = 0f;
        Integer low = 0;
        Integer medium = 0;
        Integer high = 0;
        Integer abnormal = 0;
    }
}
