package com.triguard.backend.controller.admin;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminAccountControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAccountList() throws Exception {
		this.mockMvc.perform(get("/api/admin/account/list")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getAllAccountList() throws Exception {
		this.mockMvc.perform(get("/api/admin/account/all")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
