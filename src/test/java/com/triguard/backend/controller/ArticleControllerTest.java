package com.triguard.backend.controller;

import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.JwtUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import com.triguard.backend.entity.RestBean;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArticleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getDiseaseArticle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/article/disease/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getScienceArticle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/article/science/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getArticleById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/article/get")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("id", "1")
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void addArticleFavorites() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/article/favorites/add")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("articleId", "15")
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteArticleFavorites() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/article/favorites/delete")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("articleId", "10")
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void getDiseaseArticleFavoritesList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/article/favorites/disease/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("page", "1")
						.param("size", "10")
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getScienceArticleFavoritesLis() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/article/favorites/science/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("page", "1")
						.param("size", "10")
						.accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

}
