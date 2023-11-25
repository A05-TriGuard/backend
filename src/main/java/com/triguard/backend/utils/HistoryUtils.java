package com.triguard.backend.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoryUtils {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 保存字符串记录
     * @param key 键
     * @param value 值
     */
    public void saveStringHistory(String key, String value) {
        List<String> history = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (history != null && history.contains(value)) {
            stringRedisTemplate.opsForList().remove(key, 0, value);
        }
        stringRedisTemplate.opsForList().leftPush(key, value);
        stringRedisTemplate.opsForList().trim(key, 0, 9);
    }

    /**
     * 获取字符串记录
     * @param key 键
     * @return 值
     */
    public List<String> getStringHistory(String key) {
        return stringRedisTemplate.opsForList().range(key, 0, 9);
    }

    /**
     * 保存整数记录
     * @param key 键
     * @param value 值
     */
    public void saveIntegerHistory(String key, Integer value) {
        List<String> history = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (history != null && history.contains(value.toString())) {
            stringRedisTemplate.opsForList().remove(key, 0, value.toString());
        }
        stringRedisTemplate.opsForList().leftPush(key, value.toString());
        stringRedisTemplate.opsForList().trim(key, 0, 9);
    }

    /**
     * 获取整数记录
     * @param key 键
     * @return 值
     */
    public List<Integer> getIntegerHistory(String key) {
        List<String> history = stringRedisTemplate.opsForList().range(key, 0, 9);
        if (history == null) {
            return new ArrayList<>();
        }
        return history.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
