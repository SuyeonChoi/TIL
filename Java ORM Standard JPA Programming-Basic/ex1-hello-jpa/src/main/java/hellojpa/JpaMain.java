package hellojpa;

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

            Movie movie = new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("바람과 함께 사라지다.");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Item item = em.find(Item.class, movie.getId());
            System.out.println("item = " + item);
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
