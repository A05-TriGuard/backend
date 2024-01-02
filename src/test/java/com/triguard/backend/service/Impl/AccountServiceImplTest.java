package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.vo.request.Authorization.EmailRegisterVO;
import com.triguard.backend.utils.FlowUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.triguard.backend.entity.dto.Account;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@SpringBootTest
@Transactional
public class AccountServiceImplTest {
	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@MockBean
	private MultipartFile file;

	@Test
	public void testLoadUserByUsername() throws Exception {
		String username = "username";
		assertThrows(UsernameNotFoundException.class, () -> {
			accountServiceImpl.loadUserByUsername(username);
		});
		String username2 = "admin";
		UserDetails result = accountServiceImpl.loadUserByUsername(username2);
		assertNotNull(result);
	}

	@Test
	public void registerEmailAccountTest() {
		EmailRegisterVO emailRegisterVO = new EmailRegisterVO();
		emailRegisterVO.setEmail("2374530749@qq.com");
		String result = accountServiceImpl.registerEmailAccount(emailRegisterVO);
		assertEquals("请先获取验证码",               result);
	}

	@Test
	public void findAccountByNameOrEmailOrPhone() {
		String text = "admin";
		Account actual = accountServiceImpl.findAccountByNameOrEmailOrPhone(text);
		assertNotNull(actual);
		assertEquals("admin", actual.getUsername());
	}

	@Test
	public void setProfileTest() throws IOException {
		when(file.isEmpty()).thenReturn(false);
		when(file.getOriginalFilename()).thenReturn("test.jpg");
		doNothing().when(file).transferTo(any(File.class));
		Integer accountId = 2;
		String result = accountServiceImpl.setProfile(accountId, file);
		assertNotNull(result);
	}
}
