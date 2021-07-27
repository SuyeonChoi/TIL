package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //로딩 시점에 한개만

        EntityManager em = emf.createEntityManager(); // 요청이 올때마다 실행

        EntityTransaction tx = em.getTransaction(); // JPA의 모든 데이터 변경은 DB transaction안에서 이루어져야함
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            //
            team.getMembers().add(member);

            em.persist(team);

            tx.commit(); // 필수
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
