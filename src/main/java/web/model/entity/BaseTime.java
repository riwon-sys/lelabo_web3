/*  BaseTime 클래스 | rw 25-04-15 생성
   - 모든 테이블 공통으로 사용하는 '생성일', '수정일'을 자동으로 관리하는 상위 클래스입니다.
   - @MappedSuperclass를 통해 실제 테이블은 생성되지 않으며,
     이를 상속받는 다른 클래스에 날짜 필드를 자동으로 포함시킵니다.
*/

package web.model.entity;

// [1] JPA 감시 기능을 활성화하기 위한 리스너 관련 어노테이션들
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

// [2] 생성자 없이도 데이터를 읽을 수 있게 해주는 Lombok 어노테이션
import lombok.Getter;

// [3] JPA에서 날짜 기록을 자동으로 처리해주는 어노테이션들
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// [4] 날짜/시간을 나타내는 자바 클래스
import java.time.LocalDateTime;

// [5] 이 클래스는 실제 테이블이 아닌, 상속용 부모 클래스임을 선언
@MappedSuperclass
// [6] JPA가 생성일/수정일을 자동으로 기록할 수 있도록 리스너를 등록
@EntityListeners(AuditingEntityListener.class)
// [7] 모든 필드에 대해 Getter 메서드를 자동 생성해주는 Lombok 어노테이션
@Getter
public class BaseTime {

    // [8] 엔티티가 생성될 때(등록될 때) 자동으로 시간이 저장되는 필드
    @CreatedDate
    private LocalDateTime createAt;  // 예: 2025-04-15T10:20:30

    // [9] 엔티티가 수정될 때마다 자동으로 시간이 저장되는 필드
    @LastModifiedDate
    private LocalDateTime updateAt;  // 예: 2025-04-15T10:30:45
}
