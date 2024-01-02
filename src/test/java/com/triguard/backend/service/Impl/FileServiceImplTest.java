package com.triguard.backend.service.Impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class FileServiceImplTest {
	@Autowired
	private FileServiceImpl fileServiceImpl;

	@MockBean
	MultipartFile file;

	@Test
	public void uploadMultipartFile() {
		when(file.isEmpty()).thenReturn(true);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			fileServiceImpl.uploadMultipartFile(file);
		});

		String expectedMessage = "文件为空";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void uploadNotEmptyMultipartFile() throws IOException {
		when(file.isEmpty()).thenReturn(false);
		when(file.getOriginalFilename()).thenReturn("test.txt");

		doNothing().when(file).transferTo(any(File.class));

		String result = fileServiceImpl.uploadMultipartFile(file);

		assertNotNull(result);
	}
}
