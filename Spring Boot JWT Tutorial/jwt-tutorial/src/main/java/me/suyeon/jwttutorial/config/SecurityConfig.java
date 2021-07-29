package me.suyeon.jwttutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.suyeon.jwttutorial.jwt.JwtAccessDeniedHandler;
import me.suyeon.jwttutorial.jwt.JwtAuthenticationEntryPoint;
import me.suyeon.jwttutorial.jwt.JwtSecurityConfig;
import me.suyeon.jwttutorial.jwt.TokenProvider;

@EnableWebSecurity //기본적인 웹 보안 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 를 메소드 단위로 사용하기 위해 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	// 생성자 주입
	public SecurityConfig(TokenProvider tokenProvider,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		this.tokenProvider = tokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

	// BCryptPasswordEncoder 사용하여 패스워드 encode
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// h2-console/ 하위 모든 요청과 파비콘을 모두 무시하는 것으로 설정
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers(
				"/h2-console/**"
				, "favicon.ico"
			);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // 토큰을 사용하므로 csrf 설정 disable

			// exception 핸들링 시 직접 생성한 클래스들로 추가
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// h2-console을 위한 설정
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			// 세션을 사용하지 않으므로 STATELESS로 세션 설정
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// 토큰을 받기 위한 로그인 api, 회원가입 api는 토큰이 없는 상태에서 요청이 들어오므로 permitAll
			.and()
			.authorizeRequests()
			.antMatchers("/api/hello").permitAll()
			.antMatchers("/api/authenticate").permitAll() // 로그인
			.antMatchers("/api/signup").permitAll() // 회원가입
			.anyRequest().authenticated()

			// JwtFilter를 addFilterBefore로 등록하는 JwtSecurityConfig 클래스 적용
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}
}
