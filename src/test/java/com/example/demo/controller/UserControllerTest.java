package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.UserRequest;
import com.example.demo.service.UserService;

/**
 * テストクラス kanggo UserController.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
    @MockBean
    UserService userService;
	
    @Autowired
    private UserController userController;

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
    
    /**
     * テストメソッド  userCreate() 正常系テスト
     */
    @Test
    public void testCreateUserSucess() throws Exception {
    	UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-1234-5678");
    	
    	mockMvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    		.andExpect(model().hasNoErrors())
    		.andExpect(model().attribute("userRequest", testRequest))
    		.andExpect(view().name("redirect:/user/list"));
    	
    	verify(userService, times(1)).create(testRequest);
    }
    
    /**
     *  テストメソッド userCreate() 異常系テスト
     */
    @Test
    public void testCreateUserError() throws Exception {
    	UserRequest testRequest = new UserRequest();
    	testRequest.setName("");
    	testRequest.setAddress("");
    	testRequest.setPhone("");
    	
    	mockMvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    		.andExpect(model().hasErrors())
    		.andExpect(model().attribute("userRequest",testRequest))
    		.andExpect(view().name("user/add"));
    	}
}
