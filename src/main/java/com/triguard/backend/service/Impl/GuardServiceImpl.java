package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.dto.Guard;
import com.triguard.backend.mapper.GuardianMapper;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.service.GuardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 监护人相关服务实现类
 */
@Service
public class GuardServiceImpl extends ServiceImpl<GuardianMapper, Guard> implements GuardService {

    @Resource
    AccountService accountService;

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
        this.removeById(guardian.getGuardianId());
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
        Guard guardian = this.query()
                .eq("ward_id", wardId)
                .eq("guardian_id", account.getId())
                .one();
        if (guardian != null) {
            return "已经添加过该监护人";
        }
        guardian = new Guard();
        guardian.setWardId(wardId);
        guardian.setWardNickname(accountService.getById(wardId).getUsername());
        guardian.setGuardianId(account.getId());
        guardian.setGuardianNickname(account.getUsername());
        guardian.setIsAccepted(false);
        return this.save(guardian) ? null : "邀请失败";
    }
}
