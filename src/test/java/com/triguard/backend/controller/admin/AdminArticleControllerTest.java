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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminArticleControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createArticle() throws Exception {
		this.mockMvc.perform(post("/api/admin/article/create")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN)
				.param("content", "abc")
				.param("subtitle", "abc")
				.param("title", "abc")
				.param("type", "abc"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void updateArticle() throws Exception {
		this.mockMvc.perform(post("/api/admin/article/update")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN)
						.param("articleId", "1")
						.param("type", "bcd")
						.param("content", "bcd"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	@Order(1)
	public void createArticleDraft() throws Exception {
		this.mockMvc.perform(post("/api/admin/article/draft")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN)
				.param("content", "abc")
				.param("title", "abc")
				.param("type", "abc"))
			.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void getArticleDraft() throws Exception {
		this.mockMvc.perform(get("/api/admin/article/draft")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteArticle() throws Exception {
		this.mockMvc.perform(get("/api/admin/article/delete")
						.header("Authorization", ConstUtils.TEST_ADMIN_JWT_TOKEN)
						.param("articleId", "2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}
}
