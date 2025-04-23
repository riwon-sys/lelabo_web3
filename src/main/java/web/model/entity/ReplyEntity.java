/*  ReplyEntity 클래스 | rw 25-04-23 생성
    - DB의 'reply' 테이블과 매핑되는 JPA 클래스입니다.
    - 댓글 내용(rcontent)을 저장하며, 작성자(MemberEntity)와 대상 상품(ProductEntity)과 연결됩니다.
    - 단방향 다대일(@ManyToOne) 관계를 통해 각 댓글이 회원과 상품에 속함을 나타냅니다.
*/

package web.model.entity;

// [1] JPA 관련 어노테이션
import jakarta.persistence.*;

// [2] 롬복 어노테이션
import lombok.*;

@Entity                                                 // 이 클래스는 DB와 연결되는 JPA 엔티티임을 의미
@Table(name = "reply")                                  // 매핑될 테이블명을 "reply"로 지정
@Getter @Setter                                          // getter, setter 자동 생성
@Builder                                                 // 빌더 패턴 메서드 자동 생성
@ToString                                                // toString() 자동 생성
@NoArgsConstructor                                       // 파라미터 없는 생성자
@AllArgsConstructor                                      // 모든 필드 포함 생성자
public class ReplyEntity { // CS

    // [1] 댓글 번호(PK) - 자동 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // MySQL AUTO_INCREMENT 방식
    private long rno;                                    // 댓글 식별번호

    // [2] 댓글 내용
    @Column(nullable = false)                            // NOT NULL 제약조건
    private String rcontent;                             // 댓글 본문

    // [3] 작성자 - 회원 엔티티와 다대일 단방향 연결
    @ManyToOne
    @JoinColumn(name = "mno")                            // 외래키: 회원번호
    private MemberEntity memberEntity;                   // 댓글 작성자

    // [4] 대상 상품 - 상품 엔티티와 다대일 단방향 연결
    @ManyToOne
    @JoinColumn(name = "pno")                            // 외래키: 상품번호
    private ProductEntity productEntity;                 // 댓글이 달린 상품

} // CE