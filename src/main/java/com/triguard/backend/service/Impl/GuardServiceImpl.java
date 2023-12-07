package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.*;
import com.triguard.backend.entity.vo.response.Guard.WardActivityInfoVO;
import com.triguard.backend.entity.vo.response.Guard.WardActivityVO;
import com.triguard.backend.mapper.GuardianMapper;
import com.triguard.backend.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 监护人相关服务实现类
 */
@Service
public class GuardServiceImpl extends ServiceImpl<GuardianMapper, Guard> implements GuardService {

    @Resource
    AccountService accountService;

    @Resource
    BloodPressureService bloodPressureService;

    @Resource
    BloodSugarService bloodSugarService;

    @Resource
    BloodLipidsService bloodLipidsService;

    /**
     * 获取监护人列表
     * @param wardId 被监护人ID
     * @return 监护人列表
     */
    public List<Guard> getGuardianList(Integer wardId) {
        return this.query()
                .eq("ward_id", wardId)
                .eq("is_accepted", true)
                .list();
    }

    /**
     * 删除监护人
     * @param wardId 被监护人ID
     * @param guardianId 监护人ID
     * @return 删除结果
     */
    public String deleteGuardian(Integer wardId, Integer guardianId) {
        Guard guardian = this.query()
                .eq("ward_id", wardId)
                .eq("guardian_id", guardianId)
                .one();
        if (guardian == null) {
            return "监护人不存在";
        }
        this.removeById(guardian.getId());
        return null;
    }

    /**
     * 设置监护人昵称
     * @param wardId 被监护人ID
     * @param guardianId 监护人ID
     * @param nickname 监护人昵称
     * @return 设置结果
     */
    public String setGuardianNickname(Integer wardId, Integer guardianId, String nickname) {
        Guard guardian = this.query()
                .eq("ward_id", wardId)
                .eq("guardian_id", guardianId)
                .one();
        if (guardian == null) {
            return "监护人不存在";
        }
        guardian.setGuardianNickname(nickname);
        this.updateById(guardian);
        return null;
    }

    /**
     * 邀请监护人
     * @param wardId 用户ID
     * @param email 监护人邮箱
     * @return 邀请结果
     */
    public String inviteGuardian(Integer wardId, String email) {
        Account account = accountService.query()
                .eq("email", email)
                .one();
        if (account == null) {
            return "邮箱错误，用户不存在";
        }
        Guard guard = this.query()
                .eq("ward_id", wardId)
                .eq("guardian_id", account.getId())
                .one();
        if (guard != null) {
            return "已经添加过该监护人";
        }
        guard = new Guard();
        guard.setWardId(wardId);
        guard.setWardNickname(accountService.getById(wardId).getUsername());
        guard.setGuardianId(account.getId());
        guard.setGuardianNickname(account.getUsername());
        guard.setIsAccepted(false);
        guard.setCreatedAt(new Date());
        return this.save(guard) ? null : "邀请失败";
    }

    /**
     * 获取被监护人列表
     * @param guardianId 监护人ID
     * @return 被监护人列表
     */
    public List<Guard> getWardList(Integer guardianId) {
        return this.query()
                .eq("guardian_id", guardianId)
                .eq("is_accepted", true)
                .list();
    }

    /**
     * 获取被监护人邀请列表
     * @param guardianId 监护人ID
     * @return 监护人列表
     */
    public List<Guard> getWardInvitationList(Integer guardianId) {
        return this.query()
                .eq("guardian_id", guardianId)
                .eq("is_accepted", false)
                .list();
    }

    /**
     * 获取被监护人活动信息
     * @param guardianId 监护人ID
     * @param wardId 被监护人ID
     * @return 被监护人活动信息
     */
    public WardActivityVO getWardActivity(Integer guardianId, Integer wardId) {
        Guard guard = this.query()
                .eq("guardian_id", guardianId)
                .eq("ward_id", wardId)
                .one();
        if (guard == null) {
            return null;
        }
        WardActivityVO wardActivityVO = new WardActivityVO();
        Account wardAccount = accountService.getById(wardId);
        wardActivityVO.setWardInfo(new WardActivityVO.WardInfo(wardAccount.getEmail(), wardAccount.getUsername(), guard.getWardNickname()));
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = simpleDateFormat.format(today);
        List<BloodPressure> bloodPressureList = bloodPressureService.getBloodPressure(wardId, todayString);
        List<BloodSugar> bloodSugarList = bloodSugarService.getBloodSugar(wardId, todayString);
        List<BloodLipids> bloodLipidsList = bloodLipidsService.getBloodLipids(wardId, todayString);
        List<WardActivityInfoVO> wardActivityInfoVOS = new ArrayList<>();
        for (BloodPressure bloodPressure : bloodPressureList) {
            wardActivityInfoVOS.add(new WardActivityInfoVO(bloodPressure.getTime(), 0));
        }
        for (BloodSugar bloodSugar : bloodSugarList) {
            wardActivityInfoVOS.add(new WardActivityInfoVO(bloodSugar.getTime(), 1));
        }
        for (BloodLipids bloodLipids : bloodLipidsList) {
            wardActivityInfoVOS.add(new WardActivityInfoVO(bloodLipids.getTime(), 2));
        }
        if (!wardActivityInfoVOS.isEmpty()) {
            wardActivityInfoVOS.sort(Comparator.comparing(WardActivityInfoVO::getTime));
        }
        wardActivityVO.setActivities(wardActivityInfoVOS);
        return wardActivityVO;
    }

    /**
     * 删除被监护人
     * @param guardianId 监护人ID
     * @param wardId 被监护人ID
     * @return 删除结果
     */
    public String deleteWard(Integer guardianId, Integer wardId) {
        Guard ward = this.query()
                .eq("guardian_id", guardianId)
                .eq("ward_id", wardId)
                .one();
        if (ward == null) {
            return "被监护人不存在";
        }
        this.removeById(ward.getId());
        return null;
    }

    /**
     * 接受邀请
     * @param guardianId 监护人ID
     * @param invitationId 被监护人ID
     * @return 接受结果
     */
    public String acceptWardInvitation(Integer guardianId, Integer invitationId, Boolean isAccepted) {
        Guard guard = this.query()
                .eq("guardian_id", guardianId)
                .eq("id", invitationId)
                .one();
        if (guard == null) {
            return "邀请不存在";
        }
        if (isAccepted) {
            guard.setIsAccepted(true);
            this.updateById(guard);
        } else {
            this.removeById(guard.getId());
        }
        return null;
    }
}
