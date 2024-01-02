package com.triguard.backend.utils;

/**
 * 一些常量字符串整合
 */
public final class ConstUtils {
    //JWT令牌
    public final static String JWT_BLACK_LIST = "jwt:blacklist:";
    public final static String JWT_FREQUENCY = "jwt:frequency:";
    //请求频率限制
    public final static String FLOW_LIMIT_COUNTER = "flow:counter:";
    public final static String FLOW_LIMIT_BLOCK = "flow:block:";
    //邮件验证码
    public final static String VERIFY_EMAIL_LIMIT = "verify:email:limit:";
    public final static String VERIFY_EMAIL_DATA = "verify:email:data:";
    //短信验证码
    public final static String VERIFY_PHONE_LIMIT = "verify:phone:limit:";
    public final static String VERIFY_PHONE_DATA = "verify:phone:data:";
    //过滤器优先级
    public final static int ORDER_FLOW_LIMIT = -101;
    public final static int ORDER_CORS = -102;
    //请求自定义属性
    public final static String ATTR_USER_ID = "userId";
    //消息队列
    public final static String MQ_MAIL = "mail";
    public final static String MQ_SMS = "sms";
    //用户角色
    public final static String ROLE_DEFAULT = "user";
    public static final String ROLE_ADMIN = "admin";
    //药品搜索历史
    public final static String SEARCH_MEDICINE_HISTORY = "search:medicine:history:";
    //药品信息获取历史
    public final static String GET_MEDICINE_INFO_HISTORY = "get:medicine:info:history:";
    //食物搜索历史
    public final static String SEARCH_FOOD_HISTORY = "search:food:history:";
    //食物信息获取历史
    public final static String GET_FOOD_INFO_HISTORY = "get:food:info:history:";
    // 运动信息
    public static final String REDIS_KEY_EXERCISE = "exercise:";
    // 默认头像
    public static final String DEFAULT_AVATAR = "default_avatar.png";
    public static final String TEST_JWT_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaW4xIiwiaWQiOjIsImV4cCI6MTcwNDM4OTgwMywiaWF0IjoxNzA0MTMwNjAzLCJqdGkiOiI2ODc4MWUxMy00YjUxLTRiZWYtYjdjMC0zYmRjNWU2MGQzNjAiLCJhdXRob3JpdGllcyI6WyJST0xFX3VzZXIiXX0.00uCztvNsNJw8mFzhNRaDh_6dZ01nYfTL4U7l2bI14I";
    public static final String TEST_ADMIN_JWT_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaW4iLCJpZCI6MywiZXhwIjoxNzA0NDY4NDAxLCJpYXQiOjE3MDQyMDkyMDEsImp0aSI6ImRiZDk4MGU3LTE0ZTktNGI5ZC1hOTJjLWIzZjc5YjBlYWU4YyIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtaW4iXX0.5CFNvmUykbxUmtr3PYsy1lQBxx12YZvVBvoiO24sHvA";
}
