package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.dto.Moment;
import com.triguard.backend.entity.dto.MomentComment;
import com.triguard.backend.entity.vo.response.Moment.MomentCommentCreateVO;
import com.triguard.backend.entity.vo.response.Moment.MomentInfoVO;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.service.MomentService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 动态相关接口
 */
@Validated
@RestController
@RequestMapping("/api/moment")
@Tag(name = "动态相关接口")
public class MomentController {

    @Resource
    MomentService momentService;

    @Resource
    AccountService accountService;

    /**
     * 根据三高分类、话题分类、排序方式获取动态列表
     *
     * @param classification 三高分类
     * @param category       话题分类
     * @param filter         排序方式
     * @param page           页码
     * @param size           每页大小
     * @param keyword        关键词
     * @return 动态列表
     */
    @GetMapping("/list")
    @Operation(summary = "根据三高分类、话题分类、排序方式获取动态列表")
    public RestBean<List<MomentInfoVO>> getMomentList(@RequestParam(name = "class") String classification,
                                            @RequestParam String category,
                                            @RequestParam String filter,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(required = false) String keyword,
                                            HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        List<Moment> momentList = switch (filter) {
            case "最受欢迎" -> momentService.getMomentListByHot(classification, category, page, size, keyword);
            case "我的帖子" ->
                    momentService.getMomentListByAccount(accountId, classification, category, page, size, keyword);
            case "我的点赞" ->
                    momentService.getMomentListByLike(accountId, classification, category, page, size, keyword);
            case "我的收藏" ->
                    momentService.getMomentListByFavorite(accountId, classification, category, page, size, keyword);
            case "我的关注" ->
                    momentService.getMomentListByFollow(accountId, classification, category, page, size, keyword);
            default -> momentService.getMomentListByTime(classification, category, page, size, keyword);
        };
        List<MomentInfoVO> momentInfoVOS = momentList.stream().map(moment -> {
            MomentInfoVO momentInfoVO = new MomentInfoVO();
            BeanUtils.copyProperties(moment, momentInfoVO);
            Account account = accountService.getById(moment.getAccountId());
            momentInfoVO.setUsername(account.getUsername());
            momentInfoVO.setProfile(account.getProfile());
            momentInfoVO.setIsLike(momentService.isLike(accountId, moment.getId()));
            momentInfoVO.setIsFavorite(momentService.isFavorite(accountId, moment.getId()));
            momentInfoVO.setIsFollow(momentService.isFollow(accountId, moment.getAccountId()));
            return momentInfoVO;
        }).toList();
        return RestBean.success(momentInfoVOS);
    }

    /**
     * 点赞动态
     *
     * @param momentId 动态id
     * @return 动态id
     */
    @PostMapping("/like")
    @Operation(summary = "点赞动态")
    public RestBean<String> likeMoment(@RequestParam Integer momentId,
                                       HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.likeMoment(accountId, momentId) ? RestBean.success() : RestBean.failure(400, "点赞失败");
    }

    /**
     * 取消点赞动态
     *
     * @param momentId 动态id
     * @return 动态id
     */
    @PostMapping("/unlike")
    @Operation(summary = "取消点赞动态")
    public RestBean<String> unlikeMoment(@RequestParam Integer momentId,
                                         HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.unlikeMoment(accountId, momentId) ? RestBean.success() : RestBean.failure(400, "取消点赞失败");
    }

    /**
     * 收藏动态
     *
     * @param momentId 动态id
     * @return 动态id
     */
    @PostMapping("/favorite")
    @Operation(summary = "收藏动态")
    public RestBean<String> favoriteMoment(@RequestParam Integer momentId,
                                           HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.favoriteMoment(accountId, momentId) ? RestBean.success() : RestBean.failure(400, "收藏失败");
    }

    /**
     * 取消收藏动态
     *
     * @param momentId 动态id
     * @return 动态id
     */
    @PostMapping("/unfavorite")
    @Operation(summary = "取消收藏动态")
    public RestBean<String> unfavoriteMoment(@RequestParam Integer momentId,
                                             HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.unfavoriteMoment(accountId, momentId) ? RestBean.success() : RestBean.failure(400, "取消收藏失败");
    }

    /**
     * 关注动态发布者
     *
     * @param momentAccountId 动态发布者id
     * @return 动态发布者id
     */
    @PostMapping("/follow")
    @Operation(summary = "关注动态发布者")
    public RestBean<String> followMomentAccount(@RequestParam Integer momentAccountId,
                                                HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.followMomentAccount(accountId, momentAccountId) ? RestBean.success() : RestBean.failure(400, "关注失败");
    }

    /**
     * 取消关注动态发布者
     *
     * @param momentAccountId 动态发布者id
     * @return 动态发布者id
     */
    @PostMapping("/unfollow")
    @Operation(summary = "取消关注动态发布者")
    public RestBean<String> unfollowMomentAccount(@RequestParam Integer momentAccountId,
                                                  HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.unfollowMomentAccount(accountId, momentAccountId) ? RestBean.success() : RestBean.failure(400, "取消关注失败");
    }

    /**
     * 举报动态
     *
     * @param momentId 动态id
     * @return 动态id
     */
    @PostMapping("/report")
    @Operation(summary = "举报动态")
    public RestBean<String> reportMoment(@RequestParam Integer momentId,
                                         @RequestParam String reason,
                                         HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.reportMoment(accountId, momentId, reason) ? RestBean.success() : RestBean.failure(400, "举报失败");
    }

    /**
     * 发布动态
     *
     * @param content        动态内容
     * @param classification 三高分类
     * @param category       话题分类
     * @param images         图片
     * @param video          视频
     * @return 动态id
     */
    @PostMapping("/create")
    @Operation(summary = "发布动态")
    public RestBean<Moment> createMoment(@RequestParam String content,
                                         @RequestParam(name = "class") String classification,
                                         @RequestParam String category,
                                         @RequestPart(required = false) List<MultipartFile> images,
                                         @RequestPart(required = false) MultipartFile video,
                                         HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return RestBean.success(momentService.createMoment(accountId, content, classification, category, images, video));
    }

    /**
     * 删除动态
     *
     * @param momentId 动态id
     * @return 动态id
     */
    @GetMapping("/delete")
    @Operation(summary = "删除动态")
    public RestBean<String> deleteMoment(@RequestParam Integer momentId,
                                         HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        return momentService.deleteMoment(accountId, momentId) ? RestBean.success() : RestBean.failure(400, "删除失败");
    }

    /**
     * 发布评论
     * @param momentId 动态id
     * @param content 评论内容
     * @return 评论id
     */
    @PostMapping("/comment")
    @Operation(summary = "发布评论")
    public RestBean<MomentCommentCreateVO> commentMoment(@RequestParam Integer momentId,
                                                         @RequestParam String content,
                                                         @RequestParam(required = false) Integer quoteCommentId,
                                                         HttpServletRequest request) {
        Integer accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        MomentComment momentComment = momentService.commentMoment(accountId, momentId, content, quoteCommentId);
        MomentCommentCreateVO momentCommentCreateVO = momentService.getCommentInfo(momentComment);
        return RestBean.success(momentCommentCreateVO);
    }

    /**
     * 获取评论列表
     * @param momentId 动态id
     * @return 评论信息
     */
    @GetMapping("/comment/list")
    @Operation(summary = "获取评论列表")
    public RestBean<List<MomentCommentCreateVO>> getCommentList(@RequestParam Integer momentId) {
        List<MomentComment> momentCommentList = momentService.getCommentList(momentId);
        List<MomentCommentCreateVO> momentCommentCreateVOList = momentCommentList.stream().map(momentComment -> momentService.getCommentInfo(momentComment)).toList();
        return RestBean.success(momentCommentCreateVOList);
    }
}
