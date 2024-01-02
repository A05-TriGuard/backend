package com.triguard.backend.service.Impl;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.dto.GuardGroupMember;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuardGroupMemberServiceImplTest {
	@Autowired
	private GuardGroupMemberServiceImpl guardGroupMemberServiceImpl;

	@Test
	public void getGroupIdList() {
		Integer guardianId = 1;
		List<Integer> actual = guardGroupMemberServiceImpl.getGroupIdList(guardianId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void addMemberList() {
		Integer groupId = 2;
		Integer guardianId = 3;
		List<Integer> wardIdList = new ArrayList<>();
		wardIdList.add(2);
		String actual = guardGroupMemberServiceImpl.addMemberList(groupId, guardianId, wardIdList);
        assertNull(actual);
	}

	@Test
	public void addWardMemberList() {
		Integer groupId = 2;
		Integer guardianId = 3;
		List<Integer> wardIdList = new ArrayList<>();
		wardIdList.add(2);
		String actual = guardGroupMemberServiceImpl.addWardMemberList(groupId, guardianId, wardIdList);
		assertNull(actual);
	}

	@Test
	public void getMemberList() {
		Integer groupId = 4;
		List<GuardGroupMember> actual = guardGroupMemberServiceImpl.getMemberList(groupId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void deleteMember() {
		Integer groupId = 4;
		Integer accountId = 2;
		String actual = guardGroupMemberServiceImpl.deleteMember(groupId, accountId);
		assertNull(actual);
	}

	@Test
	@Order(Integer.MAX_VALUE)
	public void deleteByGroupId() {
		Integer groupId = 4;
		guardGroupMemberServiceImpl.deleteByGroupId(groupId);
	}
}
