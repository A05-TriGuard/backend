package com.triguard.backend.entity.vo.response.Guard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMemberActivityVO {
    String time;
    String nickname;
    Integer type;
}
