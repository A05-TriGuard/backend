package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Article;
import com.triguard.backend.entity.dto.ArticleFavorites;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
@Transactional
public class ArticleFavoritesServiceImplTest {
	@Autowired
	private ArticleFavoritesServiceImpl articleFavoritesServiceImpl;

	@Test
	public void addArticleFavorites() {
		Integer accountId = 2;
		Integer articleId = 23;
		ArticleFavorites actual = articleFavoritesServiceImpl.addArticleFavorites(accountId, articleId);
		assertEquals(accountId, actual.getAccountId());
		assertEquals(articleId, actual.getArticleId());
		assertNotNull(actual.getArticleType());
	}

	@Test
	public void getDiseaseArticleFavorites() {
		Integer accountId = 2;
		Integer page = 1;
		Integer size = 10;
		List<ArticleFavorites> actual = articleFavoritesServiceImpl.getDiseaseArticleFavorites(accountId, page, size);
		assertNotNull(actual);
	}

	@Test
	public void getScienceArticleFavorites() {
		Integer accountId = 2;
		Integer page = 1;
		Integer size = 10;
		List<ArticleFavorites> actual = articleFavoritesServiceImpl.getScienceArticleFavorites(accountId, page, size);
		assertNotNull(actual);
	}

	@Test
	public void getArticleFavorites() {
		Integer accountId = 2;
		Integer articleId = 1;
		ArticleFavorites actual = articleFavoritesServiceImpl.getArticleFavorites(accountId, articleId);
		assertEquals(accountId, actual.getAccountId());
		assertEquals(articleId, actual.getArticleId());
		assertNotNull(actual.getArticleType());
	}

	@Test
	public void deleteArticleFavorites() {
		List<ArticleFavorites> articleFavoritesList = articleFavoritesServiceImpl.list();
		Integer accountId = articleFavoritesList.get(0).getAccountId();
		Integer articleId = articleFavoritesList.get(0).getArticleId();
		boolean actual = articleFavoritesServiceImpl.deleteArticleFavorites(accountId, articleId);
		assertTrue(actual);
	}
}
