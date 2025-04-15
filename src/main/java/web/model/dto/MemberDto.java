/*  MemberDto 클래스 | rw 25-04-15 생성
   - 클라이언트 요청 데이터(JSON)를 저장하거나, 클라이언트에 응답으로 전달할 때 사용하는 데이터 전송 객체입니다.
   - Entity와 달리 DB와 직접 연결되지 않으며, 데이터를 안전하게 전달하는 용도로 사용됩니다.
*/

package web.model.dto;

// [1] 롬복 어노테이션: 코드 자동 생성 관련
import lombok.Data;                     // 모든 필드에 대해 getter, setter, toString 자동 생성
import lombok.Builder;                  // 객체를 유연하게 생성할 수 있는 빌더 패턴 제공
import lombok.NoArgsConstructor;        // 기본 생성자 자동 생성
import lombok.AllArgsConstructor;       // 모든 필드를 포함한 생성자 자동 생성

// [2] 해당 DTO와 변환을 위한 Entity 클래스
import web.model.entity.MemberEntity;   // DB 테이블과 연결된 JPA 클래스

@Data                                   // [3] getter, setter, toString 등을 자동 생성
@Builder                                // [4] 객체를 생성할 때 .builder().필드().build() 형태로 생성 가능
@NoArgsConstructor                      // [5] 매개변수가 없는 생성자 자동 생성
@AllArgsConstructor                     // [6] 모든 필드를 포함하는 생성자 자동 생성
public class MemberDto { // CS

    // [7] 필드 선언: 클라이언트가 주는 데이터 또는 응답에 포함될 데이터
    private int mno;                    // 회원 고유 번호 (식별자)
    private String memail;              // 이메일
    private String mpwd;                // 비밀번호
    private String mname;               // 이름

    // [8] DTO → Entity로 변환하는 메서드
    // - DB 저장을 위해 Entity 객체로 변환하는 기능
    public MemberEntity toEntity() { // fs
        return MemberEntity.builder()   // 빌더 패턴을 사용하여 객체 생성
                .mno(mno)               // Dto의 mno → Entity의 mno로 복사
                .memail(memail)         // 이메일 복사
                .mpwd(mpwd)             // 비밀번호 복사
                .mname(mname)           // 이름 복사
                .build();               // 객체 생성 완료
    } // fe

} // CE