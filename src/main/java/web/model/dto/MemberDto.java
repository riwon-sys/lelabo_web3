/*  MemberDto 클래스 | rw 25-04-15 생성
   - 클라이언트 요청 데이터(JSON)를 저장하거나, 클라이언트에 응답으로 전달할 때 사용하는 데이터 전송 객체입니다.
   - Entity와 달리 DB와 직접 연결되지 않으며, 데이터를 안전하게 전달하는 용도로 사용됩니다.
   - 회원가입, 로그인, 내정보 조회 등 회원 관련 기능에 필요한 데이터를 주고받는 데 활용됩니다.
*/

package web.model.dto;

// [1] Lombok 어노테이션 임포트 (getter/setter/toString 등 자동 생성)
import lombok.Data;                     // [1-1] @Getter, @Setter, @ToString, @EqualsAndHashCode 포함
import lombok.Builder;                  // [1-2] 빌더 패턴을 사용할 수 있게 해주는 어노테이션
import lombok.NoArgsConstructor;        // [1-3] 기본 생성자 자동 생성
import lombok.AllArgsConstructor;       // [1-4] 모든 필드를 매개변수로 받는 생성자 자동 생성

// [2] DTO 변환 대상인 MemberEntity 임포트
import web.model.entity.MemberEntity;   // [2-1] DB 테이블과 직접 매핑되는 JPA Entity 클래스

@Data                                   // [3] 클래스 내부 모든 필드에 대해 getter, setter, toString 자동 생성
@Builder                                // [4] MemberDto.builder().mname("홍길동").memail("test@test.com")... 생성 가능
@NoArgsConstructor                      // [5] 매개변수가 없는 기본 생성자 생성
@AllArgsConstructor                     // [6] 모든 필드를 포함한 생성자 생성 (순서 중요: DTO 필드 순서와 동일하게)

public class MemberDto { // CS

    // [7-1] 회원 번호
    // - DB의 기본키(PK) 역할을 하는 값이며, 자동 생성되는 숫자일 가능성이 높음
    // - 회원 식별에 사용됨
    private int mno;

    // [7-2] 이메일
    // - 회원 아이디처럼 사용됨
    // - 로그인 시 ID로 입력받는 항목
    private String memail;

    // [7-3] 비밀번호
    // - 사용자가 입력한 비밀번호
    // - 서버에서는 저장 전에 반드시 암호화(Bcrypt 등)를 적용해야 함
    private String mpwd;

    // [7-4] 회원 이름
    // - 사용자의 실명 또는 닉네임
    private String mname;

    // [8] DTO → Entity 변환 메서드
    // - DB에 저장하거나 수정할 때 Entity 객체가 필요하기 때문에 변환 작업 수행
    // - builder 패턴을 사용하여 MemberEntity 객체 생성
    public MemberEntity toEntity() { // fs
        return MemberEntity.builder()   // [8-1] Entity 객체 생성 시작
                .mno(mno)               // [8-2] DTO의 mno 값을 Entity에 복사
                .memail(memail)         // [8-3] 이메일 복사
                .mpwd(mpwd)             // [8-4] 비밀번호 복사 (주의: 암호화는 서비스에서 처리해야 함)
                .mname(mname)           // [8-5] 이름 복사
                .build();               // [8-6] MemberEntity 객체 최종 생성 및 반환
    } // fe

} // CE