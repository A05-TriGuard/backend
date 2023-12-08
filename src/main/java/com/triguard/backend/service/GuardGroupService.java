package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.GuardGroup;
import com.triguard.backend.entity.vo.response.Guard.GuardGroupActivityVO;

import java.util.List;

public interface GuardGroupService extends IService<GuardGroup> {

    List<GuardGroup> getGuardGroupList(Integer guardianId);

    String createGuardGroup(Integer guardianId, String groupName, List<Integer> wardIdList);

    String addGuardGroupMember(Integer groupId, Integer guardianId, Integer wardId);

    GuardGroupActivityVO getGuardGroupActivity(Integer groupId, Integer guardianId);

    String deleteGuardGroupMember(Integer groupId, Integer wardId);

    String deleteGuardGroup(Integer groupId);

}
