package com.jojoldu.book.springboot.config.auth; //시큐리티 관련 클래스의 패키지

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor ///선언된 모든 final필드의 생성자 생성
@EnableWebSecurity // Spring Security 설정들을 활성화함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //.csrf().disable().headers().frameOptions().disable(): h2-console 화면을 사용하기 위해 해당 옵션들 disable
        // authorizeRequests: URL별 권한 관리 설정의 시작점
        // antMatchers: 권한 관리 대상을 지정. "/"등은 전체에게 권한(permitAll). "/api/v1/**"주소를 가진 api는 user권한 가진 사람에게만 권한
        // anyRequest: 설정된 값 외 나머지 url. authenticated()로 인증된(로그인한) 사용자에게만 허용
        // logout().logoutSuccessUrl("/"): 로그아웃 기능에 대한 진입점. 로그아웃 성공시 "/"주소로 이동
        // oauth2Login: OAuth2 로그인 기능에 대한 진입점
        // userInfoEndpoint: OAuth2 로그인 성공 이후 사용자 정보를 가져올때 설정 담당
        // userService: 로그인 성공시 후속 조치를 진행할 구현체(UserInterface)등록. 리소스 서버(소셜서비스 등)에서 사용자 정보를 가져온 상태에서 추가 기능 명시 가능
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
