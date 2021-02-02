package hello.hellospring; //hello.hellospring의 하위패키지만 스프링빈으로 등록됨. Component 스캔 대상

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		//HelloSpringApplication을 넣어서 스프링 어플리케이션 실행
		//톰캣 웹서버 내장. 자체적으로 띄우면서 스프링부트 실행
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
