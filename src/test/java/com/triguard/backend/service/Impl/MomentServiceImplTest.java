package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Moment;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.triguard.backend.entity.dto.MomentComment;
import com.triguard.backend.entity.vo.response.Moment.MomentCommentCreateVO;

@SpringBootTest
@Transactional
public class MomentServiceImplTest {
	@Autowired
	private MomentServiceImpl momentServiceImpl;

	@Test
	public void getMomentListByTime() {
		String classification = "高血压";
		String category = "饮食";
		Integer page = 1;
		Integer size = 10;
		String keyword = "吃什么";
		List<Moment> actual = momentServiceImpl.getMomentListByTime(classification, category, page, size, keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getMomentListByHot() {
		String classification = "高血压";
		String category = "饮食";
		Integer page = 1;
		Integer size = 10;
		String keyword = "吃什么";
		List<Moment> actual = momentServiceImpl.getMomentListByHot(classification, category, page, size, keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getMomentListByAccount() {
		Integer accountId = 2;
		String classification = "高血压";
		String category = "饮食";
		Integer page = 1;
		Integer size = 10;
		String keyword = "吃什么";
		List<Moment> actual = momentServiceImpl.getMomentListByAccount(accountId, classification, category, page, size, keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getMomentListByLike() {
		Integer accountId = 2;
		String classification = "高血压";
		String category = "饮食";
		Integer page = 1;
		Integer size = 10;
		String keyword = "吃什么";
		List<Moment> actual = momentServiceImpl.getMomentListByLike(accountId, classification, category, page, size, keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getMomentListByFavorite() {
		Integer accountId = 2;
		String classification = "高血压";
		String category = "饮食";
		Integer page = 1;
		Integer size = 10;
		String keyword = "吃什么";
		List<Moment> actual = momentServiceImpl.getMomentListByFavorite(accountId, classification, category, page, size, keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getMomentListByFollow() {
		Integer accountId = 2;
		String classification = "高血压";
		String category = "饮食";
		Integer page = 1;
		Integer size = 10;
		String keyword = "吃什么";
		List<Moment> actual = momentServiceImpl.getMomentListByFollow(accountId, classification, category, page, size, keyword);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void isLike() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentServiceImpl.isLike(accountId, momentId);
		assertFalse(actual);
	}

	@Test
	public void isFavorite() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentServiceImpl.isFavorite(accountId, momentId);
		assertFalse(actual);
	}

	@Test
	public void isFollow() {
		Integer accountId = 2;
		Integer momentAccountId = 5;
		Boolean actual = momentServiceImpl.isFollow(accountId, momentAccountId);
		assertFalse(actual);
	}

	@Test
	public void likeMoment() {
		Integer accountId = 2;
		Integer momentId = 4;
		Boolean actual = momentServiceImpl.likeMoment(accountId, momentId);
		assertTrue(actual);
	}

	@Test
	public void unlikeMoment() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentServiceImpl.unlikeMoment(accountId, momentId);
		assertFalse(actual);
	}

	@Test
	public void favoriteMoment() {
		Integer accountId = 2;
		Integer momentId = 4;
		Boolean actual = momentServiceImpl.favoriteMoment(accountId, momentId);
		assertTrue(actual);
	}

	@Test
	public void unfavoriteMoment() {
		Integer accountId = 2;
		Integer momentId = 2;
		Boolean actual = momentServiceImpl.unfavoriteMoment(accountId, momentId);
		assertFalse(actual);
	}

	@Test
	public void followMomentAccount() {
		Integer accountId = 2;
		Integer momentAccountId = 3;
		Boolean actual = momentServiceImpl.followMomentAccount(accountId, momentAccountId);
		assertTrue(actual);
	}

	@Test
	public void unfollowMomentAccount() {
		Integer accountId = 2;
		Integer momentAccountId = 5;
		Boolean actual = momentServiceImpl.unfollowMomentAccount(accountId, momentAccountId);
		assertFalse(actual);
	}

	@Test
	public void reportMoment() {
		Integer accountId = 2;
		Integer momentId = 4;
		String reason = "abc";
		Boolean actual = momentServiceImpl.reportMoment(accountId, momentId, reason);
		assertTrue(actual);
	}

	@Test
	public void createMoment() {
		Integer accountId = 3;
		String content = "abc";
		String classification = "abc";
		String category = "abc";
		Moment actual = momentServiceImpl.createMoment(accountId, content, classification, category, null, null);
		assertNotNull(actual);
		assertNotNull(actual.getId());
	}

	@Test
	public void deleteMoment() {
		Integer accountId = 3;
		Integer momentId = 4;
		boolean actual = momentServiceImpl.deleteMoment(accountId, momentId);
		assertTrue(actual);
	}

	@Test
	public void commentMoment() {
		Integer accountId = 3;
		Integer momentId = 4;
		String content = "abc";
		MomentComment actual = momentServiceImpl.commentMoment(accountId, momentId, content, null);
		assertNotNull(actual);
		assertNotNull(actual.getId());
	}

	@Test
	public void getCommentById() {
		Integer commentId = 3;
		MomentComment actual = momentServiceImpl.getCommentById(commentId);
		assertNotNull(actual);
		assertEquals(commentId, actual.getId());
	}

	@Test
	public void getCommentInfo() {
		MomentComment momentComment = momentServiceImpl.getCommentById(3);
		MomentCommentCreateVO actual = momentServiceImpl.getCommentInfo(momentComment);
		assertNotNull(actual);
		assertEquals(momentComment.getId(), actual.getId());
	}

	@Test
	public void getCommentList() {
		Integer momentId = 2;
		List<MomentComment> actual = momentServiceImpl.getCommentList(momentId);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}
}
