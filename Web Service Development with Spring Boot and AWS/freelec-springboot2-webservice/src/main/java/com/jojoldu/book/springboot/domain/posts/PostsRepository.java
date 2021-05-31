package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//Entity 클래스와 기본 Entity Repository(Interface)는 함께 위치(domain)
//기본적인 CRUD 메소드 자동 생성
public interface PostsRepository extends JpaRepository<Posts, Long> {

    //전체 조회
    //SpringDataJpa에서 제공하지 않는 메소드인 경우 아래와 같이 사용 가능.
    //현재 코드는 사용가능함.다만 Query가 가독성이 좋음.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
