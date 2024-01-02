package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.dto.Moment;
import com.triguard.backend.entity.dto.MomentComment;
import com.triguard.backend.entity.vo.response.Moment.MomentCommentCreateVO;
import com.triguard.backend.mapper.MomentMapper;
import com.triguard.backend.service.*;
import com.triguard.backend.utils.ConstUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MomentServiceImpl extends ServiceImpl<MomentMapper, Moment> implements MomentService {

    @Resource
    MomentLikeService momentLikeService;

    @Resource
    MomentFavoriteService momentFavoriteService;

    @Resource
    FollowService followService;

    @Resource
    MomentReportService momentReportService;

    @Resource
    FileService fileService;

    @Resource
    AccountService accountService;

    @Resource
    MomentCommentService momentCommentService;

    /**
     * 根据三高分类、话题分类、时间排序获取动态列表
     *
     * @param classification 三高分类
     * @param category       话题分类
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    public List<Moment> getMomentListByTime(String classification, String category, Integer page, Integer size, String keyword) {
        Page<Moment> momentPage = new Page<>(page, size);
        QueryChainWrapper<Moment> queryWrapper = this.getBaseQueryWrapper(classification, category, keyword);
        queryWrapper = queryWrapper.orderByDesc("created_at");
        return queryWrapper.page(momentPage).getRecords();
    }

    /**
     * 根据三高分类、话题分类、热度排序获取动态列表
     *
     * @param classification 三高分类
     * @param category       话题分类
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    public List<Moment> getMomentListByHot(String classification, String category, Integer page, Integer size, String keyword) {
        Page<Moment> momentPage = new Page<>(page, size);
        QueryChainWrapper<Moment> queryWrapper = this.getBaseQueryWrapper(classification, category, keyword);
        queryWrapper = queryWrapper.orderByDesc("comment_count").orderByDesc("like_count").orderByDesc("created_at");
        return queryWrapper.page(momentPage).getRecords();
    }

    /**
     * 根据用户id、三高分类、话题分类、时间排序获取用户发布的动态列表
     *
     * @param accountId      用户id
     * @param classification 三高分类
     * @param category       话题分类
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    public List<Moment> getMomentListByAccount(Integer accountId, String classification, String category, Integer page, Integer size, String keyword) {
        Page<Moment> momentPage = new Page<>(page, size);
        QueryChainWrapper<Moment> queryWrapper = this.getBaseQueryWrapper(classification, category, keyword);
        queryWrapper = queryWrapper.eq("account_id", accountId).orderByDesc("created_at");
        return queryWrapper.page(momentPage).getRecords();
    }

    /**
     * 根据用户id、三高分类、话题分类、热度排序获取用户点赞的动态列表
     *
     * @param accountId      用户id
     * @param classification 三高分类
     * @param category       话题分类
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    public List<Moment> getMomentListByLike(Integer accountId, String classification, String category, Integer page, Integer size, String keyword) {
        Page<Moment> momentPage = new Page<>(page, size);
        QueryChainWrapper<Moment> queryWrapper = this.getBaseQueryWrapper(classification, category, keyword);
        queryWrapper = queryWrapper.inSql("id", "select moment_id from db_moment_like where account_id = " + accountId)
                .orderByDesc("created_at");
        return queryWrapper.page(momentPage).getRecords();
    }

    /**
     * 根据用户id、三高分类、话题分类、热度排序获取用户收藏的动态列表
     *
     * @param accountId      用户id
     * @param classification 三高分类
     * @param category       话题分类
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    public List<Moment> getMomentListByFavorite(Integer accountId, String classification, String category, Integer page, Integer size, String keyword) {
        Page<Moment> momentPage = new Page<>(page, size);
        QueryChainWrapper<Moment> queryWrapper = this.getBaseQueryWrapper(classification, category, keyword);
        queryWrapper = queryWrapper.inSql("id", "select moment_id from db_moment_favorite where account_id = " + accountId)
                .orderByDesc("created_at");
        return queryWrapper.page(momentPage).getRecords();
    }

    /**
     * 根据用户id、三高分类、话题分类、热度排序获取用户关注的动态列表
     *
     * @param accountId      用户id
     * @param classification 三高分类
     * @param category       话题分类
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    public List<Moment> getMomentListByFollow(Integer accountId, String classification, String category, Integer page, Integer size, String keyword) {
        Page<Moment> momentPage = new Page<>(page, size);
        QueryChainWrapper<Moment> queryWrapper = this.getBaseQueryWrapper(classification, category, keyword);
        queryWrapper = queryWrapper.inSql("account_id", "select follow_id from db_follow where account_id = " + accountId)
                .orderByDesc("created_at");
        return queryWrapper.page(momentPage).getRecords();
    }

    private QueryChainWrapper<Moment> getBaseQueryWrapper(String classification, String category, String keyword) {
        QueryChainWrapper<Moment> queryWrapper = this.query()
                .eq("class", classification)
                .eq("category", category);
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("content", keyword)
                    .or()
                    .like("username", keyword);
        }
        return queryWrapper;
    }

    /**
     * 判断用户是否点赞过某条动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否点赞过
     */
    public Boolean isLike(Integer accountId, Integer momentId) {
        return this.query()
                .eq("id", momentId)
                .inSql("id", "select moment_id from db_moment_like where account_id = " + accountId)
                .count() > 0;
    }

    /**
     * 判断用户是否收藏过某条动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否收藏过
     */
    public Boolean isFavorite(Integer accountId, Integer momentId) {
        return this.query()
                .eq("id", momentId)
                .inSql("id", "select moment_id from db_moment_favorite where account_id = " + accountId)
                .count() > 0;
    }

    /**
     * 判断用户是否关注过某条动态的发布者
     *
     * @param accountId 用户id
     * @param momentAccountId  动态id
     * @return 是否关注过
     */
    public Boolean isFollow(Integer accountId, Integer momentAccountId) {
        return this.query()
                .eq("account_id", momentAccountId)
                .inSql("account_id", "select follow_id from db_follow where account_id = " + accountId)
                .count() > 0;
    }

    /**
     * 点赞动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否点赞成功
     */
    public Boolean likeMoment(Integer accountId, Integer momentId) {
        Boolean result = momentLikeService.saveMomentLike(accountId, momentId);
        if (result) {
            Moment moment = this.getById(momentId);
            moment.setLikeCount(moment.getLikeCount() + 1);
            this.updateById(moment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消点赞动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否取消点赞成功
     */
    public Boolean unlikeMoment(Integer accountId, Integer momentId) {
        Boolean result = momentLikeService.removeMomentLike(accountId, momentId);
        if (result) {
            Moment moment = this.getById(momentId);
            moment.setLikeCount(moment.getLikeCount() - 1);
            this.updateById(moment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 收藏动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否收藏成功
     */
    public Boolean favoriteMoment(Integer accountId, Integer momentId) {
        Boolean result = momentFavoriteService.saveMomentFavorite(accountId, momentId);
        if (result) {
            Moment moment = this.getById(momentId);
            moment.setFavoriteCount(moment.getFavoriteCount() + 1);
            this.updateById(moment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消收藏动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否取消收藏成功
     */
    public Boolean unfavoriteMoment(Integer accountId, Integer momentId) {
        Boolean result = momentFavoriteService.removeMomentFavorite(accountId, momentId);
        if (result) {
            Moment moment = this.getById(momentId);
            moment.setFavoriteCount(moment.getFavoriteCount() - 1);
            this.updateById(moment);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 关注动态发布者
     *
     * @param accountId 用户id
     * @param momentAccountId  动态发布者id
     * @return 是否关注成功
     */
    public Boolean followMomentAccount(Integer accountId, Integer momentAccountId) {
        return followService.saveFollow(accountId, momentAccountId);
    }

    /**
     * 取消关注动态发布者
     *
     * @param accountId 用户id
     * @param momentAccountId  动态发布者id
     * @return 是否取消关注成功
     */
    public Boolean unfollowMomentAccount(Integer accountId, Integer momentAccountId) {
        return followService.removeFollow(accountId, momentAccountId);
    }

    /**
     * 举报动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @param reason 举报理由
     * @return 是否举报成功
     */
    public Boolean reportMoment(Integer accountId, Integer momentId, String reason) {
        return momentReportService.saveMomentReport(accountId, momentId, reason);
    }

    /**
     * 创建动态
     *
     * @param accountId 用户id
     * @param content   动态内容
     * @param classification 三高分类
     * @param category 话题分类
     * @param images 图片
     * @param video 视频
     * @return 是否创建成功
     */
    public Moment createMoment(Integer accountId, String content, String classification, String category, List<MultipartFile> images, MultipartFile video) {
        Moment moment = new Moment();
        moment.setContent(content);
        moment.setClassification(classification);
        moment.setCategory(category);
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        moment.setDate(formatter.format(today));
        moment.setAccountId(accountId);
        Account account = accountService.getById(accountId);
        moment.setUsername(account.getUsername());
        moment.setProfile(account.getProfile() == null ? ConstUtils.DEFAULT_AVATAR : account.getProfile());
        if (images != null && !images.isEmpty()) {
            StringBuilder imageUrls = new StringBuilder();
            for (MultipartFile image : images) {
                String url = fileService.uploadMultipartFile(image);
                imageUrls.append(url).append(";");
            }
            moment.setImages(imageUrls.toString());
        } else {
            moment.setImages("");
        }
        if (video != null && !video.isEmpty()) {
            String url = fileService.uploadMultipartFile(video);
            moment.setVideo(url);
        } else {
            moment.setVideo("");
        }
        this.save(moment);
        return moment;
    }

    /**
     * 删除动态
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @return 是否删除成功
     */
    public boolean deleteMoment(Integer accountId, Integer momentId) {
        Moment moment = this.getById(momentId);
        if (moment == null) {
            return false;
        }
        if (!moment.getAccountId().equals(accountId)) {
            return false;
        }
        return this.removeById(momentId);
    }

    /**
     * 发布评论
     *
     * @param accountId 用户id
     * @param momentId  动态id
     * @param content 评论内容
     * @return 评论id
     */
    public MomentComment commentMoment(Integer accountId, Integer momentId, String content, Integer quoteCommentId) {
        Moment moment = this.getById(momentId);
        if (moment == null) {
            return null;
        }
        MomentComment momentComment = new MomentComment();
        momentComment.setAccountId(accountId);
        momentComment.setMomentId(momentId);
        momentComment.setContent(content);
        momentComment.setQuoteCommentId(quoteCommentId);
        momentComment.setCreateTime(new Date());
        boolean result = momentCommentService.save(momentComment);
        if (result) {
            moment.setCommentCount(moment.getCommentCount() + 1);
            this.updateById(moment);
        }
        return momentComment;
    }

    /**
     * 根据评论id获取评论
     *
     * @param commentId 评论id
     * @return 评论
     */
    public MomentComment getCommentById(Integer commentId) {
        return momentCommentService.getById(commentId);
    }

    /**
     * 获取评论信息
     *
     * @param momentComment 评论
     * @return 评论信息
     */
    public MomentCommentCreateVO getCommentInfo(MomentComment momentComment) {
        MomentCommentCreateVO momentCommentCreateVO = new MomentCommentCreateVO();
        momentCommentCreateVO.setId(momentComment.getId());
        momentCommentCreateVO.setMomentId(momentComment.getMomentId());
        Account account = accountService.getById(momentComment.getAccountId());
        momentCommentCreateVO.setAccountId(account.getId());
        momentCommentCreateVO.setUsername(account.getUsername());
        momentCommentCreateVO.setProfile(account.getProfile());
        momentCommentCreateVO.setContent(momentComment.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        momentCommentCreateVO.setCreateTime(simpleDateFormat.format(momentComment.getCreateTime()));
        if (momentComment.getQuoteCommentId() != null) {
            MomentComment quoteComment = this.getCommentById(momentComment.getQuoteCommentId());
            momentCommentCreateVO.setQuoteCommentId(quoteComment.getId());
            momentCommentCreateVO.setQuoteCommentAccountId(quoteComment.getAccountId());
            Account quoteAccount = accountService.getById(quoteComment.getAccountId());
            momentCommentCreateVO.setQuoteCommentUsername(quoteAccount.getUsername());
            momentCommentCreateVO.setQuoteCommentProfile(quoteAccount.getProfile());
            momentCommentCreateVO.setQuoteCommentContent(quoteComment.getContent());
        }
        return momentCommentCreateVO;
    }

    /**
     * 根据动态id获取评论列表
     *
     * @param momentId 动态id
     * @return 评论列表
     */
    public List<MomentComment> getCommentList(Integer momentId) {
        return momentCommentService.query()
                .eq("moment_id", momentId)
                .orderByDesc("create_time")
                .list();
    }

}
