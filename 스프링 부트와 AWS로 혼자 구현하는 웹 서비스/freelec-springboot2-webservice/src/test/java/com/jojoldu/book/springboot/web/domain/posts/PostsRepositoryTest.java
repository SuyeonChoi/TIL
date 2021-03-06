package com.jojoldu.book.springboot.web.domain.posts;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After //Junit에서 단위 테스트가 끝날때마다 수행되는 메소드를 지정. 보통 테스트간 데이터 침범을 막기 위해 사용
    public void cleanup(){
        //h2 데이터베이스의 데이터를 날림
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불럭오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //테이블 post에 insert/update 쿼리를 실행
        //id 값이 있는 경우 update. 없는 경우 insert 쿼리가 실행됨
        postsRepository.save(Posts.builder()
                            .title(title)
                            .content(content)
                            .author("tndus970120@naver.com")
                            .build());

        //when
        List<Posts> postList = postsRepository.findAll(); //테이블 posts의 모든 데이터를 조회

        //then
        Posts posts = postList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    //JPA Auditing 테스트
    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 3, 7, 0, 0, 0);
        postsRepository.save(Posts.builder()
                                .title("title")
                                .content("content")
                                .author("author")
                                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
