package me.suyeon.jwttutorial.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.suyeon.jwttutorial.dto.UserDto;
import me.suyeon.jwttutorial.entity.Authority;
import me.suyeon.jwttutorial.entity.User;
import me.suyeon.jwttutorial.repository.UserRepository;
import me.suyeon.jwttutorial.util.SecurityUtil;

// 회원가입, 유저 정보 조회 등
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository,
		PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// 회원 가입 로직을 수행하는 함수
	@Transactional
	public User signup(UserDto userDto) {
		// username을 기준으로 DB에 존재하는지 확인
		if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
			throw new RuntimeException("이미 가입되어 있는 유저입니다.");
		}

		// DB에 User 존재하지 않는 경우 권한 정보 생성
		Authority authority = Authority.builder()
			.authorityName("ROLE_USER") // ROLE_USER 라는 하나의 권한 정보를 가짐. 권한 정보의 차이로 권한검정 테스트 가능
			.build();

		// 권한 정보를 넣어서 User 정보를 생성
		User user = User.builder()
			.username(userDto.getUsername())
			.password(passwordEncoder.encode(userDto.getPassword()))
			.nickname(userDto.getNickname())
			.authorities(Collections.singleton(authority))
			.activated(true)
			.build();

		// user 정보와 권한 정보 저장
		return userRepository.save(user);
	}

	//유저, 권한정보를 가져오는 메소드
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(String username) {
		// username에 해당하는 유저 객체와 권한 정보를 가져옴
		return userRepository.findOneWithAuthoritiesByUsername(username);
	}

	//유저, 권한정보를 가져오는 메소드
	@Transactional(readOnly = true)
	public Optional<User> getMyUserWithAuthorities() {
		// 현재 SecurityContext에 저장되어있는 username에 해당하는 유저와 권한 정보를 가져옴
		return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
	}
}
