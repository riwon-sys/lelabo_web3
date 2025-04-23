/*  CategoryEntity 클래스 | rw 25-04-23 생성
    - DB의 'category' 테이블과 매핑되는 JPA 클래스입니다.
    - 카테고리 이름(cname)을 저장하며, 하나의 카테고리는 여러 상품(ProductEntity)과 연결됩니다.
    - 상품과는 양방향 @OneToMany 관계로 설정되어 있습니다.
*/

package web.model.entity;

// [1] JPA 관련 어노테이션
import jakarta.persistence.*;

// [2] 롬복 어노테이션 - 필드 자동 처리 관련
import lombok.*;

// [3] 컬렉션 import
import java.util.ArrayList;
import java.util.List;

@Entity                                                 // 이 클래스는 DB와 연결되는 JPA 엔티티임을 의미
@Table(name = "category")                               // 매핑될 테이블명을 "category"로 지정
@Getter @Setter                                          // getter, setter 자동 생성
@Builder                                                 // 빌더 패턴 생성자
@ToString                                                // toString() 자동 생성 (순환참조 주의)
@NoArgsConstructor                                       // 파라미터 없는 생성자
@AllArgsConstructor                                      // 전체 필드 포함 생성자
public class CategoryEntity { // CS

    // [1] 카테고리 번호(PK) - 자동 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // MySQL AUTO_INCREMENT 방식
    private long cno;                                    // 카테고리 식별번호

    // [2] 카테고리 이름
    @Column(nullable = false, length = 100)              // NOT NULL, 최대 길이 100
    private String cname;                                // 카테고리명 (ex: 전자기기)

    // [3] 상품 리스트 - 양방향 다대일 관계
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ProductEntity> productEntityList = new ArrayList<>(); // 해당 카테고리에 속한 상품들

} // CE