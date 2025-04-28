/*  ProductEntity 클래스 | rw 25-04-23 생성
    - DB의 'product' 테이블과 매핑되는 JPA 클래스입니다.
    - 상품 정보(pname, pcontent, pprice, pview)를 저장하며, 회원(MemberEntity), 카테고리(CategoryEntity)와 연결됩니다.
    - 상품은 여러 이미지(ImgEntity), 여러 댓글(ReplyEntity)과 양방향 관계를 가집니다.
*/

package web.model.entity;

// [*] JPA 관련 어노테이션
import jakarta.persistence.*;

// [*] 롬복 어노테이션 - 필드 자동 처리 관련
import lombok.*;

// [*] 기타 어노테이션
import org.hibernate.annotations.ColumnDefault;

// [*] 컬렉션 및 연관 엔티티 import
import java.util.ArrayList;
import java.util.List;

@Entity                                                  // [A] 이 클래스는 데이터베이스 테이블과 연결되는 JPA 엔티티임을 표시 (DB 저장 대상)
@Table(name = "product")                                 // [B] 이 엔티티가 데이터베이스의 "product" 테이블과 매핑된다는 의미
@Getter @Setter                                          // [C] 모든 필드에 대한 getter/setter 자동 생성
@Builder                                                 // [D] 객체 생성 시 빌더 패턴 사용 가능
@ToString                                                // [E] 객체 정보를 문자열로 출력 (디버깅 시 유용)
@NoArgsConstructor                                       // [F] 기본 생성자 자동 생성 (필수)
@AllArgsConstructor                                      // [G] 모든 필드를 받는 전체 생성자 자동 생성
public class ProductEntity extends BaseTime { // CS

    // [1] 상품 번호(PK) - 자동 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // MySQL AUTO_INCREMENT 방식
    private long pno;                                    // 상품 식별번호

    // [2] 상품 이름
    @Column(nullable = false)                            // NOT NULL 제약조건
    private String pname;                                // 상품명 (ex: 나이키 운동화)

    // [3] 상품 설명 (Lob 타입)
    @Column(columnDefinition = "longtext")               // MySQL의 longtext 타입으로 매핑
    private String pcontent;                             // 상품 설명

    // [4] 상품 가격
    @Column(nullable = false)                            // NOT NULL
    @ColumnDefault("0")                                  // 기본값 0 설정
    private int pprice;                                  // 상품 가격

    // [5] 조회수
    @Column(nullable = false)
    @ColumnDefault("0")
    private int pview;                                   // 조회 수 (기본값 0)

    // [6] 작성자 - 회원 엔티티와 다대일 단방향 연결
    @ManyToOne
    @JoinColumn(name = "mno")                            // 외래키: 회원번호
    private MemberEntity memberEntity;                   // 작성 회원 정보

    // [7] 카테고리 - 다대일 단방향 연결
    @ManyToOne
    @JoinColumn(name = "cno")                            // 외래키: 카테고리번호
    private CategoryEntity categoryEntity;               // 상품 분류

    // [8] 이미지 리스트 - 양방향 다대일 관계
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ImgEntity> imgEntityList = new ArrayList<>(); // 상품 이미지 리스트

    // [9] 댓글 리스트 - 양방향 다대일 관계
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>(); // 댓글(리뷰) 리스트

} // CE