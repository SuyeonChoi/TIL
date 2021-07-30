package me.suyeon.jwttutorial.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

// JWT를 위한 컨스텀한 필터를 만들기 위한 클래스
public class JwtFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private TokenProvider tokenProvider;

	public JwtFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	// JWT Token의 인증 정보를 현재 실행중인 Security Context에 저장
	// 실제 필터링 로직 또한 doFilter 함수 내부에 작성
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {
		//request에서 토큰 받음
		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		String jwt = resolveToken(httpServletRequest);
		String requestURI = httpServletRequest.getRequestURI();

		// jwt 토큰이 유효성 검증(validateToken)을 통과하여 정상적인 경우
		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			// 토큰에서 authentication 객체 생성
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			// SecurityContext에 authentication 객체 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri : {}", authentication.getName(), requestURI);
		} else {
			logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	// 필터링을 사용하기 위해 토큰 정보가 필요
	private String resolveToken(HttpServletRequest request) {
		// Request Header에서 토큰 정보를 가져옴
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
