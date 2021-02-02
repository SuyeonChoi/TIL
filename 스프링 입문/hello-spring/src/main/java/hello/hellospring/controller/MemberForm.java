package hello.hellospring.controller;

public class MemberForm {
    //members/createMemberForm.html의 input tag의 name과 매칭됨
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
