package me.suyeon.jwttutorial.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 생성한 TokenProvider, JwtFilter를 SecurityConfig에 적용하기 위한 클래스
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	// TokenProvider 주입
	private TokenProvider tokenProvider;

	public JwtSecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	// 생성한 JwtFilter를 Security로직에 필터로 등록
	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtFilter customFilter = new JwtFilter(tokenProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
