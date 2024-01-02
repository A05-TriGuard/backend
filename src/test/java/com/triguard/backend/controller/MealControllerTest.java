package com.triguard.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import com.triguard.backend.utils.ConstUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MealControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createMeal() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("date", "2023-12-30");
		jsonObject.put("category", "早餐");
		jsonObject.put("food", "鸡蛋");
		jsonObject.put("weight", 100);
		this.mockMvc.perform(post("/api/meal/create")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString()))
					.andExpect(status().isOk());
	}

	@Test
	public void deleteMeal() throws Exception {
		this.mockMvc.perform(get("/api/meal/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("id", "1"))
					.andExpect(status().isOk());
	}

	@Test
	public void getMeal() throws Exception {
		this.mockMvc.perform(get("/api/meal/get")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("date", "2023-12-26")
					.param("category", "全部"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void setMealGoal() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("calories", 1000);
		jsonObject.put("carbohydrates", 1000);
		jsonObject.put("lipids", 1000);
		jsonObject.put("cholesterol", 1000);
		jsonObject.put("proteins", 1000);
		jsonObject.put("fiber", 1000);
		jsonObject.put("sodium", 1000);
		this.mockMvc.perform(post("/api/meal/set-goal")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString()))
					.andExpect(status().isOk());
	}

	@Test
	public void getMealGoal() throws Exception {
		this.mockMvc.perform(get("/api/meal/get-goal")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
