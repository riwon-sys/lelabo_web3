package example.class02._BaseTime;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// - DB테이블의 레코드 생널날짜와 수정날짜를 감지하는 엔티티
// - AppStart 클래스에서 @EnableJpaAuditing' 활성화 해야함
@MappedSuperclass // 상속 엔티티 (공용 부분) 사용
@EntityListeners(AuditingEntityListener.class) // 해당 클래스의 멤버 변수들은 JPA 감지대상
@Getter // 롬복
public class BaseTime {

    // 1. 엔티티|레코드의 영속/생성 날짜/시간 자동 주입
    @CreatedDate
    private LocalDateTime 생성날짜시간;  // 회원가입날짜 , 제품등록날짜 , 주문일짜.

    // 2. 엔티티|레코드의 수정 날짜/시간 자동 주입
    @LastModifiedDate
    private LocalDateTime 수정날짜시간;  // 수정수정날짜 , 제품수정일짜 , 주문수정일짜.
}
