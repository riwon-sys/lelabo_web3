package example.class03.과제03.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass @EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseTime {
    // 1. 생성날짜
    @CreatedDate// 엔티티의 수정 날짜 및 시간 주입하기
    private LocalDateTime createAt;

    // 2. 수정날짜
    @LastModifiedDate // 엔티티의 수정 날짜 및 시간 주입하기
    private LocalDateTime updateAt;
}
