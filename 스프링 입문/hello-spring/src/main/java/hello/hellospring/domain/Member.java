package hello.hellospring.domain;

public class Member {

    private Long id; //식별자. 시스템 임의의 값. 고객 설정값 X.
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
