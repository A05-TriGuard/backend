package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.GuardGroup;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.search.FromTerm;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.vo.response.Guard.GuardGroupActivityVO;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuardGroupServiceImplTest {
	@Autowired
	private GuardGroupServiceImpl guardGroupServiceImpl;

	@Test
	public void getGuardGroupList() {
		Integer guardianId = 1;
		List<GuardGroup> actual = guardGroupServiceImpl.getGuardGroupList(guardianId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
		Integer guardianId1 = 2;
		List<GuardGroup> actual1 = guardGroupServiceImpl.getGuardGroupList(guardianId1);
		assertNotNull(actual1);
		assertEquals(0, actual1.size());
	}

	@Test
	public void createGuardGroup() {
		Integer guardianId = 3;
		String groupName = "abc";
		List<Integer> wardIdList = new ArrayList<>();
		wardIdList.add(2);
		String actual = guardGroupServiceImpl.createGuardGroup(guardianId, groupName, wardIdList);
		assertNull(actual);
	}

	@Test
	public void addGuardGroupMember() {
		Integer groupId = 4;
		Integer guardianId = 3;
		List<Integer> wardIdList = new ArrayList<>();
		wardIdList.add(2);
		String actual = guardGroupServiceImpl.addGuardGroupMember(groupId, guardianId, wardIdList);
		assertNull(actual);
	}

	@Test
	public void getGuardGroupActivity() {
		Integer groupId = 4;
		Integer guardianId = 1;
		GuardGroupActivityVO actual = guardGroupServiceImpl.getGuardGroupActivity(groupId, guardianId);
		assertNotNull(actual);
	}

	@Test
	@Order(Integer.MAX_VALUE - 1)
	public void deleteGuardGroupMember() {
		Integer groupId = 4;
		Integer wardId = 2;
		String actual = guardGroupServiceImpl.deleteGuardGroupMember(groupId, wardId);
		assertNull(actual);
		groupId = 2;
		String actual1 = guardGroupServiceImpl.deleteGuardGroupMember(groupId, wardId);
		assertNotNull(actual1);
	}

	@Test
	@Order(Integer.MAX_VALUE)
	public void deleteGuardGroup() {
		Integer groupId = 4;
		String actual = guardGroupServiceImpl.deleteGuardGroup(groupId);
		assertNull(actual);
	}
}
