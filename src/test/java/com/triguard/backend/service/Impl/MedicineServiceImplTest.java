package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Medicine;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MedicineServiceImplTest {
	@Autowired
	private MedicineServiceImpl medicineServiceImpl;

	@Test
	public void searchMedicine() {
		String keyword = "测试药品";
		List<Medicine> actual = medicineServiceImpl.searchMedicine(keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}
}
