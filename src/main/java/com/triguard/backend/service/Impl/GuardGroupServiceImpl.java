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
        this.save(guardGroup);
        return guardGroupMemberService.addMemberList(guardGroup.getId(), guardianId , wardIdList);
    }

    /**
     * 获取监护组活动
     * @param groupId 监护组ID
     * @return 监护组活动
     */
    public GuardGroupActivityVO getGuardGroupActivity(Integer groupId) {
        GuardGroupActivityVO guardGroupActivityVO = new GuardGroupActivityVO();
        List<GuardGroupMember> memberList = guardGroupMemberService.getMemberList(groupId);
        List<WardActivityVO.WardInfo> wardInfoList = memberList.stream()
                .filter(member -> member.getRole().equals("ward"))
                .map(member -> {
                    Account ward = accountService.getById(member.getAccountId());
                    return new WardActivityVO.WardInfo(ward.getEmail(), ward.getUsername(), member.getNickname());
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
            List<BloodPressure> bloodPressureList = bloodPressureService.getBloodPressure(member.getAccountId(), todayString);
            List<BloodSugar> bloodSugarList = bloodSugarService.getBloodSugar(member.getAccountId(), todayString);
            List<BloodLipids> bloodLipidsList = bloodLipidsService.getBloodLipids(member.getAccountId(), todayString);
            for (BloodPressure bloodPressure : bloodPressureList) {
                groupMemberActivityVOS.add(new GroupMemberActivityVO(bloodPressure.getTime(), member.getNickname(), 0));
            }
            for (BloodSugar bloodSugar : bloodSugarList) {
                groupMemberActivityVOS.add(new GroupMemberActivityVO(bloodSugar.getTime(), member.getNickname(), 1));
            }
            for (BloodLipids bloodLipids : bloodLipidsList) {
                groupMemberActivityVOS.add(new GroupMemberActivityVO(bloodLipids.getTime(), member.getNickname(), 2));
            }
        }
        if (!groupMemberActivityVOS.isEmpty()) {
            groupMemberActivityVOS.sort(Comparator.comparing(GroupMemberActivityVO::getTime));
        }
        guardGroupActivityVO.setActivities(groupMemberActivityVOS);
        return guardGroupActivityVO;
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

    /**
     * 设置成员昵称
     * @param groupId 监护组ID
     * @param accountId 成员ID
     * @param nickname 昵称
     * @return 设置结果
     */
    public String setMemberNickname(Integer groupId, Integer accountId, String nickname) {
        GuardGroupMember member = guardGroupMemberService.query()
                .eq("group_id", groupId)
                .eq("account_id", accountId)
                .one();
        if (member == null) {
            return "成员不存在";
        }
        member.setNickname(nickname);
        guardGroupMemberService.updateById(member);
        return null;
    }

}
