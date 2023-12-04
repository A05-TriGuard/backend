package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Guard;

import java.util.List;

public interface GuardService extends IService<Guard> {
    List<Guard> getGuardianList(Integer wardId);

    String deleteGuardian(Integer wardId, Integer guardianId);

    String setGuardianNickname(Integer wardId, Integer guardianId, String nickname);

    String inviteGuardian(Integer wardId, String email);
}
