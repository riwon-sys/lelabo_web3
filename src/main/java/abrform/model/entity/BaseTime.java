// Entity - BaseTime 구성 | rw 25-04-11 생성
package abrform.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseTime {
    // [1] create | 생성 날짜 , 시간 주입
    @CreatedDate
    private LocalDateTime createAt;

    // [2] update | 수정 날짜 , 시간 주입
    @LastModifiedDate
    private LocalDateTime updateAt;
}
