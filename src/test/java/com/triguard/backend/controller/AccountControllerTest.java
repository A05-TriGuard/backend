package com.triguard.backend.controller;

import com.triguard.backend.utils.ConstUtils;
import com.triguard.backend.utils.JwtUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import com.triguard.backend.entity.RestBean;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAccountInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/info")
                .header("Authorization", ConstUtils.TEST_JWT_TOKEN)
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void setProfile() throws Exception {
        MockMultipartFile profile = new MockMultipartFile("profile", "filename.jpg", "image/jpeg", "<<jpeg data>>".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/account/set-profile")
                .file(profile)
                .header("Authorization", ConstUtils.TEST_JWT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }
}
