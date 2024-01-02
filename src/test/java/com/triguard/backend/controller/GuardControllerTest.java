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
public class GuardControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getGuardianList() throws Exception {
		this.mockMvc.perform(get("/api/guardian/list")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteGuardian() throws Exception {
		this.mockMvc.perform(get("/api/guardian/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("id", "1"))
					.andExpect(status().is4xxClientError());
	}

	@Test
	public void deleteGuardian1() throws Exception {
		this.mockMvc.perform(get("/api/guardian/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("id", "3"))
					.andExpect(status().is4xxClientError());
	}

	@Test
	public void setGuardianNickname() throws Exception {
		this.mockMvc.perform(post("/api/guardian/set-nickname")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("guardianId", "3")
					.param("nickname", "test"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void setGuardianNickname1() throws Exception {
		this.mockMvc.perform(post("/api/guardian/set-nickname")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("guardianId", "1")
					.param("nickname", "test"))
					.andExpect(status().isOk());
	}

	@Test
	public void inviteGuardian() throws Exception {
		this.mockMvc.perform(post("/api/guardian/invite")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("email", "yuc21@mails.tsinghua.edu.cn"))
					.andExpect(status().isOk());
	}

	@Test
	public void getWardList() throws Exception {
		this.mockMvc.perform(get("/api/ward/list")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void getWardInvitationList() throws Exception {
		this.mockMvc.perform(get("/api/ward/invitation/list")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void acceptWardInvitation() throws Exception {
		this.mockMvc.perform(post("/api/ward/invitation/accept")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("invitationId", "17")
					.param("isAccept", "true"))
					.andExpect(status().is4xxClientError());
	}

	@Test
	public void getWardActivity() throws Exception {
		this.mockMvc.perform(get("/api/ward/activity")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("wardId", "3"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void deleteWard() throws Exception {
		this.mockMvc.perform(get("/api/ward/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("wardId", "3"))
					.andExpect(status().isOk());
	}

	@Test
	public void setWardNickname() throws Exception {
		this.mockMvc.perform(post("/api/ward/set-nickname")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("wardId", "3")
					.param("nickname", "test"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void createGuardGroup() throws Exception {
		this.mockMvc.perform(post("/api/guard-group/create")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("groupName", "test")
					.param("wardIds", "3,"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	public void getGuardGroupActivity() throws Exception {
		this.mockMvc.perform(get("/api/guard-group/activity")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("groupId", "2"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	public void addGuardGroupMember() throws Exception {
		this.mockMvc.perform(post("/api/guard-group/member/add")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("groupId", "2")
					.param("wardIds", "3,"))
					.andExpect(status().isOk());
	}

	@Test
	public void disbandGuardGroup() throws Exception {
		this.mockMvc.perform(get("/api/guard-group/disband")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("groupId", "10"))
					.andExpect(status().isOk());
	}

	@Test
	public void deleteGuardGroupMember() throws Exception {
		this.mockMvc.perform(get("/api/guard-group/member/delete")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("groupId", "2")
					.param("wardId", "3"))
					.andExpect(status().isOk());
	}

	@Test
	public void setGroupName() throws Exception {
		this.mockMvc.perform(post("/api/guard-group/set-name")
					.header("Authorization", ConstUtils.TEST_JWT_TOKEN)
					.param("groupId", "2")
					.param("groupName", "test"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.data").isEmpty());
	}
}
