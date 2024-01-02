package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Food;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FoodServiceImplTest {
	@Autowired
	private FoodServiceImpl foodServiceImpl;

	@Test
	public void searchFood() {
		String keyword = "测试";
		List<Food> actual = foodServiceImpl.searchFood(keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getByNames() {
		List<String> list = new ArrayList<>();
		list.add("测试食物1");
		list.add("测试食物2");
		List<Food> actual = foodServiceImpl.getByNames(list);
		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

	@Test
	public void getCalories() {
		String food = "测试食物1";
		Integer weight = 100;
		Integer actual = foodServiceImpl.getCalories(food, weight);
		assertNotNull(actual);
		String food1 = "测试食物10";
		Integer weight1 = 100;
		Integer actual1 = foodServiceImpl.getCalories(food1, weight1);
		assertNotNull(actual1);
		assertEquals(0, actual1);
	}
}
