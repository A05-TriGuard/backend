package com.triguard.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Steps;
import com.triguard.backend.mapper.StepsMapper;
import com.triguard.backend.service.StepsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class StepsServiceImpl extends ServiceImpl<StepsMapper, Steps> implements StepsService {

    /**
     * 获取今日步数
     *
     * @param accountId 用户id
     * @return 今日步数
     */
    public List<Steps> getTodaySteps(Integer accountId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(System.currentTimeMillis());
        List<Steps> steps = this.query()
                .eq("account_id", accountId)
                .eq("date", today)
                .list();
        if (steps.isEmpty()) {
            Steps newSteps = new Steps();
            newSteps.setSteps(0);
            newSteps.setDate(today);
            steps.add(newSteps);
        }
        return steps;
    }

    /**
     * 更新今日步数
     *
     * @param accountId 用户id
     * @param newSteps     步数
     * @return 是否成功
     */
    public boolean updateTodaySteps(Integer accountId, Integer newSteps) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(System.currentTimeMillis());
        Steps steps = this.query()
                .eq("account_id", accountId)
                .eq("date", today)
                .one();
        if (steps == null) {
            steps = new Steps();
            steps.setAccountId(accountId);
            steps.setSteps(newSteps);
            steps.setDate(today);
            return this.save(steps);
        } else {
            steps.setSteps(steps.getSteps() + newSteps);
            return this.updateById(steps);
        }
    }

    /**
     * 获取某日步数
     *
     * @param accountId 用户id
     * @param date      日期
     * @return 步数
     */
    public List<Steps> getStepsByDate(Integer accountId, String date) {
        List<Steps> steps = this.query()
                .eq("account_id", accountId)
                .eq("date", date)
                .list();
        if (steps.isEmpty()) {
            Steps newSteps = new Steps();
            newSteps.setSteps(0);
            newSteps.setDate(date);
            steps.add(newSteps);
        }
        return steps;
    }

    /**
     * 获取某日范围内步数
     *
     * @param accountId  用户id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 步数
     */
    public List<Steps> getStepsByDateRange(Integer accountId, String startDate, String endDate) {
        List<Steps> steps = this.query()
                .eq("account_id", accountId)
                .between("date", startDate, endDate)
                .list();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        List<Steps> allDays = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            String formattedDate = date.format(formatter);
            Steps newSteps = steps.stream()
                    .filter(step -> step.getDate().equals(formattedDate))
                    .findFirst()
                    .orElse(new Steps(null, null, 0, formattedDate));
            allDays.add(newSteps);
        }
        return allDays;
    }
}
