package me.suyeon.jwttutorial.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.suyeon.jwttutorial.dto.UserDto;
import me.suyeon.jwttutorial.entity.User;
import me.suyeon.jwttutorial.service.UserService;

// UserService 메소드 호출하는 컨트롤러
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signUp(
		@Valid @RequestBody UserDto userDto
	) {
		return ResponseEntity.ok(userService.signup(userDto));
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')") // USER, ADMIN 두가지 권한 모두 허용하여 호출 가능
	public ResponseEntity<User> getMyUserInfo() {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 권한만 호출 가능. USER 권한이 발급한 토큰으로 호출시 JwtAccessDeniedHandler 작동 
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}

}
