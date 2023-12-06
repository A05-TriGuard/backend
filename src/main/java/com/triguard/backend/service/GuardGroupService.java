package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.GuardGroup;
import com.triguard.backend.entity.vo.response.Guard.GuardGroupActivityVO;

import java.util.List;

public interface GuardGroupService extends IService<GuardGroup> {

    List<GuardGroup> getGuardGroupList(Integer guardianId);

    String createGuardGroup(Integer guardianId, String groupName, List<Integer> wardIdList);

    GuardGroupActivityVO getGuardGroupActivity(Integer groupId);

    String deleteGuardGroup(Integer groupId);

    String setMemberNickname(Integer groupId, Integer accountId, String nickname);

}
