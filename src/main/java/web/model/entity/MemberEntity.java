/*  MemberEntity 클래스 | rw 25-04-23 수정
    - DB의 'member' 테이블과 매핑되는 JPA 클래스입니다.
    - 회원 정보(mno, memail, mpwd, mname)를 데이터베이스에 저장하거나 불러올 때 사용됩니다.
    - 상품(productEntityList), 댓글(replyEntityList)과의 양방향 관계를 가집니다.
*/

package web.model.entity;

// [1] JPA 관련 어노테이션
import jakarta.persistence.*;

// [2] 롬복 어노테이션 - 필드 자동 처리 관련
import lombok.*;

// [3] DTO 변환용 클래스 import
import web.model.dto.MemberDto;

// [4] 연관 엔티티 import
import java.util.ArrayList;
import java.util.List;

@Entity                                               // 이 클래스는 DB와 연결되는 JPA 엔티티임을 의미
@Table(name = "member")                               // 매핑될 테이블명을 "member"로 지정
@Data                                                 // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 포함
@Builder                                              // .builder().필드().build() 형식 생성자 지원
@NoArgsConstructor                                    // 파라미터 없는 생성자
@AllArgsConstructor                                   // 전체 필드를 포함한 생성자
public class MemberEntity { // CS

    // [1] 회원 고유번호(PK) - 자동 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL AUTO_INCREMENT 방식
    private int mno;                                     // 회원 식별번호

    // [2] 이메일
    private String memail;                               // 이메일 (예: test@naver.com)

    // [3] 비밀번호
    private String mpwd;                                 // 암호화 저장 필요

    // [4] 이름
    private String mname;                                // 이름 (예: 유재석)

    // [5] 상품 리스트 - 양방향 관계
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude                                     // 순환 참조 방지
    @Builder.Default
    private List<ProductEntity> productEntityList = new ArrayList<>(); // 사용자가 등록한 상품 목록

    // [6] 댓글 리스트 - 양방향 관계
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();     // 사용자가 작성한 댓글 목록

    // [7] Entity → DTO 변환 메서드
    public MemberDto toDto() { // fs
        return MemberDto.builder()
                .mno(mno)                                 // 식별번호 복사
                .memail(memail)                           // 이메일 복사
                .mname(mname)                             // 이름 복사 (※ 비밀번호는 제외)
                .build();
    } // fe

} // CE