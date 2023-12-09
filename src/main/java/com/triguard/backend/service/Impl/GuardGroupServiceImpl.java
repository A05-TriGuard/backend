package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.*;
import com.triguard.backend.entity.vo.response.Guard.GroupMemberActivityVO;
import com.triguard.backend.entity.vo.response.Guard.GuardGroupActivityVO;
import com.triguard.backend.entity.vo.response.Guard.WardActivityInfoVO;
import com.triguard.backend.entity.vo.response.Guard.WardActivityVO;
import com.triguard.backend.mapper.GuardGroupMapper;
import com.triguard.backend.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 监护组相关服务实现类
 */
@Service
public class GuardGroupServiceImpl extends ServiceImpl<GuardGroupMapper, GuardGroup> implements GuardGroupService {

    @Resource
    GuardGroupMemberService guardGroupMemberService;

    @Resource
    AccountService accountService;

    @Resource
    GuardService guardService;

    @Resource
    BloodPressureService bloodPressureService;

    @Resource
    BloodSugarService bloodSugarService;

    @Resource
    BloodLipidsService bloodLipidsService;

    /**
     * 获取监护组列表
     * @param guardianId 监护人ID
     * @return 监护组列表
     */
    public List<GuardGroup> getGuardGroupList(Integer guardianId) {
        List<Integer> groupIdList = guardGroupMemberService.getGroupIdList(guardianId);
        if (groupIdList.isEmpty()) {
            return List.of();
        }
        return this.query()
                .in("id", groupIdList)
                .list();
    }

    /**
     * 创建监护组
     * @param guardianId 监护人ID
     * @param groupName 监护组名称
     * @param wardIdList 被监护人ID列表
     * @return 创建结果
     */
    public String createGuardGroup(Integer guardianId, String groupName, List<Integer> wardIdList) {
        GuardGroup guardGroup = new GuardGroup();
        guardGroup.setName(groupName);
        guardGroup.setCreatedBy(guardianId);
        this.save(guardGroup);
        return guardGroupMemberService.addMemberList(guardGroup.getId(), guardianId , wardIdList);
    }

    /**
     * 添加监护组成员
     * @param groupId 监护组ID
     * @param wardIdList 被监护人ID
     * @return 添加结果
     */
    public String addGuardGroupMember(Integer groupId, Integer guardianId, List<Integer> wardIdList) {
        GuardGroup guardGroup = this.getById(groupId);
        if (guardGroup == null) {
            return "监护组不存在";
        }
        return guardGroupMemberService.addWardMemberList(groupId, guardianId, wardIdList);
    }

    /**
     * 获取监护组活动
     * @param groupId 监护组ID
     * @return 监护组活动
     */
    public GuardGroupActivityVO getGuardGroupActivity(Integer groupId, Integer guardianId) {
        GuardGroupActivityVO guardGroupActivityVO = new GuardGroupActivityVO();
        List<GuardGroupMember> memberList = guardGroupMemberService.getMemberList(groupId);
        List<WardActivityVO.WardInfo> wardInfoList = memberList.stream()
                .filter(member -> member.getRole().equals("ward"))
                .map(member -> {
                    Account ward = accountService.getById(member.getAccountId());
                    Guard guard = guardService.query()
                            .eq("guardian_id", guardianId)
                            .eq("ward_id", ward.getId())
                            .one();
                    if (guard == null) {
                        return null;
                    }
                    return new WardActivityVO.WardInfo(ward.getId(), ward.getEmail(), ward.getUsername(), guard.getWardNickname(), null);
                })
                .toList();
        guardGroupActivityVO.setWardInfos(wardInfoList);
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = simpleDateFormat.format(today);
        List<GroupMemberActivityVO> groupMemberActivityVOS = new ArrayList<>();
        for (GuardGroupMember member : memberList) {
            if (member.getRole().equals("guardian")) {
                continue;
            }
            Guard guard = guardService.query()
                    .eq("guardian_id", guardianId)
                    .eq("ward_id", member.getAccountId())
                    .one();
            if (guard == null) {
                continue;
            }
            List<BloodPressure> bloodPressureList = bloodPressureService.getBloodPressure(member.getAccountId(), todayString);
            List<BloodSugar> bloodSugarList = bloodSugarService.getBloodSugar(member.getAccountId(), todayString);
            List<BloodLipids> bloodLipidsList = bloodLipidsService.getBloodLipids(member.getAccountId(), todayString);
            for (BloodPressure bloodPressure : bloodPressureList) {
                groupMemberActivityVOS.add(new GroupMemberActivityVO(bloodPressure.getTime(), guard.getWardNickname(), 0));
            }
            for (BloodSugar bloodSugar : bloodSugarList) {
                groupMemberActivityVOS.add(new GroupMemberActivityVO(bloodSugar.getTime(), guard.getWardNickname(), 1));
            }
            for (BloodLipids bloodLipids : bloodLipidsList) {
                groupMemberActivityVOS.add(new GroupMemberActivityVO(bloodLipids.getTime(), guard.getWardNickname(), 2));
            }
        }
        if (!groupMemberActivityVOS.isEmpty()) {
            groupMemberActivityVOS.sort(Comparator.comparing(GroupMemberActivityVO::getTime));
        }
        guardGroupActivityVO.setActivities(groupMemberActivityVOS);
        return guardGroupActivityVO;
    }

    /**
     * 删除监护组成员
     * @param groupId 监护组ID
     * @param wardId 被监护人ID
     * @return 删除结果
     */
    public String deleteGuardGroupMember(Integer groupId, Integer wardId) {
        GuardGroup guardGroup = this.getById(groupId);
        if (guardGroup == null) {
            return "监护组不存在";
        }
        return guardGroupMemberService.deleteMember(groupId, wardId);
    }

    /**
     * 删除监护组
     * @param groupId 监护组ID
     * @return 删除结果
     */
    public String deleteGuardGroup(Integer groupId) {
        try {
            this.removeById(groupId);
            guardGroupMemberService.deleteByGroupId(groupId);
        } catch (Exception e) {
            return "删除失败";
        }
        return null;
    }

}
