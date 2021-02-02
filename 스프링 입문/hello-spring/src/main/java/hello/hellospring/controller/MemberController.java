package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    //memberService를 controller와 연결시켜줌. DI.의존관계
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
