package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.BloodLipids;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsCreateVO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsUpdateVO;
import java.util.List;
import java.util.ArrayList;
import com.triguard.backend.entity.vo.request.BloodLipids.BloodLipidsFilterVO;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BloodLipidsServiceImplTest {
	@Autowired
	private BloodLipidsServiceImpl bloodLipidsServiceImpl;

	@Test
	public void createBloodLipids() {
		Integer accountId = 2;
		BloodLipidsCreateVO vo = new BloodLipidsCreateVO();
		vo.setTc(1.0f);
		vo.setTg(1.0f);
		vo.setHdl(1.0f);
		vo.setLdl(1.0f);
		vo.setFeeling(1);
		vo.setRemark("abc");
		vo.setDate("2021-01-01");
		vo.setTime("01:01");
		BloodLipids actual = bloodLipidsServiceImpl.createBloodLipids(accountId, vo);
		assertNotNull(actual);
		assertEquals(2, actual.getAccountId());
		assertEquals(1.0f, actual.getTc());
		assertEquals(1.0f, actual.getTg());
		assertEquals(1.0f, actual.getHdl());
		assertEquals(1.0f, actual.getLdl());
		assertEquals(1, actual.getFeeling());
		assertEquals("abc", actual.getRemark());
		assertEquals("2021-01-01", actual.getDate());
		assertEquals("01:01", actual.getTime());
	}

	@Test
	public void deleteBloodLipids() {
		Integer id = 5;
		String actual = bloodLipidsServiceImpl.deleteBloodLipids(id);
		assertNull(actual);
	}

	@Test
	public void updateBloodLipids() {
		BloodLipidsUpdateVO vo = new BloodLipidsUpdateVO();
		vo.setId(5);
		vo.setRemark("test");
		String actual = bloodLipidsServiceImpl.updateBloodLipids(vo);
		assertNull(actual);
	}

	@Test
	public void getBloodLipids() {
		Integer accountId = 2;
		String date = "2021-01-01";
		List<BloodLipids> actual = bloodLipidsServiceImpl.getBloodLipids(accountId, date);
		assertNotNull(actual);
		String startDate = "2021-01-01";
		String endDate = "2021-01-02";
		List<BloodLipids> actual2 = bloodLipidsServiceImpl.getBloodLipids(accountId, startDate, endDate);
		assertNotNull(actual2);
		BloodLipidsFilterVO vo = new BloodLipidsFilterVO();
		vo.setAccountId(2);
		vo.setStartDate("2021-01-01");
		vo.setEndDate("2021-01-02");
		vo.setFeeling("000");
		vo.setRemark("00");
		assertEquals(0, bloodLipidsServiceImpl.getBloodLipids(accountId , vo).size());
	}
}
