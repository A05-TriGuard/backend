package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Guard;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.vo.response.Guard.WardActivityVO;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class GuardServiceImplTest {
	@Autowired
	private GuardServiceImpl guardServiceImpl;

	@Test
	public void getGuardianList() {
		Integer wardId = 2;
		List<Guard> actual = guardServiceImpl.getGuardianList(wardId);
		assertNotNull(actual);
	}

	@Test
	public void deleteGuardian() {
		Integer wardId = 2;
		Integer guardianId= 3;
		String actual = guardServiceImpl.deleteGuardian(wardId, guardianId);
		assertNull(actual);
	}

	@Test
	public void setGuardianNickname() {
		Integer wardId = 2;
		Integer guardianId = 3;
		String nickname = "nickname";
		String actual = guardServiceImpl.setGuardianNickname(wardId, guardianId, nickname);
		assertNull(actual);
	}

	@Test
	public void inviteGuardian() {
		Integer wardId = 2;
		String email = "email";
		String actual = guardServiceImpl.inviteGuardian(wardId, email);
		assertEquals("邮箱错误，用户不存在", actual);
		email = "admin@qq.com";
		actual = guardServiceImpl.inviteGuardian(wardId, email);
		assertEquals("已经添加过该监护人", actual);
		email = "yuc21@mails.tsinghua.edu.cn";
		actual = guardServiceImpl.inviteGuardian(wardId, email);
		assertNull(actual);
	}

	@Test
	public void getWardList() {
		Integer guardianId = 3;
		List<Guard> actual = guardServiceImpl.getWardList(guardianId);
		assertNotNull(actual);
	}

	@Test
	public void getWardInvitationList() {
		Integer guardianId = 2;
		List<Guard> actual = guardServiceImpl.getWardInvitationList(guardianId);
		assertNotNull(actual);
	}

	@Test
	public void getWardActivity() {
		Integer guardianId = 3;
		Integer wardId = 2;
		WardActivityVO actual = guardServiceImpl.getWardActivity(guardianId, wardId);
		assertNotNull(actual);
	}

	@Test
	public void deleteWard() {
		Integer guardianId = 3;
		Integer wardId = 2;
		String actual = guardServiceImpl.deleteWard(guardianId, wardId);
		assertNull(actual);
	}

	@Test
	public void acceptWardInvitation() {
		Integer guardianId = 3;
		Integer invitationId = 2;
		Boolean isAccepted = true;
		String actual = guardServiceImpl.acceptWardInvitation(guardianId, invitationId, isAccepted);
		assertNull(actual);
		Boolean isAccepted2 = false;
		actual = guardServiceImpl.acceptWardInvitation(guardianId, invitationId, isAccepted2);
		assertNull(actual);
	}

	@Test
	public void setWardNickname() {
		Integer guardianId = 3;
		Integer wardId = 2;
		String nickname = "abc";
		String actual = guardServiceImpl.setWardNickname(guardianId, wardId, nickname);
		assertNull(actual);
	}
}
