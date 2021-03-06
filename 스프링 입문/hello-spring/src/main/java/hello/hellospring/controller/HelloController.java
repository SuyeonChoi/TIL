package hello.hellospring.controller;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//Controller Annotation 기입 필수
@Controller
public class HelloController {
    @GetMapping("hello") //웹에서 /hello 호출시 mapping. GET방식
    public String hello(@NotNull Model model) {
        model.addAttribute("data", "hello!!"); //key:data, value:hello!!
        return "hello";
    }

    //MVC, 템플릿 엔진
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    //API 방식
    @GetMapping("hello-string")
    @ResponseBody //http의 body에 데이터 직접 전달. 뷰 X.
    public String helloSTring(@RequestParam("name") String name) {
        return "hello " + name; //hello spring
    }

    //API방식 - json(key-value구조)으로 데이터 전달
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
