/*  ImgEntity 클래스 | rw 25-04-23 생성
    - DB의 'img' 테이블과 매핑되는 JPA 클래스입니다.
    - 상품 이미지 정보를 저장하며, 각 이미지는 특정 상품(ProductEntity)에 속합니다.
    - 단방향 다대일 관계(@ManyToOne)를 통해 상품과 연결됩니다.
*/

package web.model.entity;

// [1] JPA 관련 어노테이션
import jakarta.persistence.*;

// [2] 롬복 어노테이션 - 기본 메서드 자동 생성
import lombok.*;

// [3] 연관 클래스 import
import web.model.entity.ProductEntity;

@Entity                                               // 이 클래스는 DB와 연결되는 JPA 엔티티임을 의미
@Table(name = "img")                                  // 매핑될 테이블명을 "img"로 지정
@Getter @Setter                                       // getter, setter 자동 생성
@Builder                                              // .builder().필드().build() 형식 생성자 자동 생성
@ToString                                             // toString() 자동 생성 (단, 순환참조 주의 필요)
@NoArgsConstructor                                    // 파라미터 없는 생성자 생성
@AllArgsConstructor                                   // 모든 필드를 포함한 생성자 생성
public class ImgEntity { // CS

    // [1] 이미지 고유번호(PK) - 자동 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL AUTO_INCREMENT 방식
    private long ino;                                    // 이미지 식별번호

    // [2] 이미지 파일명
    @Column(nullable = false)                            // 비어 있을 수 없음 (NOT NULL)
    private String iname;                                // 이미지 이름 (ex: uuid_파일명.jpg)

    // [3] 연관 상품 - 다대일 단방향 매핑
    @ManyToOne                                           // 여러 이미지가 하나의 상품에 속함
    @JoinColumn(name = "pno")                            // 외래키 컬럼명을 'pno'로 지정 (상품 번호)
    private ProductEntity productEntity;                 // 연관된 상품 엔티티

} // CE