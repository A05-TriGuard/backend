package com.triguard.backend.entity.vo.response.Guard;

import lombok.Data;

import java.util.List;

@Data
public class GuardGroupActivityVO {
    List<WardActivityVO.WardInfo> wardInfos;
    List<GroupMemberActivityVO> activities;
}
