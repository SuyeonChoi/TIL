package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Interface가 Interface를 상속받을때는 extends 키워드
//JpaRepository<T, id(Entity의 pk) type>
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //JPQL select m from Member m where m.name = ?
    //findByName, findByNameAndId, findByIdOrName 등
    @Override
    Optional<Member> findByName(String name);
}
