package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    //jpa가 동작하는 방식 기준. 스프링부트와 연동되므로 주입
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        //jpql 객체지향 query를 사용해야함
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //jpql query 언어. 객체를 대상으로 쿼리를 날림.
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;
    }
}
