package example.class02._toDto;

import lombok.Builder;
import lombok.Data;

// DTO 목적 : 서로 다른 계층/레이어 간의 이동 객체
// VO 목적 : 수정 불가능한 객체 (setter 없음 / 불변)
// [*] 사용되는 계층 : 컨트롤러 레이어 (view-dto-controller)
@Data // 룸복
@Builder // 빌더 패턴

public class ExamDto { // 컨트롤러 사용
    private String id;
    private String title;
    private int price;
    // (2) dto -> entity 객체로 변환함수
    public  ExamEntity1 toEntity1(){
        return ExamEntity1.builder()
               .id(this.id)
               .title(this.title)
               .price(this.price)
               .build();
    }
}
