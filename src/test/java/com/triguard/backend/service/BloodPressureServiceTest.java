package com.triguard.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import com.triguard.backend.entity.dto.BloodPressure;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureCreateVO;
import com.triguard.backend.entity.vo.request.BloodPressure.BloodPressureUpdateVO;
import com.triguard.backend.service.Impl.BloodPressureServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BloodPressureServiceTest {

    @Autowired
    private BloodPressureServiceImpl bloodPressureService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createBloodPressure() {
        Integer accountId = 1;
        BloodPressureCreateVO vo = new BloodPressureCreateVO();
        vo.setDbp(80);
        vo.setSbp(120);
        vo.setHeartRate(80);
        vo.setArm(0);
        vo.setDate("2023-11-22");
        vo.setTime("12:12");
        BloodPressure bloodPressure = bloodPressureService.createBloodPressure(accountId, vo);
        assertNotNull(bloodPressure);
        assertEquals(bloodPressure.getAccountId(), accountId);
        assertEquals(bloodPressure.getDbp(), vo.getDbp());
        assertEquals(bloodPressure.getSbp(), vo.getSbp());
        assertEquals(bloodPressure.getHeartRate(), vo.getHeartRate());
        assertEquals(bloodPressure.getArm(), vo.getArm());
        assertEquals(bloodPressure.getDate(), vo.getDate());
        assertEquals(bloodPressure.getTime(), vo.getTime());
    }

    @Test
    void deleteBloodPressure() {
        Integer id = 1;
        BloodPressure bloodPressure = bloodPressureService.getById(id);
        assertNotNull(bloodPressure);
        String result = bloodPressureService.deleteBloodPressure(id);
        assertNull(result);
        BloodPressure bloodPressure1 = bloodPressureService.getById(id);
        assertNull(bloodPressure1);
    }

    @Test
    void updateBloodPressure() {
        Integer id = 1;
        BloodPressure bloodPressure = bloodPressureService.getById(id);
        assertNotNull(bloodPressure);
        BloodPressureUpdateVO vo = new BloodPressureUpdateVO();
        vo.setId(id);
        vo.setDbp(bloodPressure.getDbp() + 1);
        vo.setSbp(bloodPressure.getSbp() + 1);
        String result = bloodPressureService.updateBloodPressure(vo);
        assertNull(result);
        BloodPressure bloodPressure1 = bloodPressureService.getById(id);
        assertNotNull(bloodPressure1);
        assertEquals(bloodPressure1.getDbp(), vo.getDbp());
        assertEquals(bloodPressure1.getSbp(), vo.getSbp());
    }

    @Test
    void getBloodPressure() {
    }

    @Test
    void testGetBloodPressure() {
    }
}