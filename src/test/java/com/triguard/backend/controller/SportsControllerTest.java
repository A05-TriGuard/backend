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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SportsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getTodaySportsInfo() throws Exception {
		this.mockMvc.perform(get("/api/sports/info")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("endDate", "2023-12-20")
				.param("startDate", "2023-12-25"))
			.andExpect(status().isOk());
	}

	@Test
	public void getTodaySteps() throws Exception {
		this.mockMvc.perform(get("/api/sports/steps")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("endDate", "2023-12-20")
				.param("startDate", "2023-12-25"))
			.andExpect(status().isOk());
	}

	@Test
	public void getTodaySteps1() throws Exception {
		this.mockMvc.perform(get("/api/sports/steps")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("date", "2023-12-21"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void updateTodaySteps() throws Exception {
		this.mockMvc.perform(post("/api/sports/steps/update")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("steps", "1000"))
				.andExpect(status().isOk());
	}

	@Test
	public void getExerciseList() throws Exception {
		this.mockMvc.perform(get("/api/sports/exercise/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("date", "2023-12-21"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getExerciseList1() throws Exception {
		this.mockMvc.perform(get("/api/sports/exercise/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("startDate", "2023-12-21")
						.param("endDate", "2023-12-22"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void filterExercise() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("startDate", "2023-12-21");
		jsonObject.put("endDate", "2023-12-22");
		this.mockMvc.perform(post("/api/sports/exercise/filter")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject.toJSONString()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	@Order(1)
	public void startExercise() throws Exception {
		this.mockMvc.perform(post("/api/sports/exercise/start")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("time", "2023-12-21 12:00:00")
						.param("type", "1"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void pauseExercise() throws Exception {
		this.mockMvc.perform(post("/api/sports/exercise/pause")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("time", "2023-12-21 12:30:00"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	public void continueExercise() throws Exception {
		this.mockMvc.perform(post("/api/sports/exercise/continue")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("time", "2023-12-21 12:40:00"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void endExercise() throws Exception {
		this.mockMvc.perform(post("/api/sports/exercise/end")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("time", "2023-12-21 13:00:00"))
				.andExpect(status().isOk());
	}

	@Test
	public void getCurrentExercise() throws Exception {
		this.mockMvc.perform(get("/api/sports/exercise/current")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN))
				.andExpect(status().isOk());
	}

	@Test
	public void getTodayExerciseDuration() throws Exception {
		this.mockMvc.perform(get("/api/sports/exercise/duration")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void updateExercise() throws Exception {
		this.mockMvc.perform(post("/api/sports/exercise/update")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("id", "1")
						.param("type", "2")
						.param("feelings", "1")
						.param("remark", "1"))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteExercise() throws Exception {
		this.mockMvc.perform(get("/api/sports/exercise/delete")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("id", "1"))
				.andExpect(status().isOk());
	}
}
