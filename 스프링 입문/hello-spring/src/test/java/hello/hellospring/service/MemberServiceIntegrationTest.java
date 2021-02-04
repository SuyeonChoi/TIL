package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//통합테스트
@SpringBootTest
@Transactional //테스트를 실행시 트랜잭션 먼저 실행. 테스트에서 DB에 넣은 데이터 반영X. 트랜잭션 롤백
class MemberServiceIntegrationTest {

    //테스트는 편리하게 필드로 injection
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given(무엇인가 주어져서(data))
        Member member = new Member();
        member.setName("spring100");

        //when(실행하였을 때. 검증할것)
        Long saveId = memberService.join(member);

        //then(이러한 결과가 나와야함)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}