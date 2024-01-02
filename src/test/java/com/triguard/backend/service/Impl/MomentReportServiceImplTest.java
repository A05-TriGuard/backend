package com.triguard.backend.service.Impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MomentReportServiceImplTest {
	@Autowired
	private MomentReportServiceImpl momentReportServiceImpl;

	@Test
	public void saveMomentReport() {
		Integer accountId = 2;
		Integer momentId = 4;
		String reason = "abc";
		Boolean actual = momentReportServiceImpl.saveMomentReport(accountId, momentId, reason);
		assertTrue(actual);
	}
}
