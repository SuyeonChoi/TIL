package com.jojoldu.book.springboot.domain.user;
//사용자의 권한 관리 클래스

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    //스프링 시큐리티에서는 권한 코드에 ROLE_ 이 항상 앞에 있어야 함
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
