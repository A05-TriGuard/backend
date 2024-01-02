package com.triguard.backend.service.Impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MomentFavoriteServiceImplTest {
	@Autowired
	private MomentFavoriteServiceImpl momentFavoriteServiceImpl;

	@Test
	public void saveMomentFavorite() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentFavoriteServiceImpl.saveMomentFavorite(accountId, momentId);
		assertTrue(actual);
	}

	@Test
	public void removeMomentFavorite() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentFavoriteServiceImpl.removeMomentFavorite(accountId, momentId);
		assertFalse(actual);
	}
}
