/*  MemberEntity 클래스 | rw 25-04-15 생성
   - DB의 'member' 테이블과 매핑되는 JPA 클래스입니다.
   - 회원 정보(mno, memail, mpwd, mname)를 데이터베이스에 저장하거나 불러올 때 사용됩니다.
   - DTO와 달리 실제 DB와 연결되어 있으며, @Entity 어노테이션을 통해 테이블과 직접 연결됩니다.
*/

package web.model.entity;

// [1] JPA 관련 어노테이션 (테이블 매핑, 기본키 지정 등)
import jakarta.persistence.Entity;                       // 이 클래스가 JPA 엔티티임을 나타냄 (DB 테이블과 연결)
import jakarta.persistence.Id;                           // 기본키(PK) 지정
import jakarta.persistence.Table;                        // 테이블 이름을 수동 지정할 때 사용
import jakarta.persistence.GeneratedValue;               // 기본키 자동 생성 전략 지정
import jakarta.persistence.GenerationType;               // 자동 생성 방식(enum)

// [2] 롬복 어노테이션 - 필드 자동 처리 관련
import lombok.Data;                                      // getter, setter, toString 자동 생성
import lombok.Builder;                                   // 빌더 패턴 제공
import lombok.NoArgsConstructor;                         // 기본 생성자 자동 생성
import lombok.AllArgsConstructor;                        // 전체 필드를 포함한 생성자 자동 생성

// [3] DTO 변환용 클래스 import
import web.model.dto.MemberDto;                          // toDto() 변환 시 사용

// [4] DB 테이블과 매핑되는 클래스 선언부
@Entity                                                  // 이 클래스는 DB와 연결되는 JPA 엔티티임을 의미
@Table(name = "member")                                  // 매핑될 실제 테이블 이름을 "member"로 지정
@Data                                                    // getter, setter, toString 등을 자동 생성
@Builder                                                 // 객체를 .builder().필드().build() 형식으로 생성 가능
@NoArgsConstructor                                        // 파라미터 없는 생성자 생성
@AllArgsConstructor                                       // 모든 필드를 포함한 생성자 생성
public class MemberEntity { // CS

    // [5] 회원 번호(PK) - 자동 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // MySQL의 AUTO_INCREMENT와 동일 (자동 증가)
    private int mno;                                     // 회원 식별번호 (기본키)

    // [6] 회원 이메일
    private String memail;                               // 이메일 (ex. user@naver.com)

    // [7] 회원 비밀번호
    private String mpwd;                                 // 비밀번호 (암호화 처리 필요)

    // [8] 회원 이름
    private String mname;                                // 이름 (ex. 유재석)

    // [9] Entity → DTO로 변환하는 메서드
    // - 클라이언트에 응답할 때 Entity를 안전한 DTO로 변환하여 전달
    public MemberDto toDto() { // fs
        return MemberDto.builder()
                .mno(mno)                                // 식별번호 복사
                .memail(memail)                          // 이메일 복사
                .mname(mname)                            // 이름 복사 (※ 비밀번호는 제외)
                .build();
    } // fe

} // CE