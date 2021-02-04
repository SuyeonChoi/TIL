package hello.hellospring.domain;

import javax.persistence.*;

@Entity //jpa가 관리하는 entitiy로 형성
public class Member {
    //pk, DB가 알아서 id생성
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //식별자. 시스템 임의의 값. 고객 설정값 X.

    //@Column(name = "username") -> 이름이 다른 경우 이렇게 column직접 지정할수 있음
    private String name; //이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
