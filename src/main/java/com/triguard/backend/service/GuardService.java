package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Guard;
import com.triguard.backend.entity.vo.response.Guard.WardActivityInfoVO;
import com.triguard.backend.entity.vo.response.Guard.WardActivityVO;

import java.util.List;

public interface GuardService extends IService<Guard> {
    List<Guard> getGuardianList(Integer wardId);

    String deleteGuardian(Integer wardId, Integer guardianId);

    String setGuardianNickname(Integer wardId, Integer guardianId, String nickname);

    String inviteGuardian(Integer wardId, String email);

    List<Guard> getWardList(Integer guardianId);

    List<Guard> getWardInvitationList(Integer guardianId);

    WardActivityVO getWardActivity(Integer guardianId, Integer wardId);

    String deleteWard(Integer guardianId, Integer wardId);

    String acceptWardInvitation(Integer guardianId, Integer invitationId, Boolean isAccepted);
}
