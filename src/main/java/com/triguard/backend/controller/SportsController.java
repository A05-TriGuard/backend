package com.triguard.backend.controller;

import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Exercise;
import com.triguard.backend.entity.dto.Steps;
import com.triguard.backend.entity.vo.request.Sports.ExerciseFilterVO;
import com.triguard.backend.entity.vo.response.Sports.CurrentExerciseVO;
import com.triguard.backend.entity.vo.response.Sports.ExerciseFilteredVO;
import com.triguard.backend.entity.vo.response.Sports.ExerciseInfoVO;
import com.triguard.backend.entity.vo.response.Sports.StepsInfoVO;
import com.triguard.backend.service.ExerciseService;
import com.triguard.backend.service.StepsService;
import com.triguard.backend.utils.ConstUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 运动相关接口
 */
@Validated
@RestController
@RequestMapping("/api/sports")
@Tag(name = "运动相关接口")
public class SportsController {

    @Resource
    StepsService stepsService;

    @Resource
    ExerciseService exerciseService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取步数
     *
     * @param accountId 用户id
     * @param date      日期
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 今日步数
     */
    @GetMapping("/steps")
    @Operation(summary = "获取步数")
    public RestBean<List<StepsInfoVO>> getTodaySteps(@RequestParam(required = false) Integer accountId,
                                                     @RequestParam(required = false) String date,
                                                     @RequestParam(required = false) String startDate,
                                                     @RequestParam(required = false) String endDate,
                                                     HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<Steps> steps;
        if (date != null) {
            steps = stepsService.getStepsByDate(accountId, date);
        } else if (startDate != null && endDate != null) {
            steps = stepsService.getStepsByDateRange(accountId, startDate, endDate);
        } else {
            steps = stepsService.getTodaySteps(accountId);
        }
        List<StepsInfoVO> stepsInfoVOS = steps.stream().map(step -> {
            StepsInfoVO stepsInfoVO = new StepsInfoVO();
            BeanUtils.copyProperties(step, stepsInfoVO);
            return stepsInfoVO;
        }).toList();
        return RestBean.success(stepsInfoVOS);
    }

    /**
     * 更新今日步数
     *
     * @param steps     步数
     * @param accountId 用户id
     * @return 是否成功
     */
    @PostMapping("/steps/update")
    @Operation(summary = "更新今日步数")
    public RestBean<Void> updateTodaySteps(@RequestParam Integer steps,
                                           @RequestParam(required = false) Integer accountId,
                                           HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        return stepsService.updateTodaySteps(accountId, steps) ? RestBean.success() : RestBean.failure(400, "更新失败");
    }

    /**
     * 获取运动记录
     *
     * @param accountId 用户id
     * @param date      日期
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 运动记录
     */
    @GetMapping("/exercise/list")
    @Operation(summary = "获取运动记录")
    public RestBean<List<ExerciseInfoVO>> getExerciseList(@RequestParam(required = false) Integer accountId,
                                                          @RequestParam(required = false) String date,
                                                          @RequestParam(required = false) String startDate,
                                                          @RequestParam(required = false) String endDate,
                                                          HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<Exercise> exercises;
        if (date != null) {
            exercises = exerciseService.getExerciseByDate(accountId, date);
        } else if (startDate != null && endDate != null) {
            exercises = exerciseService.getExerciseByDateRange(accountId, startDate, endDate);
        } else {
            exercises = exerciseService.getExerciseList(accountId);
        }
        List<ExerciseInfoVO> exerciseInfoVOS = exercises.stream().map(exercise -> {
            ExerciseInfoVO exerciseInfoVO = new ExerciseInfoVO();
            BeanUtils.copyProperties(exercise, exerciseInfoVO);
            return exerciseInfoVO;
        }).toList();
        return RestBean.success(exerciseInfoVOS);
    }

    /**
     * 筛选运动记录
     *
     * @param vo 筛选条件
     * @return 筛选结果
     */
    @PostMapping("/exercise/filter")
    @Operation(summary = "筛选运动记录")
    public RestBean<ExerciseFilteredVO> filterExercise(@RequestBody @Valid ExerciseFilterVO vo,
                                                       HttpServletRequest request) {
        if (vo.getAccountId() == null) {
            vo.setAccountId((Integer) request.getAttribute(ConstUtils.ATTR_USER_ID));
        }
        List<Steps> steps = stepsService.getStepsByDateRange(vo.getAccountId(), vo.getStartDate(), vo.getEndDate());
        List<StepsInfoVO> stepsInfoVOS = steps.stream().map(step -> {
            StepsInfoVO stepsInfoVO = new StepsInfoVO();
            BeanUtils.copyProperties(step, stepsInfoVO);
            return stepsInfoVO;
        }).toList();
        List<Exercise> exercises = exerciseService.getExerciseByDateRange(vo.getAccountId(), vo.getStartDate(), vo.getEndDate());
        List<ExerciseInfoVO> exerciseInfoVOS = exercises.stream().map(exercise -> {
            ExerciseInfoVO exerciseInfoVO = new ExerciseInfoVO();
            BeanUtils.copyProperties(exercise, exerciseInfoVO);
            return exerciseInfoVO;
        }).toList();
        List<ExerciseInfoVO> filteredExerciseInfoVOS = exerciseService.filterExercise(exerciseInfoVOS, vo);
        return RestBean.success(new ExerciseFilteredVO(stepsInfoVOS, filteredExerciseInfoVOS, vo.getStartDate(), vo.getEndDate()));
    }

    /**q
     * 开始运动
     *
     * @param time      开始时间
     * @param type      运动类型
     * @param accountId 用户id
     * @return 是否成功
     */
    @PostMapping("/exercise/start")
    @Operation(summary = "开始运动")
    public RestBean<Void> startExercise(@RequestParam String time,
                                        @RequestParam Integer type,
                                        @RequestParam(required = false) Integer accountId,
                                        HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        Exercise exercise = new Exercise();
        exercise.setAccountId(accountId);
        exercise.setStartTime(time);
        exercise.setType(type);
        if (exerciseService.save(exercise)) {
            stringRedisTemplate.opsForValue().set(ConstUtils.REDIS_KEY_EXERCISE + accountId, time);
            return RestBean.success();
        } else {
            return RestBean.failure(400, "开始失败");
        }
    }

    /**
     * 暂停运动
     *
     * @param time      暂停时间
     * @param accountId 用户id
     * @return 是否成功
     */
    @PostMapping("/exercise/pause")
    @Operation(summary = "暂停运动")
    public RestBean<Void> pauseExercise(@RequestParam(required = false) Integer accountId,
                                        @RequestParam String time,
                                        HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        String startTime = stringRedisTemplate.opsForValue().get(ConstUtils.REDIS_KEY_EXERCISE + accountId);
        if (startTime == null) {
            return RestBean.failure(400, "未开始运动");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Exercise currentExercise = exerciseService.getCurrentExercise(accountId);
        try {
            currentExercise.setDuration(currentExercise.getDuration() + (int) ((simpleDateFormat.parse(time).getTime() - simpleDateFormat.parse(startTime).getTime()) / 60000));
        } catch (Exception e) {
            return RestBean.failure(500, "服务器错误");
        }
        if (exerciseService.updateById(currentExercise)) {
            stringRedisTemplate.delete(ConstUtils.REDIS_KEY_EXERCISE + accountId);
            return RestBean.success();
        } else {
            return RestBean.failure(400, "暂停失败");
        }
    }

    /**
     * 继续运动
     *
     * @param time      继续时间
     * @param accountId 用户id
     * @return 是否成功
     */
    @PostMapping("/exercise/continue")
    @Operation(summary = "继续运动")
    public RestBean<Void> continueExercise(@RequestParam(required = false) Integer accountId,
                                           @RequestParam String time,
                                           HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        Exercise currentExercise = exerciseService.getCurrentExercise(accountId);
        if (currentExercise.getEndTime() != null) {
            return RestBean.failure(400, "未开始运动");
        }
        if (stringRedisTemplate.opsForValue().get(ConstUtils.REDIS_KEY_EXERCISE + accountId) != null) {
            return RestBean.failure(400, "正在运动中");
        }
        stringRedisTemplate.opsForValue().set(ConstUtils.REDIS_KEY_EXERCISE + accountId, time);
        return RestBean.success();
    }

    /**
     * 结束运动
     *
     * @param time      结束时间
     * @param accountId 用户id
     * @return 是否成功
     */
    @PostMapping("/exercise/end")
    @Operation(summary = "结束运动")
    public RestBean<Void> endExercise(@RequestParam(required = false) Integer accountId,
                                      @RequestParam String time,
                                      @RequestParam(required = false) Integer feelings,
                                      @RequestParam(required = false) String remark,
                                      HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        String startTime = stringRedisTemplate.opsForValue().get(ConstUtils.REDIS_KEY_EXERCISE + accountId);
        if (startTime == null) {
            return RestBean.failure(400, "未开始运动");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Exercise currentExercise = exerciseService.getCurrentExercise(accountId);
        try {
            currentExercise.setDuration(currentExercise.getDuration() + (int) ((simpleDateFormat.parse(time).getTime() - simpleDateFormat.parse(startTime).getTime()) / 60000));
        } catch (Exception e) {
            return RestBean.failure(500, "服务器错误");
        }
        currentExercise.setEndTime(time);
        currentExercise.setFeelings(feelings);
        currentExercise.setRemark(remark);
        if (exerciseService.updateById(currentExercise)) {
            stringRedisTemplate.delete(ConstUtils.REDIS_KEY_EXERCISE + accountId);
            return RestBean.success();
        } else {
            return RestBean.failure(400, "结束失败");
        }
    }

    /**
     * 获取当前运动
     *
     * @param accountId 用户id
     * @return 当前运动
     */
    @GetMapping("/exercise/current")
    @Operation(summary = "获取当前运动")
    public RestBean<CurrentExerciseVO> getCurrentExercise(@RequestParam(required = false) Integer accountId,
                                                       HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        CurrentExerciseVO currentExerciseVO = new CurrentExerciseVO();
        Exercise currentExercise = exerciseService.getCurrentExercise(accountId);
        if (currentExercise == null || currentExercise.getEndTime() != null) {
            return RestBean.success(null);
        } else {
            currentExerciseVO.setIsExercising(true);
            currentExerciseVO.setStartTime(currentExercise.getStartTime());
            currentExerciseVO.setType(currentExercise.getType());
            currentExerciseVO.setIsPausing(stringRedisTemplate.opsForValue().get(ConstUtils.REDIS_KEY_EXERCISE + accountId) == null);
        }
        return RestBean.success(currentExerciseVO);
    }

    /**
     * 获取今日运动时长
     *
     * @param accountId 用户id
     * @return 今日运动时长
     */
    @GetMapping("/exercise/duration")
    @Operation(summary = "获取今日运动时长")
    public RestBean<Integer> getTodayExerciseDuration(@RequestParam(required = false) Integer accountId,
                                                       HttpServletRequest request) {
        if (accountId == null) {
            accountId = (Integer) request.getAttribute(ConstUtils.ATTR_USER_ID);
        }
        List<Exercise> exercises = exerciseService.getExerciseList(accountId);
        int duration = 0;
        for (Exercise exercise : exercises) {
            duration += exercise.getDuration();
        }
        return RestBean.success(duration);
    }

}
