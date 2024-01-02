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
public class BloodPressureControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void recordBloodPressure() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sbp", 120);
		jsonObject.put("dbp", 80);
		jsonObject.put("heartRate", 80);
		jsonObject.put("arm", 0);
		jsonObject.put("feeling", 0);
		jsonObject.put("remark", "remark");
		jsonObject.put("date", "2023-12-29");
		jsonObject.put("time", "12:00");
		this.mockMvc.perform(post("/api/blood-pressure/create")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void recordBloodPressureWithAccountId() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", 1);
		jsonObject.put("sbp", 120);
		jsonObject.put("dbp", 80);
		jsonObject.put("heartRate", 80);
		jsonObject.put("arm", 0);
		jsonObject.put("feeling", 0);
		jsonObject.put("remark", "remark");
		jsonObject.put("date", "2023-12-29");
		jsonObject.put("time", "12:00");
		this.mockMvc.perform(post("/api/blood-pressure/create")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteBloodPressure() throws Exception {
		this.mockMvc.perform(get("/api/blood-pressure/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("id", "1")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void updateBloodPressure() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 1);
		jsonObject.put("sbp", 105);
		this.mockMvc.perform(post("/api/blood-pressure/update")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void getBloodPressureByDate() throws Exception {
		this.mockMvc.perform(get("/api/blood-pressure/get-by-date")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("date", "2023-12-06")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodPressureByDateRange() throws Exception {
		this.mockMvc.perform(get("/api/blood-pressure/get-by-date-range")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("startDate", "2023-12-06")
					.param("endDate", "2023-12-07")
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodPressureByFilter() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startDate", "2023-12-06");
		jsonObject.put("endDate", "2023-12-07");
		this.mockMvc.perform(post("/api/blood-pressure/get-by-filter")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBloodPressureByFilterWithAccountId() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accountId", 3);
		jsonObject.put("startDate", "2023-12-06");
		jsonObject.put("endDate", "2023-12-07");
		this.mockMvc.perform(post("/api/blood-pressure/get-by-filter")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
