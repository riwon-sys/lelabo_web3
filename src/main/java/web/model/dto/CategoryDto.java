/*  CategoryDto 클래스 | rw 25-04-24 생성
   - 클라이언트에게 전달할 카테고리 정보를 담는 DTO(데이터 전송 객체)입니다.
   - DB와 직접 연결된 Entity가 아니라, 데이터를 안전하고 구조적으로 전달하기 위한 중간 객체입니다.
   - 주로 Entity → DTO 변환 방식으로 사용되며, View 또는 응답 용도로 활용됩니다.
   - toDto() 메서드를 통해 Entity 객체를 CategoryDto 형태로 가공할 수 있습니다.
*/

package web.model.dto;

// [ * ] Lombok 어노테이션: 코드를 간결하게 만들어주는 자동 생성 도구들
import lombok.*;

// [ * ] DTO 변환용 Entity 클래스 import
import web.model.entity.CategoryEntity;

@Getter                 // [1] 모든 필드에 대한 getter 자동 생성 (예: getCno(), getCname())
@Setter                 // [2] 모든 필드에 대한 setter 자동 생성 (예: setCno(), setCname())
@ToString              // [3] toString() 자동 생성 (디버깅 및 로그 출력에 유용)
@Builder               // [4] 빌더 패턴 사용 가능 → CategoryDto.builder().cno(1L).build()
@NoArgsConstructor     // [5] 매개변수 없는 생성자 자동 생성
@AllArgsConstructor    // [6] 모든 필드를 매개변수로 받는 생성자 자동 생성
public class CategoryDto { // CS

    // [7] 카테고리 번호
    // - DB에서는 기본 키(PK) 역할을 하며, 일반적으로 자동 증가되는 숫자입니다.
    // - 예: 1 (전자제품), 2 (의류), 3 (도서) 등
    private long cno;

    // [8] 카테고리 이름
    // - 사용자가 보는 이름값 (예: "전자제품", "의류", "도서" 등)
    // - 뷰 화면 또는 API 응답에 그대로 사용됩니다.
    private String cname;

    // [9] Entity → DTO 변환 메서드
    // - DB에서 조회된 CategoryEntity 객체를 CategoryDto로 변환합니다.
    // - 클라이언트에 전달할 데이터를 가공하는 역할입니다.
    public static CategoryDto toDto(CategoryEntity categoryEntity) {
        return CategoryDto.builder()                        // [9-1] 빌더 패턴을 사용하여 객체 생성 시작
                .cno(categoryEntity.getCno())              // [9-2] Entity의 cno 값을 DTO에 대입
                .cname(categoryEntity.getCname())          // [9-3] Entity의 cname 값을 DTO에 대입
                .build();                                  // [9-4] DTO 객체 최종 생성 후 반환
    }

} // CE