package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class OrderSimpleQueryRepository {

    private final EntityManager em;

    //새로운 레파지토리에 생성하여 유지보수성을 높임 
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
        //select 절에서 원하는 데이터를 직접 선택하므로 애플리케이션 네트워크 용량 최적화
        //하지만 v3과 성능 차이가 심하지 않음
        //데이터가 대용량인 경우 성능 측정하고 결정!
    }
}
