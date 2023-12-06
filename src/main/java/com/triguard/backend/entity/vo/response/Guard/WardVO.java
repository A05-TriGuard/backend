package com.triguard.backend.entity.vo.response.Guard;

import lombok.Data;

import java.util.List;

@Data
public class WardVO {
    List<GuardGroupInfoVO> groupList;
    List<WardInfoVO> wardList;

    public WardVO(List<GuardGroupInfoVO> groupList, List<WardInfoVO> wardList) {
        this.groupList = groupList;
        this.wardList = wardList;
    }
}
