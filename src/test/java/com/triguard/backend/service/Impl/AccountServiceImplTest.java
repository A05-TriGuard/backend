package com.triguard.backend.service.Impl;

import com.triguard.backend.utils.FlowUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public class AccountServiceImplTest {
	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@MockBean
	private FlowUtils flowUtils;
}
