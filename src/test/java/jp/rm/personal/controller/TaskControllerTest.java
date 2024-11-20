package jp.rm.personal.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import jp.rm.personal.model.SiteUser;
import jp.rm.personal.util.Authority;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	@DisplayName("登録エラーがあるとき、エラー表示することを期待します")
	void whenThereIsRegistrationError_exceptToSeeErrors() throws Exception {
		
		mockMvc
			.perform(
				post("/register")
				.flashAttr("user", new SiteUser())
				.with(csrf())
			)
			
			.andExpect(model().hasErrors())
			.andExpect(view().name("userRegister"));
	}
	
	@Test
	@DisplayName("管理者ユーザとして登録するとき、成功することを期待します")
	void whenRegisteringAsAdminUser_expectToSucceed() throws Exception {
		var user = new SiteUser();
		user.setUsername("管理者ユーザ");
		user.setPassword("password");
		user.setEmail("admin@example.com");
		user.setAdmin(true);
		user.setAuthority(Authority.ADMIN);
		
		mockMvc.perform(post("/register")
			.flashAttr("user", user).with(csrf())
		)
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/login?userRegister"))
		.andExpect(status().isFound());
	}
	
	@Test
	@DisplayName("管理者ユーザでログインするとき、ユーザ一覧を表示することを期待します")
	@WithMockUser(username="admin", authorities="ADMIN")
	void whenLoggedInAsAdminUser_expectToSeeListOfUsers() throws Exception {
		mockMvc.perform(get("/admin/list"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ユーザ一覧")))
			.andExpect(view().name("list"));
	}













}
