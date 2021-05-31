package com.jojoldu.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class Application { //프로젝트의 메인 클래스. 항상 프로젝트 최상단에 위치
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); //내장 WAS(Web Application Server)실행
    }
}
