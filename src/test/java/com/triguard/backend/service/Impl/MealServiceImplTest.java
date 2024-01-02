package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Meal;
import com.triguard.backend.entity.vo.request.Meal.MealCreateVO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
@Transactional
public class MealServiceImplTest {
	@Autowired
	private MealServiceImpl mealServiceImpl;

	@Test
	public void createMeal() {
		Integer accountId = 3;
		MealCreateVO mealCreateVO = new MealCreateVO();
		mealCreateVO.setFood("abc");
		mealCreateVO.setWeight(3);
		Meal actual = mealServiceImpl.createMeal(accountId, mealCreateVO);
		assertNotNull(actual);
	}

	@Test
	public void deleteMeal() {
		Integer accountId = 2;
		Integer id = 1;
		Boolean actual = mealServiceImpl.deleteMeal(accountId, id);
		assertTrue(actual);
	}

	@Test
	public void getMeals() {
		Integer accountId = 2;
		String date = "2023-12-26";
		String category = "全部";
		List<Meal> actual = mealServiceImpl.getMeals(accountId, date, category);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
		category = "早餐";
		actual = mealServiceImpl.getMeals(accountId, date, category);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}
}
