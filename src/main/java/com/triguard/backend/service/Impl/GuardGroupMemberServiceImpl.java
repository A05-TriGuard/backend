package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.dto.Guard;
import com.triguard.backend.entity.dto.GuardGroupMember;
import com.triguard.backend.mapper.GuardGroupMemberMapper;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.service.GuardGroupMemberService;
import com.triguard.backend.service.GuardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 监护组成员相关服务实现类
 */
@Service
public class GuardGroupMemberServiceImpl extends ServiceImpl<GuardGroupMemberMapper, GuardGroupMember> implements GuardGroupMemberService {

    @Resource
    GuardService guardService;

    @Resource
    AccountService accountService;

    /**
     * 获取监护组ID列表
     * @param guardianId 监护人ID
     * @return 监护组ID列表
     */
    public List<Integer> getGroupIdList(Integer guardianId) {
        return this.query()
                .eq("account_id", guardianId)
                .eq("role", "guardian")
                .list()
                .stream()
                .map(GuardGroupMember::getGroupId)
                .toList();
    }

    /**
     * 添加监护组成员
     * @param groupId 监护组ID
     * @param guardianId 监护人ID
     * @param wardIdList 被监护人ID列表
     * @return 添加结果
     */
    public String addMemberList(Integer groupId, Integer guardianId, List<Integer> wardIdList) {
        try {
            GuardGroupMember guardianMember = new GuardGroupMember();
            guardianMember.setGroupId(groupId);
            guardianMember.setAccountId(guardianId);
            guardianMember.setRole("guardian");
            guardianMember.setCreatedAt(new Date());
            Account guardian = accountService.getById(guardianId);
            guardianMember.setNickname(guardian.getUsername());
            this.save(guardianMember);
            for (Integer wardId : wardIdList) {
                GuardGroupMember wardMember = new GuardGroupMember();
                wardMember.setGroupId(groupId);
                wardMember.setAccountId(wardId);
                wardMember.setRole("ward");
                wardMember.setCreatedAt(new Date());
                Guard guard = guardService.query()
                        .eq("ward_id", wardId)
                        .eq("guardian_id", guardianId)
                        .one();
                wardMember.setNickname(guard.getWardNickname());
                this.save(wardMember);
            }
        } catch (Exception e) {
            return "添加失败";
        }
        return null;
    }

    /**
     * 获取所有监护组成员
     * @param groupId 监护组ID
     * @return 监护组成员列表
     */
    public List<GuardGroupMember> getMemberList(Integer groupId) {
        return this.query()
                .eq("group_id", groupId)
                .list();
    }

    /**
     * 删除监护组成员
     *
     * @param groupId 监护组ID
     */
    public void deleteByGroupId(Integer groupId) {
        List<GuardGroupMember> memberList = this.query()
                .eq("group_id", groupId)
                .list();
        for (GuardGroupMember member : memberList) {
            this.removeById(member.getId());
        }
    }
}
