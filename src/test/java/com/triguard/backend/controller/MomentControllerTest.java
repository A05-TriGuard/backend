package com.triguard.backend.controller;

import com.triguard.backend.utils.ConstUtils;
import org.junit.After;
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
public class MomentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getMomentList() throws Exception {
		this.mockMvc.perform(get("/api/moment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
				.param("category", "饮食")
				.param("class", "高血压")
				.param("filter", "最受欢迎"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getMomentList1() throws Exception {
		this.mockMvc.perform(get("/api/moment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("category", "饮食")
						.param("class", "高血压")
						.param("filter", "我的帖子"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getMomentList2() throws Exception {
		this.mockMvc.perform(get("/api/moment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("category", "饮食")
						.param("class", "高血压")
						.param("filter", "我的点赞"))
				.andExpect(status().isOk());
	}

	@Test
	public void getMomentList3() throws Exception {
		this.mockMvc.perform(get("/api/moment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("category", "饮食")
						.param("class", "高血压")
						.param("filter", "我的收藏"))
				.andExpect(status().isOk());
	}

	@Test
	public void getMomentList4() throws Exception {
		this.mockMvc.perform(get("/api/moment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("category", "饮食")
						.param("class", "高血压")
						.param("filter", "我的关注"))
				.andExpect(status().isOk());
	}

	@Test
	public void getMomentList5() throws Exception {
		this.mockMvc.perform(get("/api/moment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("category", "饮食")
						.param("class", "高血压")
						.param("filter", "最新发布"))
				.andExpect(status().isOk());
	}

	@Test
	public void likeMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/like")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "8"))
				.andExpect(status().isOk());
	}

	@Test
	public void unlikeMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/unlike")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "4"))
				.andExpect(status().isOk());
	}

	@Test
	public void favoriteMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/favorite")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "8"))
				.andExpect(status().isOk());
	}

	@Test
	public void unfavoriteMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/unfavorite")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "4"))
				.andExpect(status().isOk());
	}

	@Test
	public void followMomentAccount() throws Exception {
		this.mockMvc.perform(post("/api/moment/follow")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentAccountId", "3"))
				.andExpect(status().isOk());
	}

	@Test
	public void unfollowMomentAccount() throws Exception {
		this.mockMvc.perform(post("/api/moment/unfollow")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentAccountId", "3"))
				.andExpect(status().isOk());
	}

	@Test
	public void reportMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/report")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "8")
						.param("reason", "广告"))
				.andExpect(status().isOk());
	}

	@Test
	public void createMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/create")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("category", "饮食")
						.param("class", "高血压")
						.param("content", "测试帖子内容"))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteMoment() throws Exception {
		this.mockMvc.perform(get("/api/moment/delete")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "8"))
				.andExpect(status().isOk());
	}

	@Test
	public void commentMoment() throws Exception {
		this.mockMvc.perform(post("/api/moment/comment")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "8")
						.param("content", "测试评论内容"))
				.andExpect(status().isOk());
	}

	@Test
	public void getCommentList() throws Exception {
		this.mockMvc.perform(get("/api/moment/comment/list")
						.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
						.param("momentId", "8"))
				.andExpect(status().isOk());
	}
}
