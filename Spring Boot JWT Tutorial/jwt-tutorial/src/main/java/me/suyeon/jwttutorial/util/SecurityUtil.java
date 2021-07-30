package me.suyeon.jwttutorial.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import me.suyeon.jwttutorial.jwt.TokenProvider;

// 간단한 유틸리티성 메소드를 가지는 클래스
public class SecurityUtil {
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private SecurityUtil() {
	}

	// Security Context의 Authentication 객체를 통해 username 리턴
	public static Optional<String> getCurrentUsername() {
		// Security Context에서 Authentication 객체를 얻음
		// Request가 들어올때 Security Context에 Authentication 객체가 저장됨(JwtFilter 클래스의 doFilter 메소드)
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			logger.debug("Security Context에 인증 정보가 없습니다.");
			return Optional.empty();
		}

		String username = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails)authentication.getPrincipal();
			username = springSecurityUser.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			username = (String)authentication.getPrincipal();
		}

		return Optional.ofNullable(username);
	}
}
