package example.class02._toDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

// entity 목적 : DB 테이블의 영속/연결 된 객체
// [*] 사용 되는 계층 : 서비스레이어 (비즈니스 로직 : 실질적인 기능을 다루는 로직)
@Entity
@Table(name = "exambook1")
@Builder
public class ExamEntity1 { // 서비스 사용
    @Id
    private String id; // 도서고유식별번호
    private String title; // 도서명
    private int price; // 도서가격
    // - entity 에서 dto 로 변환하는 함수를 커스텀해서 만들어야함
    // - 우리는 이것을 toEntity 라고 한다.
       // (1) entity -> dto 객체로 변환함수.
       public  ExamDto toDto() {
           // this(해당 하는 식별번호를 그대로 넣겠다)
           // 1. 일반적인 방법 (생성자)
           // return new ExamDto(this.id, this.title, this.price); // dto 로 변환

           // 2. 빌더 패턴
           // 클래스명.builder().build();
           return ExamDto.builder()
                   .id(this.id) // this 왜 사용 할까? 담배라서? 아니? // 보다 명확하게 알아보기 위해서
                   .title(this.title)
                   .price(this.price)
                   .build();
       }
}
