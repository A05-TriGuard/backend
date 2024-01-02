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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BloodLipidsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createBloodLipids() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tc", "100");
		jsonObject.put("tg", "100");
		jsonObject.put("hdl", "100");
		jsonObject.put("ldl", "100");
		jsonObject.put("feeling", "1");
		jsonObject.put("remark", "remark");
		jsonObject.put("date", "2023-12-20");
		jsonObject.put("time", "12:00");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/blood-lipids/create")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObject.toJSONString())
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void createBloodLipidsWithAccountId() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", "1");
		jsonObject.put("tc", "100");
		jsonObject.put("tg", "100");
		jsonObject.put("hdl", "100");
		jsonObject.put("ldl", "100");
		jsonObject.put("feeling", "1");
		jsonObject.put("remark", "remark");
		jsonObject.put("date", "2023-12-20");
		jsonObject.put("time", "12:00");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/blood-lipids/create")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObject.toJSONString())
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteBloodLipids() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/blood-lipids/delete")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("id", "5")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}

	@Test
	public void updateBloodLipids() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tc", "120");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/blood-lipids/update")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObject.toJSONString())
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}

	@Test
	public void getBloodLipidsByDate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/blood-lipids/get-by-date")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("date", "2021-01-01")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodLipidsByDateRange() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/blood-lipids/get-by-date-range")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("startDate", "2021-01-01")
				.param("endDate", "2021-01-02")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodLipidsByDateRangeWithAccountId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/blood-lipids/get-by-date-range")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("accountId", "2")
				.param("startDate", "2021-01-01")
				.param("endDate", "2021-01-02")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodLipidsByFilter() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startDate", "2021-01-01");
		jsonObject.put("endDate", "2021-01-02");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/blood-lipids/get-by-filter")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObject.toJSONString())
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.bloodLipidsList").isNotEmpty());
	}

	@Test
	public void getBloodLipidsByFilterWithAccountId() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", "2");
		jsonObject.put("startDate", "2021-01-01");
		jsonObject.put("endDate", "2021-01-02");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/blood-lipids/get-by-filter")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObject.toJSONString())
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.bloodLipidsList").isNotEmpty());
	}
}
