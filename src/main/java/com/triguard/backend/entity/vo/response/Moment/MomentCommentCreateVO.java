package com.triguard.backend.entity.vo.response.Moment;

import lombok.Data;

@Data
public class MomentCommentCreateVO {
    Integer id;
    Integer momentId;
    Integer accountId;
    String username;
    String profile;
    String content;
    String createTime;
    Integer quoteCommentId;
    Integer quoteCommentAccountId;
    String quoteCommentUsername;
    String quoteCommentProfile;
    String quoteCommentContent;
}
