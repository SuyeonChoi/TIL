package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity 클래스에서는 Setter 생성 X
// 대신 목적(이벤트)에 맞는 public 메소드로 값을 변경 

@Getter //모든 필드의 Getter메소드 자동생성
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 명시
public class Posts { //실제 DB테이블과 매칭될 클래스
    @Id //테이블의 pk 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk생성 규칙(auto increment)
    private Long id;

    @Column(length = 500, nullable = false) //굳이 선언하지 않아도됨. 기본값 외 추가 변경 필요시 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //클래스의 빌더 패턴 클래스 생성. 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
