package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Steps;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StepsServiceImplTest {
	@Autowired
	private StepsServiceImpl stepsServiceImpl;

	@Test
	public void getTodaySteps() {
		Integer accountId = 2;
		List<Steps> actual = stepsServiceImpl.getTodaySteps(accountId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void updateTodaySteps() {
		Integer accountId = 2;
		Integer newSteps = 5;
		boolean actual = stepsServiceImpl.updateTodaySteps(accountId, newSteps);
		assertTrue(actual);
	}

	@Test
	public void getStepsByDate() {
		Integer accountId = 2;
		String date = "2023-12-12";
		List<Steps> actual = stepsServiceImpl.getStepsByDate(accountId, date);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getStepsByDateRange() {
		Integer accountId = 2;
		String startDate = "2023-12-12";
		String endDate = "2023-12-13";
		List<Steps> actual = stepsServiceImpl.getStepsByDateRange(accountId, startDate, endDate);
		assertNotNull(actual);
		assertEquals(2, actual.size());
	}
}
