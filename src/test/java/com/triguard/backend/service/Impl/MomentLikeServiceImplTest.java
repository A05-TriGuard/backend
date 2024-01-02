package com.triguard.backend.service.Impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MomentLikeServiceImplTest {
	@Autowired
	private MomentLikeServiceImpl momentLikeServiceImpl;

	@Test
	public void saveMomentLike() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentLikeServiceImpl.saveMomentLike(accountId, momentId);
		assertTrue(actual);
	}

	@Test
	public void removeMomentLike() {
		Integer accountId = 2;
		Integer momentId = 3;
		Boolean actual = momentLikeServiceImpl.removeMomentLike(accountId, momentId);
		assertFalse(actual);
	}
}
