package com.triguard.backend.entity.vo.response.Guard;

import lombok.Data;

import java.util.Date;

@Data
public class InvitationInfoVO {
    Integer invitationId;
    Integer wardId;
    String wardName;
    Date invitationTime;

    public InvitationInfoVO() {
        this.invitationId = null;
        this.wardId = null;
        this.wardName = null;
        this.invitationTime = null;
    }
}
