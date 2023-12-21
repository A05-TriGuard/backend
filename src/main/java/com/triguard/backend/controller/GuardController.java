package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.dto.Guard;
import com.triguard.backend.entity.dto.GuardGroup;
import com.triguard.backend.entity.vo.response.Guard.*;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.service.GuardGroupMemberService;
import com.triguard.backend.service.GuardGroupService;
import com.triguard.backend.service.GuardService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

/**
 * 监护系统相关API
 */
@Validated
@RestController
@RequestMapping("/api")
@Tag(name = "监护人相关接口")
public class GuardController {

    @Resource
    GuardService guardService;

    @Resource
    AccountService accountService;

    @Resource
    GuardGroupService guardGroupService;

    @Resource
    GuardGroupMemberService guardGroupMemberService;

    /**
     * 获取监护人列表
     * @param request HTTP请求
     * @return 监护人列表
     */
    @GetMapping("/guardian/list")
    @Operation(summary = "获取监护人列表")
    public RestBean<List<GuardianInfoVO>> getGuardianList(HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Guard> guardList = guardService.getGuardianList(wardId);
        List<GuardianInfoVO> guardianInfoVOS = guardList.stream().map(guard -> {
            GuardianInfoVO guardianInfoVO = new GuardianInfoVO();
            guardianInfoVO.setAccountId(guard.getGuardianId());
            guardianInfoVO.setNickname(guard.getGuardianNickname());
            Account account = accountService.getById(guard.getGuardianId());
            guardianInfoVO.setUsername(account.getUsername());
            guardianInfoVO.setEmail(account.getEmail());
            guardianInfoVO.setImage(account.getProfile());
            return guardianInfoVO;
        }).toList();
        return RestBean.success(guardianInfoVOS);
    }

    /**
     * 删除监护人
     * @param guardianId 用户ID
     * @param request HTTP请求
     * @return 删除结果
     */
    @GetMapping("/guardian/delete")
    @Operation(summary = "删除监护人")
    public RestBean<String> deleteGuardian(@RequestParam Integer guardianId,
                                           HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.deleteGuardian(wardId, guardianId);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 设置监护人昵称
     * @param guardianId 用户ID
     * @param nickname 监护人昵称
     * @param request HTTP请求
     * @return 设置结果
     */
    @PostMapping("/guardian/set-nickname")
    @Operation(summary = "设置监护人昵称")
    public RestBean<String> setGuardianNickname(@RequestParam Integer guardianId,
                                                @RequestParam String nickname,
                                                HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.setGuardianNickname(wardId, guardianId, nickname);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 邀请监护人
     * @param email 监护人邮箱
     * @param request HTTP请求
     * @return 邀请结果
     */
    @PostMapping("/guardian/invite")
    @Operation(summary = "邀请监护人")
    public RestBean<String> inviteGuardian(@RequestParam String email,
                                           HttpServletRequest request) {
        Integer wardId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.inviteGuardian(wardId, email);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 获取被监护人列表
     * @param request HTTP请求
     * @return 被监护人列表
     */
    @GetMapping("/ward/list")
    @Operation(summary = "获取被监护人列表")
    public RestBean<WardVO> getWardList(HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<GuardGroup> guardGroupList = guardGroupService.getGuardGroupList(guardianId);
        List<GuardGroupInfoVO> guardGroupInfoVOS = guardGroupList.stream().map(guardGroup -> {
            GuardGroupInfoVO guardGroupInfoVO = new GuardGroupInfoVO();
            guardGroupInfoVO.setGroupId(guardGroup.getId());
            guardGroupInfoVO.setGroupName(guardGroup.getName());
            return guardGroupInfoVO;
        }).toList();
        List<Guard> guardList = guardService.getWardList(guardianId);
        List<WardInfoVO> wardInfoVOS = guardList.stream().map(guard -> {
            WardInfoVO wardInfoVO = new WardInfoVO();
            wardInfoVO.setAccountId(guard.getWardId());
            wardInfoVO.setNickname(guard.getWardNickname());
            Account account = accountService.getById(guard.getWardId());
            wardInfoVO.setUsername(account.getUsername());
            wardInfoVO.setEmail(account.getEmail());
            wardInfoVO.setImage(account.getProfile());
            return wardInfoVO;
        }).toList();
        WardVO wardVO = new WardVO(guardGroupInfoVOS, wardInfoVOS);
        return RestBean.success(wardVO);
    }

    /**
     * 获取被监护人邀请列表
     * @param request HTTP请求
     * @return 删除结果
     */
    @GetMapping("/ward/invitation/list")
    @Operation(summary = "获取被监护人邀请列表")
    public RestBean<List<InvitationInfoVO>> getWardInvitationList(HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Guard> guardList = guardService.getWardInvitationList(guardianId);
        List<InvitationInfoVO> invitationInfoVOS = guardList.stream().map(guard -> {
            InvitationInfoVO invitationInfoVO = new InvitationInfoVO();
            invitationInfoVO.setInvitationId(guard.getId());
            invitationInfoVO.setWardId(guard.getWardId());
            invitationInfoVO.setWardName(guard.getWardNickname());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            invitationInfoVO.setInvitationTime(simpleDateFormat.format(guard.getCreatedAt()));
            return invitationInfoVO;
        }).toList();
        return RestBean.success(invitationInfoVOS);
    }

    /**
     * 接受/拒绝被监护人邀请
     * @param invitationId 邀请ID
     * @param request HTTP请求
     * @return 接受结果
     */
    @PostMapping("/ward/invitation/accept")
    @Operation(summary = "接受/拒绝被监护人邀请")
    public RestBean<String> acceptWardInvitation(@RequestParam Integer invitationId,
                                                 @RequestParam Boolean isAccepted,
                                                 HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.acceptWardInvitation(guardianId, invitationId, isAccepted);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 获取被监护人今日活动信息
     * @param wardId 被监护人ID
     * @param request HTTP请求
     * @return 设置结果
     */
    @GetMapping("/ward/activity")
    @Operation(summary = "获取被监护人今日活动信息")
    public RestBean<WardActivityVO> getWardActivity(@RequestParam Integer wardId,
                                                    HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        WardActivityVO wardActivityVO = guardService.getWardActivity(guardianId, wardId);
        return RestBean.success(wardActivityVO);
    }

    /**
     * 删除被监护人
     * @param wardId 被监护人ID
     * @param request HTTP请求
     * @return 是否成功
     */
    @GetMapping("/ward/delete")
    @Operation(summary = "删除被监护人")
    public RestBean<String> deleteWard(@RequestParam Integer wardId,
                                       HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.deleteWard(guardianId, wardId);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        List<Integer> groupIdList = guardGroupMemberService.getGroupIdList(guardianId);
        try {
            for (Integer groupId : groupIdList) {
                guardGroupMemberService.deleteMember(groupId, wardId);
            }
        } catch (Exception e) {
            return RestBean.failure(400, "删除失败");
        }
        return RestBean.success();
    }

    /**
     * 设置被监护人昵称
     * @param wardId 被监护人ID
     * @param nickname 被监护人昵称
     * @param request HTTP请求
     * @return 设置结果
     */
    @PostMapping("/ward/set-nickname")
    @Operation(summary = "设置被监护人昵称")
    public RestBean<String> setWardNickname(@RequestParam Integer wardId,
                                            @RequestParam String nickname,
                                            HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        String message = guardService.setWardNickname(guardianId, wardId, nickname);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 创建监护组
     * @param groupName 监护组名称
     * @param wardIds wardIdList 被监护人ID列表
     * @param request HTTP请求
     */
    @PostMapping("/guard-group/create")
    @Operation(summary = "创建监护组")
    public RestBean<String> createGuardGroup(@RequestParam String groupName,
                                             @RequestParam String wardIds,
                                             HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Integer> wardIdList = Stream.of(wardIds.split(","))
                .map(Integer::parseInt)
                .toList();
        String message = guardGroupService.createGuardGroup(guardianId, groupName, wardIdList);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 获取监护组活动信息
     * @return 监护组活动信息
     */
    @GetMapping("/guard-group/activity")
    @Operation(summary = "获取监护组活动信息")
    public RestBean<GuardGroupActivityVO> getGuardGroupActivity(@RequestParam Integer groupId,
                                                                HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        GuardGroupActivityVO guardGroupActivityVO = guardGroupService.getGuardGroupActivity(groupId, guardianId);
        return RestBean.success(guardGroupActivityVO);
    }

    /**
     * 添加监护组成员
     * @param groupId 监护组ID
     * @param wardIds 被监护人ID列表
     * @return 添加结果
     */
    @PostMapping("/guard-group/member/add")
    @Operation(summary = "添加监护组成员")
    public RestBean<String> addGuardGroupMember(@RequestParam Integer groupId,
                                                @RequestParam String wardIds,
                                                HttpServletRequest request) {
        Integer guardianId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Integer> wardIdList = Stream.of(wardIds.split(","))
                .map(Integer::parseInt)
                .toList();
        String message = guardGroupService.addGuardGroupMember(groupId, guardianId, wardIdList);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 解散监护组
     * @return 解散结果
     */
    @GetMapping("/guard-group/disband")
    @Operation(summary = "解散监护组")
    public RestBean<String> disbandGuardGroup(@RequestParam Integer groupId) {
        String message = guardGroupService.deleteGuardGroup(groupId);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 删除监护组成员
     * @param groupId 监护组ID
     * @param wardId 被监护人ID
     * @return 删除结果
     */
    @GetMapping("/guard-group/member/delete")
    @Operation(summary = "删除监护组成员")
    public RestBean<String> deleteGuardGroupMember(@RequestParam Integer groupId,
                                                   @RequestParam Integer wardId) {
        String message = guardGroupService.deleteGuardGroupMember(groupId, wardId);
        if (message != null) {
            return RestBean.failure(400, message);
        }
        return RestBean.success();
    }

    /**
     * 更改群组名称
     * @param groupId 监护组ID
     * @param groupName 群组名称
     */
    @PostMapping("/guard-group/set-name")
    @Operation(summary = "更改群组名称")
    public RestBean<String> setGroupName(@RequestParam Integer groupId,
                                         @RequestParam String groupName) {
        GuardGroup guardGroup = guardGroupService.getById(groupId);
        guardGroup.setName(groupName);
        guardGroupService.updateById(guardGroup);
        return RestBean.success();
    }

}
