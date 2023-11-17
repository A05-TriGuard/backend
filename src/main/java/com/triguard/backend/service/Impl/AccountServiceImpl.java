package com.triguard.backend.service.Impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.vo.request.Authorization.EmailConfirmResetVO;
import com.triguard.backend.entity.vo.request.Authorization.EmailRegisterVO;
import com.triguard.backend.entity.vo.request.Authorization.EmailResetVO;
import com.triguard.backend.mapper.AccountMapper;
import com.triguard.backend.service.AccountService;
import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.FlowUtils;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 账户信息处理相关服务
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    //验证邮件发送冷却时间限制，秒为单位
    @Value("${spring.web.verify.mail-limit}")
    int verifyLimit;

    @Resource
    AmqpTemplate rabbitTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    FlowUtils flow;

    /**
     * 从数据库中通过用户名或邮箱查找用户详细信息
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException 如果用户未找到则抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findAccountByNameOrEmailOrPhone(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(username)
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    /**
     * 生成邮箱验证码存入Redis中，并将邮件发送请求提交到消息队列等待发送
     * @param type 类型
     * @param email 邮件地址
     * @param address 请求IP地址
     * @return 操作结果，null表示正常，否则为错误原因
     */
    public String sendEmailVerificationCode(String type, String email, String address){
        synchronized (address.intern()) {
            if(this.verifyLimit(address, ConstUtils.VERIFY_EMAIL_LIMIT))
                return "请求频繁，请稍后再试";
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of("type",type,"email", email, "code", code);
            String jsonString = JSONObject.toJSONString(data);
            rabbitTemplate.convertAndSend(ConstUtils.MQ_MAIL, jsonString);
            stringRedisTemplate.opsForValue()
                    .set(ConstUtils.VERIFY_EMAIL_DATA + email, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        }
    }

    /**
     * 生成手机验证码存入Redis中，并将短信发送请求提交到消息队列等待发送
     * @param type 类型
     * @param phone 手机号
     * @param address 请求IP地址
     * @return 操作结果，null表示正常，否则为错误原因
     */
    public String sendPhoneVerificationCode(String type, String phone, String address) {
        synchronized (address.intern()) {
            if(this.verifyLimit(address, ConstUtils.VERIFY_PHONE_LIMIT))
                return "请求频繁，请稍后再试";
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of("type",type,"phone", phone, "code", code);
            String jsonString = JSONObject.toJSONString(data);
            rabbitTemplate.convertAndSend(ConstUtils.MQ_SMS, jsonString);
            stringRedisTemplate.opsForValue()
                    .set(ConstUtils.VERIFY_PHONE_DATA + phone, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;
        }
    }

    /**
     * 邮件验证码注册账号操作，需要检查验证码是否正确以及邮箱、用户名是否存在重名
     * @param info 注册基本信息
     * @return 操作结果，null表示正常，否则为错误原因
     */
    public String registerEmailAccount(EmailRegisterVO info){
        String email = info.getEmail();
        String code = this.getEmailVerifyCode(email);
        if(code == null) return "请先获取验证码";
        if(!code.equals(info.getCode())) return "验证码错误，请重新输入";
        if(this.existsAccountByEmail(email)) return "该邮件地址已被注册";
        String username = info.getUsername();
        if(this.existsAccountByUsername(username)) return "该用户名已被他人使用，请重新更换";
        String password = passwordEncoder.encode(info.getPassword());
        Account account = new Account(null, info.getUsername(),
                password, email, null, ConstUtils.ROLE_DEFAULT, new Date());
        if(!this.save(account)) {
            return "内部错误，注册失败";
        } else {
            this.deleteEmailVerifyCode(email);
            return null;
        }
    }

    /**
     * 邮件验证码重置密码操作，需要检查验证码是否正确
     * @param info 重置基本信息
     * @return 操作结果，null表示正常，否则为错误原因
     */
    @Override
    public String resetEmailAccountPassword(EmailResetVO info) {
        String verify = emailConfirmReset(new EmailConfirmResetVO(info.getEmail(), info.getCode()));
        if(verify != null) return verify;
        String email = info.getEmail();
        String password = passwordEncoder.encode(info.getPassword());
        boolean update = this.update().eq("email", email).set("password", password).update();
        if(update) {
            this.deleteEmailVerifyCode(email);
        }
        return update ? null : "更新失败，请联系管理员";
    }

    /**
     * 重置密码确认操作，验证验证码是否正确
     * @param info 验证基本信息
     * @return 操作结果，null表示正常，否则为错误原因
     */
    @Override
    public String emailConfirmReset(EmailConfirmResetVO info) {
        String email = info.getEmail();
        String code = this.getEmailVerifyCode(email);
        if(code == null) return "请先获取验证码";
        if(!code.equals(info.getCode())) return "验证码错误，请重新输入";
        return null;
    }

    /**
     * 移除Redis中存储的邮件验证码
     * @param email 电邮
     */
    private void deleteEmailVerifyCode(String email){
        String key = ConstUtils.VERIFY_EMAIL_DATA + email;
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取Redis中存储的邮件验证码
     * @param email 电邮
     * @return 验证码
     */
    private String getEmailVerifyCode(String email){
        String key = ConstUtils.VERIFY_EMAIL_DATA + email;
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 针对IP地址进行邮件验证码获取限流
     * @param address 地址
     * @return 是否通过验证
     */
    private boolean verifyLimit(String address, String type) {
        String key = type + address;
        return !flow.limitOnceCheck(key, verifyLimit);
    }

    /**
     * 通过用户名或邮件地址查找用户
     * @param text 用户名或邮件
     * @return 账户实体
     */
    public Account findAccountByNameOrEmailOrPhone(String text){
        return this.query()
                .eq("username", text).or()
                .eq("email", text).or()
                .eq("phone", text)
                .one();
    }

    /**
     * 查询指定邮箱的用户是否已经存在
     * @param email 邮箱
     * @return 是否存在
     */
    private boolean existsAccountByEmail(String email){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("email", email));
    }

    /**
     * 查询指定用户名的用户是否已经存在
     * @param username 用户名
     * @return 是否存在
     */
    private boolean existsAccountByUsername(String username){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("username", username));
    }

    /**
     * 查询指定手机号的用户是否已经存在
     * @param phone 手机号
     * @return 是否存在
     */
    private boolean existsAccountByPhone(String phone){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("phone", phone));
    }
}
