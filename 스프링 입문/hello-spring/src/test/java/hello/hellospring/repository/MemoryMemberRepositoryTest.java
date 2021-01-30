package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest { //굳이 public으로 하지 않아도 됨
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //Callback method. test 메소드가 끝날때마다 동작
    //이제 테스트 동작 순서가 관계가 없음. 의존관계가 없도록 만들어야 깔끔!
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        //get으로 Optional 타입 데이터 꺼내기
        Member result = repository.findById(member.getId()).get();
        //result = true
        //System.out.println("result = " + (result == member));

        //assert(junit.jupter.api)활용
        //Assertions.assertEquals(member, null);

        //assertj. assert문을 좀더 편하게 활용할 수 있게함.
        //Assertions. static import함
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
