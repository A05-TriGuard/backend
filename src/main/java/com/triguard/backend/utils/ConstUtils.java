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
    //药品搜索历史
    public final static String SEARCH_MEDICINE_HISTORY = "search:medicine:history:";
    //药品信息获取历史
    public final static String GET_MEDICINE_INFO_HISTORY = "get:medicine:info:history:";
    //食物搜索历史
    public final static String SEARCH_FOOD_HISTORY = "search:food:history:";
    //食物信息获取历史
    public final static String GET_FOOD_INFO_HISTORY = "get:food:info:history:";
}
