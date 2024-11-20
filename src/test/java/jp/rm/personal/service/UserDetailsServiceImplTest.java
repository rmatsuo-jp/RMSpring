package jp.rm.personal.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.rm.personal.model.SiteUser;
import jp.rm.personal.repository.SiteUserRepository;
import jp.rm.personal.util.Authority;

//テストはpublicをつけなくてよい

@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {
	
	@Autowired
	SiteUserRepository repository;
	
	@Autowired
	UserDetailsServiceImpl service;
	
	@Test
	@DisplayName("ユーザ名が存在するとき、ユーザ詳細を取得することを期待します")
	void whenUsernameExists_expectToGetUserDetails() {
		var user = new SiteUser();
		user.setUsername("test");
		user.setPassword("password");
		user.setEmail("test@example.com");
		user.setAuthority(Authority.USER);
		repository.save(user);
		
		var actual = service.loadUserByUsername("test");
		
		assertEquals(user.getUsername(), actual.getUsername());
	}
	
	@Test
	@DisplayName("ユーザ名が存在しないとき、例外をスローします")
	void whenUsernameDoesNotExist_throwException() {
		assertThrows(UsernameNotFoundException.class,
				() -> service.loadUserByUsername("fault"));
	}
}
