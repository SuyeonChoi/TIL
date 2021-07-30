package me.suyeon.jwttutorial.dto;

import lombok.*;

// 토큰 정보 response 용
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;
}
