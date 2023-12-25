package com.example.demo.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.UserController;
import com.example.demo.dto.UserRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class UserControllerOnLocalhostTest {
	/** 接続テスト用のクライアント */
	@Autowired
	private MockMvc mvc;
	
	@Autowired 
	private UserController userController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private TestEntityManager entityManager; 

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
	
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccessIT() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccess100Name() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
		// 100文字作成が手間なのでループである程度楽に作成する。
		String testName = new String();
		for(int i = 0; i < 10; i++) {
			String s = "あいうえおかきくけこ"; //10文字
			testName = testName + s;
		}
    	testRequest.setName(testName);
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccess255Address() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
		// 255文字をループで作成
		String testAddress = new String();
		for(int i = 0; i < 25; i++) {
			String s = "あいうえおかきくけこさしすせそたちつてとなにぬねの"; //25文字
			testAddress = testAddress + s;
		}
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("testAddress");
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccessFront2Phone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("09-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccessFront4Phone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("0900-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccessCenter1Phone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-1-5678"); // 中央が1桁は正常？
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccessCenter2Phone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-12-5678"); // 中央が2桁は正常？
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの正常テスト
	 * */
	@Test
	void testUserCreateSuccessCenter3Phone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-123-5678"); 
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("redirect:/user/list"));

	}
	
	/**
	 * UserCreateメソッドの異常系メソッド（nameのエラー）
	 * */
	@Test
	void testUserCreateErrorEmptyName() throws Exception {
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("user/add"));
	}
	
	/**
	 * UserCreateメソッドの異常系メソッド（nameのエラー）
	 * */
	@Test
	void testUserCreateErrorOverName() throws Exception {
		UserRequest testRequest = new UserRequest();
		// 100文字作成が手間なのでループである程度楽に作成する。
		String testName = new String();
		for(int i = 0; i < 10; i++) {
			String s = "あいうえおかきくけこ"; //10文字
			testName = testName + s;
		}
		testName = testName + "あ"; //101文字目
		
    	testRequest.setName(testName);
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("user/add"));
	}
	
	/**
	 * UserCreateメソッドの異常系メソッド（addressのエラー）
	 * */
	@Test
	void testUserCreateErrorEmptyAddress() throws Exception {
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("");
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("user/add"));
	}
	
	/**
	 * UserCreateメソッドの異常系メソッド（addressのエラー）
	 * */
	@Test
	void testUserCreateErrorOverAddress() throws Exception {
		UserRequest testRequest = new UserRequest();
		// 255文字をループで作成
		String testAddress = new String();
		for(int i = 0; i < 25; i++) {
			String s = "あいうえおかきくけこさしすせそたちつてとなにぬねの"; //25文字
			testAddress = testAddress + s;
		}
		testAddress = testAddress + "あ"; //256文字目
		
    	testRequest.setName("名前テスト");
    	testRequest.setAddress(testAddress);
    	testRequest.setPhone("090-1234-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
    			.andExpect(view().name("user/add"));
	}
	
	/**
	 * UserCreateメソッドの異常系テスト（Phoneのエラー）
	 * */
	@Test
	void testUserCreateErrorEmptyPhone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
		.andExpect(view().name("user/add"));

	}
	
	/**
	 * UserCreateメソッドの異常系テスト（Phoneのエラー）
	 * */
	@Test
	void testUserCreateErrorFormatNoneBarPhone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("09012345678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
		.andExpect(view().name("user/add"));

	}
	
	/**
	 * UserCreateメソッドの異常系テスト（Phoneのエラー）
	 * */
	@Test
	void testUserCreateErrorFormat5FrontPhone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("09000-1234-5678"); //フォーマットが間違っているため、修正されば問題なし
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
		.andExpect(view().name("user/add"));

	}
	
	/**
	 * UserCreateメソッドの異常系テスト（Phoneのエラー）
	 * */
	@Test
	void testUserCreateErrorFormat1FrontPhone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("0-1234-5678"); //UserRequestのフォーマットが間違っているため、修正されば問題なし
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
		.andExpect(view().name("user/add"));

	}

	/**
	 * UserCreateメソッドの異常系テスト（Phoneのエラー）
	 * */
	@Test
	void testUserCreateErrorFormatCenterPhone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("090-12345-5678");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
		.andExpect(view().name("user/add"));

	}
	
	/**
	 * UserCreateメソッドの異常系テスト（Phoneのエラー）
	 * */
	@Test
	void testUserCreateErrorFormatBackPhone() throws Exception {
		// リクエスト情報用意
		UserRequest testRequest = new UserRequest();
    	testRequest.setName("名前テスト");
    	testRequest.setAddress("住所テスト");
    	testRequest.setPhone("0-1234-56789");
    	
    	this.mvc.perform((post("/user/create")).flashAttr("userRequest", testRequest))
		.andExpect(view().name("user/add"));

	}
	
}
