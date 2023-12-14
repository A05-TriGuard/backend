package com.triguard.backend.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getDiseaseArticle() throws Exception {
		this.mockMvc.perform(get("/api/article/disease/list")
				.param("page", "123")
				.param("size", "123"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.<key>").value("<value>"));
	}

	@Test
	public void getScienceArticle() throws Exception {
		this.mockMvc.perform(get("/api/article/science/list")
				.param("page", "123")
				.param("size", "123"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.<key>").value("<value>"));
	}
}
