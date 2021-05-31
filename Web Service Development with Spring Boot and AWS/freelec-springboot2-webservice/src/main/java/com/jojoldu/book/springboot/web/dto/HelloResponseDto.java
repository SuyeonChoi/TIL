package com.jojoldu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter //선언된 모든 필드의 get 메소드 생성
@RequiredArgsConstructor //선언된 모든 final필드의 생성자 생
public class HelloResponseDto {

    private final String name;
    private final int amount;
}

