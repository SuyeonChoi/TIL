package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) //JUnit에 내장된 실행자 외 다른 실행자를 실행. SpringRunner 실행자 사용
@WebMvcTest(controllers = HelloController.class) //Web(SpringMVC)에 집중
public class HelloControllerTest {
    @Autowired //스프링이 관리하는 Bean주입
    private MockMvc mvc; //웹 API를 테스트할 때 사용. 스프링MVC 테스트의 시작점. get,post api 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk()) //mvc.perform 결과 검증. HTTP header의 status 검증(200, 404, 500 등). isOk는 200인지 아닌지 검증
                .andExpect(content().string(hello)); //mvc.perform 결과 검증. 응답 본문의 내용 검증. hello가 맞는지
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        //param: api 테스트 시 사용될 용청 파라미터 설정. String만 허용
        //jsonPath: json 응답값을 필드별로 검증하는 메소
        mvc.perform(
                get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(name)))
                    .andExpect(jsonPath("$.amount", is(amount)));
    }
}

