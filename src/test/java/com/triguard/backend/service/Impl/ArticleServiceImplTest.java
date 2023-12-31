package com.triguard.backend.service.Impl;

import com.triguard.backend.entity.dto.Article;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ArticleServiceImplTest {
	@Autowired
	private ArticleServiceImpl articleServiceImpl;

	@Test
	public void getDiseaseArticle() {
		Integer page = 1;
		Integer size = 10;
		List<Article> actual = articleServiceImpl.getDiseaseArticle(page, size);
		System.out.println(actual);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void getScienceArticle() {
		Integer page = 1;
		Integer size = 10;
		List<Article> actual = articleServiceImpl.getScienceArticle(page, size);
		System.out.println(actual);
		assertNotNull(actual);
		assertNotEquals(0, actual.size());
	}

	@Test
	public void createArticle() {
		Article article = new Article();
		article.setType("disease");
		article.setTitle("title");
		article.setSubtitle("subtitle");
		article.setCover("cover");
		article.setContent("content");
		Article actual = articleServiceImpl.createArticle(article);
		assertNotNull(actual);
	}
}
