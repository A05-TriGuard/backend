package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.BloodSugar;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarCreateVO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarUpdateVO;
import java.util.List;
import java.util.ArrayList;
import com.triguard.backend.entity.vo.request.BloodSugar.BloodSugarFilterVO;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BloodSugarServiceImplTest {
	@Autowired
	private BloodSugarServiceImpl bloodSugarServiceImpl;

	@Test
	public void createBloodSugar() {
		Integer accountId = 2;
		BloodSugarCreateVO vo = new BloodSugarCreateVO();
		vo.setBs(1.0f);
		vo.setMeal(1);
		vo.setFeeling(1);
		vo.setRemark("abc");
		vo.setDate("2021-01-01");
		vo.setTime("12:00");
		BloodSugar actual = bloodSugarServiceImpl.createBloodSugar(accountId, vo);
		assertNotNull(actual.getId());
	}

	@Test
	public void deleteBloodSugar() {
		Integer id = 1;
		String actual = bloodSugarServiceImpl.deleteBloodSugar(id);
        assertNull(actual);
	}

	@Test
	public void updateBloodSugar() {
		BloodSugarUpdateVO vo = new BloodSugarUpdateVO();
		vo.setId(2);
		vo.setRemark("update");
		String actual = bloodSugarServiceImpl.updateBloodSugar(vo);
		assertNull(actual);
	}

	@Test
	public void getBloodSugar() {
		Integer accountId = 2;
		String date = "2023-11-29";
		List<BloodSugar> actual = bloodSugarServiceImpl.getBloodSugar(accountId, date);
		assertNotEquals(0, actual.size());
		String startDate = "2023-11-29";
		String endDate = "2023-11-30";
		List<BloodSugar> actual2 = bloodSugarServiceImpl.getBloodSugar(accountId, startDate, endDate);
		assertNotEquals(0, actual2.size());
		BloodSugarFilterVO vo = new BloodSugarFilterVO();
		vo.setStartDate("2023-11-29");
		vo.setEndDate("2023-11-30");
		List<BloodSugar> actual3 = bloodSugarServiceImpl.getBloodSugar(accountId, vo);
		assertNotEquals(0, actual3.size());
		vo.setMeal("000");
		vo.setFeeling("000");
		vo.setRemark("00");
		List<BloodSugar> actual4 = bloodSugarServiceImpl.getBloodSugar(accountId, vo);
		assertEquals(0, actual4.size());
	}
}
