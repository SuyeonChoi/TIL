package com.jojoldu.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //json을 반환하는 컨트롤러로 생성
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
