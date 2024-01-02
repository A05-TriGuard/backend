package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.FoodFavorites;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
@Transactional
public class FoodFavoritesServiceImplTest {
	@Autowired
	private FoodFavoritesServiceImpl foodFavoritesServiceImpl;

	@Test
	public void addFoodFavorites() {
		Integer accountId = 2;
		Integer foodId = 5;
		FoodFavorites actual = foodFavoritesServiceImpl.addFoodFavorites(accountId, foodId);
		assertNotNull(actual);
	}

	@Test
	public void getFoodFavorites() {
		Integer accountId = 2;
		List<FoodFavorites> actual = foodFavoritesServiceImpl.getFoodFavorites(accountId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getFoodFavoritesByFoodId() {
		Integer accountId = 2;
		Integer foodId = 1;
		FoodFavorites actual = foodFavoritesServiceImpl.getFoodFavorites(accountId, foodId);
		assertNotNull(actual);
	}

	@Test
	public void deleteFoodFavorites() {
		Integer accountId = 2;
		Integer foodId = 1;
		boolean actual = foodFavoritesServiceImpl.deleteFoodFavorites(accountId, foodId);
		assertTrue(actual);
	}
}
