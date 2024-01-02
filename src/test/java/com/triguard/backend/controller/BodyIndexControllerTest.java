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
public class BodyIndexControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void setBodyIndex() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sex", "男");
		jsonObject.put("age", 20);
		jsonObject.put("height", 180);
		jsonObject.put("weight", 70);
		jsonObject.put("level", 0);
		this.mockMvc.perform(post("/api/body-index/set")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObject.toJSONString())
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getBodyIndex() throws Exception {
		this.mockMvc.perform(get("/api/body-index/get")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
