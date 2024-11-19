package jp.rm.personal.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.rm.personal.util.Authority;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	//パスワードの暗号化に用いる
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests(auth -> auth
				//静的リソースをアクセス可能にする
				.requestMatchers(PathRequest
					.toStaticResources()
					.atCommonLocations()
				).permitAll()
				
				//ログインに必要な画面はアクセス可能とする
				.requestMatchers("/register", "/login").permitAll()
				
				//ADMIN専用の画面を設定する
				.requestMatchers("/admin/**").hasAuthority(Authority.ADMIN.name())
				
				.anyRequest().authenticated()
			)
			
			.formLogin(login -> login
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
			)
			
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.permitAll()
			)
			
			.rememberMe(Customizer.withDefaults());
		
		return http.build();
	}
}
