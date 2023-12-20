package com.triguard.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.triguard.backend.entity.RestBean;
import com.triguard.backend.entity.dto.Account;
import com.triguard.backend.entity.vo.request.Authorization.EmailConfirmResetVO;
import com.triguard.backend.entity.vo.request.Authorization.EmailRegisterVO;
import com.triguard.backend.entity.vo.request.Authorization.EmailResetVO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByNameOrEmailOrPhone(String text);
    String sendEmailVerificationCode(String type, String email, String address);
    String sendPhoneVerificationCode(String type, String phone, String address);
    String registerEmailAccount(EmailRegisterVO info);
    String resetEmailAccountPassword(EmailResetVO info);
    String emailConfirmReset(EmailConfirmResetVO info);
    String setProfile(Integer accountId, MultipartFile profile);
}
