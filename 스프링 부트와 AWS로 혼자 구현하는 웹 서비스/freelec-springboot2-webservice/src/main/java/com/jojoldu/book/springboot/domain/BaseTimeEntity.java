package com.jojoldu.book.springboot.domain;

// 모든 Entity의 상위 클래스로 Entity들의 createdDate, modifiedDate를 자동으로 관리

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass // JPA Entity의 클래스들이 BaseTimeEntity를 상속하는 경우 필드도 칼럼으로 인식하게 함
@EntityListeners(AuditingEntityListener.class) //BaseTimeEntity 클래스에 Auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate //Entity가 생성되어 저장될 때 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 Entity 값을 변경할 때 시간 자동 저장
    private LocalDateTime modifiedDate;
}
