package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.service.UserService;

/**
 * テストクラス kanggo UserController.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    /**
     * セットアップメソッド kanggo inisialisasi objek sing diperlokaké lan konfigurasi mock.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    /**
     * テストメソッド kanggo displayAdd() saka UserController.
     */
    @Test
    public void testDisplayAdd() throws Exception {
        mockMvc.perform(get("/user/add"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("userRequest"))
            .andExpect(view().name("user/add"));
    }
}
