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

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BloodSugarControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void recordBloodSugar() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bs", 1.0);
		jsonObject.put("meal", 1);
		jsonObject.put("feeling", 1);
		jsonObject.put("remark", "abc");
		jsonObject.put("date", "2023-12-20");
		jsonObject.put("time", "12:00");
		this.mockMvc.perform(post("/api/blood-sugar/create")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void recordBloodSugarWithAccountId() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", 1);
		jsonObject.put("bs", 1.0);
		jsonObject.put("meal", 1);
		jsonObject.put("feeling", 1);
		jsonObject.put("remark", "abc");
		jsonObject.put("date", "2023-12-20");
		jsonObject.put("time", "12:00");
		this.mockMvc.perform(post("/api/blood-sugar/create")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteBloodSugar() throws Exception {
		this.mockMvc.perform(get("/api/blood-sugar/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("id", "1")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk());
	}

	@Test
	public void updateBloodSugar() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 1);
		jsonObject.put("bs", 2.0);
		this.mockMvc.perform(post("/api/blood-sugar/update")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk());
	}

	@Test
	public void getBloodSugarByDate() throws Exception {
		this.mockMvc.perform(get("/api/blood-sugar/get-by-date")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("date", "2023-12-06")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodSugarByDateRange() throws Exception {
		this.mockMvc.perform(get("/api/blood-sugar/get-by-date-range")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("startDate", "2023-12-06")
					.param("endDate", "2023-12-07")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodSugarByFilter() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startDate", "2023-12-06");
		jsonObject.put("endDate", "2023-12-07");
		this.mockMvc.perform(post("/api/blood-lipids/get-by-filter")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodSugarByFilterWithAccountId() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", 2);
		jsonObject.put("startDate", "2023-12-06");
		jsonObject.put("endDate", "2023-12-07");
		this.mockMvc.perform(post("/api/blood-lipids/get-by-filter")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
