package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Food;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FoodServiceImplTest {
	@Autowired
	private FoodServiceImpl foodServiceImpl;

	@Test
	public void searchFood() {
		String keyword = "abc";
		List<Food> expected = new ArrayList<>();
		List<Food> actual = foodServiceImpl.searchFood(keyword);
		System.out.println(actual);
		assertEquals(expected, actual);
	}
}
