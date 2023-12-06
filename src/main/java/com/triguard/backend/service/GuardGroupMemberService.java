package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.GuardGroupMember;

import java.util.List;

public interface GuardGroupMemberService extends IService<GuardGroupMember> {

    List<Integer> getGroupIdList(Integer guardianId);

    String addMemberList(Integer groupId, Integer guardianId, List<Integer> wardIdList);

    List<GuardGroupMember> getMemberList(Integer groupId);

    void deleteByGroupId(Integer groupId);

}
