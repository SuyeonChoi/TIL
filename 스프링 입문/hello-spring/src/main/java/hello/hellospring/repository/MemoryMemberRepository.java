package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); //동시성 고려 안한 것. 단순하게 예제풀이용
    private static long sequence = 0L; //key값 생성용

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //Null일 경우 감싸줘서 Client가 처리가능
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //java8의 Lambda
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); //optional로 찾은 결과 반환
    }

    @Override
    public List<Member> findAll() { //실무에서 list 많이 쓰임
        return new ArrayList<>(store.values());
    }
}
