// BaseTime 구성 | rw 25-04-10 생성
package os.model.entity;

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
    // [1]. Ocreate | 생성 날짜|시간 주입
    @CreatedDate
    private LocalDateTime ocraetAt;

    // [2]. Oupdate | 수정 날짜|시간 주입
    @LastModifiedDate
    private  LocalDateTime oupdateAt;
}
