package com.triguard.backend.controller;

import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.HistoryUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FoodControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HistoryUtils historyUtils;

	@Test
	@Order(1)
	public void searchFood() throws Exception {
		this.mockMvc.perform(get("/api/food/search")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("keyword", "测试")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	@Order(2)
	public void getSearchHistory() throws Exception {
		this.mockMvc.perform(get("/api/food/search-history")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	public void getFoodInfo() throws Exception {
		this.mockMvc.perform(get("/api/food/info")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("id", "1")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	@Order(4)
	public void getFoodInfoHistory() throws Exception {
		this.mockMvc.perform(get("/api/food/info-history")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk());
	}

	@Test
	public void addFoodFavorites() throws Exception {
		this.mockMvc.perform(post("/api/food/favorites/add")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("foodId", "3")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteFoodFavorites() throws Exception {
		this.mockMvc.perform(get("/api/food/favorites/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("foodId", "2")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void getFoodFavoritesList() throws Exception {
		this.mockMvc.perform(get("/api/food/favorites/list")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
