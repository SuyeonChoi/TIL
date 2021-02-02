package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private MemberService memberService;
    //1. 필드 주입 방식(변경이 어려움)
//    @Autowired private MemberService memberService;

    //2. Setter주입 방식(public이므로 변경당할 수 있음)
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }


//    @Autowired
//    public void setMemberService() {
//        this.memberService = memberService;
//    }


    //3.생성자 주입 방식(권장)
    //memberService를 controller와 연결시켜줌. DI.의존관계
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
