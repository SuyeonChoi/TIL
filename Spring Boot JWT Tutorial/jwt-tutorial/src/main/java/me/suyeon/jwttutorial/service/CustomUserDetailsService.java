package me.suyeon.jwttutorial.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.suyeon.jwttutorial.entity.User;
import me.suyeon.jwttutorial.repository.UserRepository;

@Component("userDetailService")
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 로그인 시 DB에서 유저 정보를 권한 정보와 함께 가져옴
	// 해당 정보를 기준으로 유저가 활성화 상태인 경우
	// 유저의 권한정보와 username, password로 userdetails.User 객체 리턴
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneWithAuthoritiesByUsername(username)
			.map(user -> createUser(username, user))
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	private org.springframework.security.core.userdetails.User createUser(String username, User user) {
		if (!user.isActivated()) {
			throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
		}
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
			.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
			.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
			user.getPassword(),
			grantedAuthorities);
	}
}
