package com.triguard.backend.service.Impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FollowServiceImplTest {

	@Autowired
	private FollowServiceImpl followServiceImpl;

	@Test
	@Order(1)
	public void saveFollow() {
		Integer accountId = 2;
		Integer momentAccountId = 3;
		Boolean expected = true;
		Boolean actual = followServiceImpl.saveFollow(accountId, momentAccountId);
		assertEquals(expected, actual);
	}

	@Test
	@Order(2)
	public void removeFollow() {
		Integer accountId = 2;
		Integer momentAccountId = 5;
		Boolean expected = true;
		Boolean actual = followServiceImpl.removeFollow(accountId, momentAccountId);
		assertNotEquals(expected, actual);
	}
}
