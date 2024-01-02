package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.BodyIndex;
import com.triguard.backend.entity.vo.request.BodyIndex.BodyIndexSetVO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BodyIndexServiceImplTest {

	@Autowired
	private BodyIndexServiceImpl bodyIndexServiceImpl;

	@Test
	public void setBodyIndex() {
		Integer accountId = 2;
		BodyIndexSetVO bodyIndexSetVO = new BodyIndexSetVO();
		bodyIndexSetVO.setSex("男");
		bodyIndexSetVO.setAge(20);
		bodyIndexSetVO.setHeight(180);
		bodyIndexSetVO.setWeight(70);
		bodyIndexSetVO.setLevel(1);
		BodyIndex actual = bodyIndexServiceImpl.setBodyIndex(accountId, bodyIndexSetVO);
		assertNotNull(actual);
		assertEquals(2, actual.getAccountId());
	}

	@Test
	public void setEmptyBodyIndex() {
		Integer accountId = 4;
		BodyIndexSetVO bodyIndexSetVO = new BodyIndexSetVO();
		bodyIndexSetVO.setSex("男");
		bodyIndexSetVO.setAge(20);
		bodyIndexSetVO.setHeight(180);
		bodyIndexSetVO.setWeight(70);
		bodyIndexSetVO.setLevel(1);
		BodyIndex actual = bodyIndexServiceImpl.setBodyIndex(accountId, bodyIndexSetVO);
		assertNotNull(actual);
		assertEquals(4, actual.getAccountId());
	}
}
