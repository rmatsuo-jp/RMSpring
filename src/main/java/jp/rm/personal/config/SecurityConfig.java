package jp.rm.personal.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.rm.personal.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final SiteUserRepository userRepository;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	//パスワードの暗号化に用いる
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(PathRequest	//静的リソースをアクセス可能にする
					.toStaticResources()
					.atCommonLocations()
				).permitAll()
				
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
	
	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			var user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found")
			);
			
			return new User(user.getUsername(), user.getPassword(),
				AuthorityUtils.createAuthorityList("ADMIN")
			);
		};
	}
}
