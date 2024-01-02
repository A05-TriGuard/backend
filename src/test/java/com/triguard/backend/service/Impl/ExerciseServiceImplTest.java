package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Exercise;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.triguard.backend.entity.vo.response.Sports.ExerciseInfoVO;
import com.triguard.backend.entity.vo.request.Sports.ExerciseFilterVO;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ExerciseServiceImplTest {
	@Autowired
	private ExerciseServiceImpl exerciseServiceImpl;

	@Test
	public void getExerciseByDate() {
		Integer accountId = 2;
		String date = "2023-12-21";
		List<Exercise> actual = exerciseServiceImpl.getExerciseByDate(accountId, date);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getExerciseByDateRange() {
		Integer accountId = 2;
		String startDate = "2023-12-21";
		String endDate = "2023-12-22";
		List<Exercise> actual = exerciseServiceImpl.getExerciseByDateRange(accountId, startDate, endDate);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getExerciseList() {
		Integer accountId = 2;
		List<Exercise> actual = exerciseServiceImpl.getExerciseList(accountId);
		assertEquals(0, actual.size());
	}

	@Test
	public void getCurrentExercise() {
		Integer accountId = 2;
		Exercise actual = exerciseServiceImpl.getCurrentExercise(accountId);
		assertNotNull(actual);
	}

	@Test
	public void filterExercise() {
		Integer accountId = 2;
		List<ExerciseInfoVO> input = new ArrayList<ExerciseInfoVO>(
				List.of(new ExerciseInfoVO(1, "2023-12-21 15:30", "2023-12-21 16:30", 60, 0, 0, "Remark"),
						new ExerciseInfoVO(2, "2023-12-20 10:00", "2023-12-20 12:00", 120, 2, 1, null)));
		ExerciseFilterVO filter = new ExerciseFilterVO();
		filter.setStartTime("15:00");
		filter.setEndTime("17:00");
		List<ExerciseInfoVO> actual = exerciseServiceImpl.filterExercise(input, filter);
		assertEquals(1, actual.size());
		filter.setStartTime("00:00");
		filter.setEndTime("23:59");
		actual = exerciseServiceImpl.filterExercise(input, filter);
		assertEquals(2, actual.size());
		filter.setMinDuration(300);
		filter.setFeelings("000");
		filter.setRemark("00");
		actual = exerciseServiceImpl.filterExercise(input, filter);
		assertEquals(0, actual.size());
	}
}
