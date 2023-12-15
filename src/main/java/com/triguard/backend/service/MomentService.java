package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.dto.Moment;
import com.triguard.backend.entity.dto.MomentComment;
import com.triguard.backend.entity.vo.response.Moment.MomentCommentCreateVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MomentService extends IService<Moment> {
    List<Moment> getMomentListByTime(String classification, String category, Integer page, Integer size, String keyword);

    List<Moment> getMomentListByHot(String classification, String category, Integer page, Integer size, String keyword);

    List<Moment> getMomentListByAccount(Integer accountId, String classification, String category, Integer page, Integer size, String keyword);

    List<Moment> getMomentListByLike(Integer accountId, String classification, String category, Integer page, Integer size, String keyword);

    List<Moment> getMomentListByFavorite(Integer accountId, String classification, String category, Integer page, Integer size, String keyword);

    List<Moment> getMomentListByFollow(Integer accountId, String classification, String category, Integer page, Integer size, String keyword);

    Boolean isLike(Integer accountId, Integer momentId);

    Boolean isFavorite(Integer accountId, Integer momentId);

    Boolean isFollow(Integer accountId, Integer momentAccountId);

    Boolean likeMoment(Integer accountId, Integer momentId);

    Boolean unlikeMoment(Integer accountId, Integer momentId);

    Boolean favoriteMoment(Integer accountId, Integer momentId);

    Boolean unfavoriteMoment(Integer accountId, Integer momentId);

    Boolean followMomentAccount(Integer accountId, Integer momentAccountId);

    Boolean unfollowMomentAccount(Integer accountId, Integer momentAccountId);

    Boolean reportMoment(Integer accountId, Integer momentId, String reason);

    Moment createMoment(Integer accountId, String content, String classification, String category, List<MultipartFile> images, MultipartFile video);

    boolean deleteMoment(Integer accountId, Integer momentId);

    MomentComment commentMoment(Integer accountId, Integer momentId, String content, Integer quoteCommentId);

    MomentComment getCommentById(Integer commentId);

    MomentCommentCreateVO getCommentInfo(MomentComment momentComment);

    List<MomentComment> getCommentList(Integer momentId);
}
