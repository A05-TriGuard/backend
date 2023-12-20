package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Exercise;
import com.triguard.backend.entity.vo.request.Sports.ExerciseFilterVO;
import com.triguard.backend.entity.vo.response.Sports.ExerciseInfoVO;
import com.triguard.backend.mapper.ExerciseMapper;
import com.triguard.backend.service.ExerciseService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExerciseServiceImpl extends ServiceImpl<ExerciseMapper, Exercise> implements ExerciseService {

    /**
     * 获取某日运动信息
     *
     * @param accountId 用户id
     * @param date      日期
     * @return 运动信息
     */
    public List<Exercise> getExerciseByDate(Integer accountId, String date) {
        return this.query()
                .eq("account_id", accountId)
                .likeRight("start_time", date)
                .list();
    }

    /**
     * 获取某段时间运动信息
     *
     * @param accountId 用户id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 运动信息
     */
    public List<Exercise> getExerciseByDateRange(Integer accountId, String startDate, String endDate) {
        startDate += " 00:00";
        endDate += " 23:59";
        return this.query()
                .eq("account_id", accountId)
                .between("start_time", startDate, endDate)
                .list();
    }

    /**
     * 获取当日运动信息列表
     *
     * @param accountId 用户id
     * @return 运动信息列表
     */
    public List<Exercise> getExerciseList(Integer accountId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(System.currentTimeMillis());
        return this.query()
                .eq("account_id", accountId)
                .likeRight("start_time", today)
                .list();
    }

    /**
     * 获取当前运动信息
     *
     * @param accountId 用户id
     * @return 运动信息
     */
    public Exercise getCurrentExercise(Integer accountId) {
        return this.query()
                .eq("account_id", accountId)
                .orderByDesc("start_time")
                .one();
    }

    /**
     * 过滤运动信息
     *
     * @param exerciseInfoVOS 运动信息列表
     * @param vo              过滤条件
     * @return 过滤后的运动信息列表
     */
    public List<ExerciseInfoVO> filterExercise(List<ExerciseInfoVO> exerciseInfoVOS, ExerciseFilterVO vo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return exerciseInfoVOS.stream()
                .filter(exerciseInfoVO -> {
                    try {
                        if (timeFormat.format(dateFormat.parse(exerciseInfoVO.getStartTime())).compareTo(vo.getStartTime()) < 0) {
                            return false;
                        }
                        if (timeFormat.format(dateFormat.parse(exerciseInfoVO.getEndTime())).compareTo(vo.getEndTime()) > 0) {
                            return false;
                        }
                        System.out.println(exerciseInfoVO.getDuration());
                        if (exerciseInfoVO.getDuration() < vo.getMinDuration()) {
                            return false;
                        }
                        if (exerciseInfoVO.getDuration() > vo.getMaxDuration()) {
                            return false;
                        }
                        if (vo.getFeelings() != null) {
                            if (vo.getFeelings().charAt(0) == '0' && exerciseInfoVO.getFeelings() == 0) {
                                return false;
                            }
                            if (vo.getFeelings().charAt(1) == '0' && exerciseInfoVO.getFeelings() == 1) {
                                return false;
                            }
                            if (vo.getFeelings().charAt(2) == '0' && exerciseInfoVO.getFeelings() == 2) {
                                return false;
                            }
                        }
                        if (vo.getRemark() != null) {
                            if (vo.getRemark().charAt(0) == '0' && exerciseInfoVO.getRemark() == null) {
                                return false;
                            }
                            if (vo.getRemark().charAt(1) == '0' && exerciseInfoVO.getRemark() != null) {
                                return false;
                            }
                        }
                        return true;
                    } catch (ParseException e) {
                        return false;
                    }
                })
                .toList();
    }
}
