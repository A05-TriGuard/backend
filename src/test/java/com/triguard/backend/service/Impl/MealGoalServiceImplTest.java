package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.MealGoal;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MealGoalServiceImplTest {
	@Autowired
	private MealGoalServiceImpl mealGoalServiceImpl;

	@Test
	public void setMealGoal() {
		Integer accountId = 2;
		MealGoal mealGoal = new MealGoal();
		mealGoal.setAccountId(accountId);
		mealGoal.setCalories(1000);
		Boolean actual = mealGoalServiceImpl.setMealGoal(accountId, mealGoal);
		assertTrue(actual);
	}
}
