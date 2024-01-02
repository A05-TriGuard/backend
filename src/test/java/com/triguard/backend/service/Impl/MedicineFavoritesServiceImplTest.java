package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.MedicineFavorites;
import jakarta.mail.search.FromTerm;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicineFavoritesServiceImplTest {
	@Autowired
	private MedicineFavoritesServiceImpl medicineFavoritesServiceImpl;

	@Test
	@Order(1)
	public void addMedicineFavorites() {
		Integer accountId = 2;
		Integer medicineId = 1;
		MedicineFavorites actual = medicineFavoritesServiceImpl.addMedicineFavorites(accountId, medicineId);
		assertNotNull(actual);
	}

	@Test
	public void getMedicineFavorites() {
		Integer accountId = 2;
		List<MedicineFavorites> actual = medicineFavoritesServiceImpl.getMedicineFavorites(accountId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	@Order(2)
	public void getMedicineFavoritesByMedicineId() {
		Integer accountId = 2;
		Integer medicineId = 2;
		MedicineFavorites actual = medicineFavoritesServiceImpl.getMedicineFavorites(accountId, medicineId);
		assertNotNull(actual);
	}

	@Test
	@Order(3)
	public void deleteMedicineFavorites() {
		Integer accountId = 2;
		Integer medicineId = 2;
		boolean actual = medicineFavoritesServiceImpl.deleteMedicineFavorites(accountId, medicineId);
		assertTrue(actual);
	}
}
