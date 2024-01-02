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
public class MedicineControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HistoryUtils historyUtils;

	@Test
	public void searchMedicine() throws Exception {
		this.mockMvc.perform(get("/api/medicine/search")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("keyword", "测试")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getSearchHistory() throws Exception {
		this.mockMvc.perform(get("/api/medicine/search-history")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}

	@Test
	public void getMedicineInfo() throws Exception {
		this.mockMvc.perform(get("/api/medicine/info")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("id", "1")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getMedicineInfoHistory() throws Exception {
		this.mockMvc.perform(get("/api/medicine/info-history")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}

	@Test
	public void addMedicineFavorites() throws Exception {
		this.mockMvc.perform(post("/api/medicine/favorites/add")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("medicineId", "1")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteMedicineFavorites() throws Exception {
		this.mockMvc.perform(get("/api/medicine/favorites/delete")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("medicineId", "2")
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}

	@Test
	public void getMedicineFavorites() throws Exception {
		this.mockMvc.perform(get("/api/medicine/favorites/list")
				.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk());
	}
}
