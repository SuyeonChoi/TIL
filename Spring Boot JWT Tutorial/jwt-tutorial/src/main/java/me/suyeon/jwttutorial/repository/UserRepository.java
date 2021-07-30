package me.suyeon.jwttutorial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import me.suyeon.jwttutorial.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Username을 기준으로 User 정보를 가져올때 권한 정보도 함께 가져오는 메소드
	@EntityGraph(attributePaths = "authorities") // eager 조회
	Optional<User> findOneWithAuthoritiesByUsername(String username);
}
