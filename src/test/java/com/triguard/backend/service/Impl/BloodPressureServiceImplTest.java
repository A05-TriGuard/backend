package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureCreateVO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureUpdateVO;
import java.util.List;
import java.util.ArrayList;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureFilterVO;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BloodPressureServiceImplTest {
	@Autowired
	private BloodPressureServiceImpl bloodPressureServiceImpl;

	@Test
	public void createBloodPressure() {
		Integer accountId = 2;
		BloodPressureCreateVO vo = new BloodPressureCreateVO();
		vo.setSbp(50);
		vo.setDbp(100);
		vo.setHeartRate(60);
		vo.setArm(0);
		vo.setFeeling(0);
		vo.setRemark("remark");
		vo.setDate("2021-06-01");
		vo.setTime("12:00");
		BloodPressure actual = bloodPressureServiceImpl.createBloodPressure(accountId, vo);
		assertNotNull(actual);
		assertNotNull(actual.getId());
	}

	@Test
	public void deleteBloodPressure() {
		Integer id = 21;
		String actual = bloodPressureServiceImpl.deleteBloodPressure(id);
		assertNull(actual);
	}

	@Test
	public void updateBloodPressure() {
		BloodPressureUpdateVO vo = new BloodPressureUpdateVO();
		vo.setId(20);
		vo.setRemark("remark");
		String actual = bloodPressureServiceImpl.updateBloodPressure(vo);
		assertNull(actual);
	}

	@Test
	public void getBloodPressure() {
		Integer accountId = 2;
		String date = "2021-06-01";
		List<BloodPressure> actual = bloodPressureServiceImpl.getBloodPressure(accountId, date);
		assertNotNull(actual);
		String startDate = "2021-06-01";
		String endDate = "2021-06-02";
		List<BloodPressure> actual2 = bloodPressureServiceImpl.getBloodPressure(accountId, startDate, endDate);
		assertNotNull(actual2);
		BloodPressureFilterVO vo = new BloodPressureFilterVO();
		vo.setAccountId(2);
		vo.setStartDate("2021-06-01");
		vo.setEndDate("2021-06-02");
		vo.setArm("000");
		vo.setFeeling("000");
		vo.setRemark("00");
		List<BloodPressure> actual3 = bloodPressureServiceImpl.getBloodPressure(accountId, vo);
		assertEquals(0, actual3.size());
	}
}
