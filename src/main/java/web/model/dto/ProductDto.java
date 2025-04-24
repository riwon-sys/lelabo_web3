/*  ProductDto 클래스 | rw 25-04-23 생성
    - 상품 등록 및 조회 시 클라이언트와의 데이터 전달 객체입니다.
    - 등록 시에는 @ModelAttribute로 multipart/form-data를 수신하며,
      이미지 파일 리스트와 상품 정보, 카테고리 번호를 포함합니다.
    - 조회 시에는 엔티티 기반 정보를 받아 DTO로 변환되어 응답에 사용됩니다.
    - 연관 정보(회원, 카테고리)는 서비스 계층에서 엔티티로 주입/추출됩니다.
*/

package web.model.dto;

// [1] 롬복 어노테이션: getter/setter, toString, 생성자, 빌더 자동 생성
import lombok.*;

// [2] 스프링 multipart 업로드용 클래스
import org.springframework.web.multipart.MultipartFile;

// [3] 연관된 엔티티 클래스
import web.model.entity.ImgEntity;
import web.model.entity.ProductEntity;

// [4] 자바 유틸: 리스트와 스트림
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString                           // [A] 객체 정보를 문자열로 출력 (디버깅 시 유용)
@Getter @Setter                     // [B] 모든 필드에 대한 getter/setter 자동 생성
@Builder                            // [C] 객체 생성 시 빌더 패턴 사용 가능
@NoArgsConstructor                  // [D] 기본 생성자 자동 생성 (필수)
@AllArgsConstructor                 // [E] 모든 필드를 받는 전체 생성자 자동 생성
public class ProductDto { // CS

    // ======================================================================================
    // [1] 등록 시 사용되는 필드들
    // - 클라이언트가 등록 요청 시 전달하는 값들입니다.
    // ======================================================================================

    private String pname; // [1-1] 상품명 (예: 무선 마우스)
    private String pcontent; // [1-2] 상품 설명 (에디터 입력 내용 포함 가능)
    private int pprice; // [1-3] 상품 가격 (정수형, 단위: 원)

    // [1-4] 업로드할 이미지 파일들 (여러 장 가능)
    // - MultipartFile: 스프링에서 파일을 업로드할 때 사용하는 자료형
    // - 기본값으로 빈 리스트를 미리 생성해 NullPointException 방지
    private List<MultipartFile> files = new ArrayList<>();

    // [1-5] 카테고리 번호
    // - 프론트에서 선택된 카테고리 ID를 담고 있음
    // - 실제 연관 객체(CategoryEntity)는 서비스에서 주입
    private long cno;

    // ======================================================================================
    // [2] DTO → Entity 변환 메서드 (등록 시 사용)
    // - 클라이언트로부터 전달받은 데이터(ProductDto)를 DB 저장용 Entity로 변환
    // - memberEntity, categoryEntity는 서비스 계층에서 set 해줌
    // ======================================================================================
    public ProductEntity toEntity() { // fs
        return ProductEntity.builder()
                .pname(this.pname)         // (1) 상품명 전달
                .pcontent(this.pcontent)   // (2) 설명 전달
                .pprice(this.pprice)       // (3) 가격 전달
                .build();                  // (4) 나머지 연관 엔티티는 서비스에서 set
    } // fe

    // ======================================================================================
    // [3] 조회 시 사용되는 필드들
    // - DB에서 조회한 데이터를 클라이언트에 응답할 때 사용됩니다.
    // - 서비스 계층에서 Entity → DTO 변환 시 사용됩니다.
    // ======================================================================================

    private long pno;              // [3-1] 상품 고유 번호 (PK)
    private int pview;             // [3-2] 조회 수
    private String memail;         // [3-3] 등록한 회원의 이메일
    private String cname;          // [3-4] 카테고리 이름
    private List<String> images = new ArrayList<>(); // [3-5] 이미지 파일명 리스트 (서버 파일 경로 X)

    // ======================================================================================
    // [4] Entity → DTO 변환 메서드 (조회 시 사용)
    // - DB에서 조회한 ProductEntity를 ProductDto로 변환
    // - 연관된 MemberEntity, CategoryEntity, ImgEntity 포함 처리
    // ======================================================================================
    public static ProductDto toDto(ProductEntity productEntity) { // fs
        return ProductDto.builder()
                .pname(productEntity.getPname())                   // (1) 상품명
                .pcontent(productEntity.getPcontent())             // (2) 설명
                .pprice(productEntity.getPprice())                 // (3) 가격
                .pno(productEntity.getPno())                       // (4) 상품 번호
                .pview(productEntity.getPview())                   // (5) 조회 수
                .memail(productEntity.getMemberEntity().getMemail())  // (6) 작성자 이메일
                .cno(productEntity.getCategoryEntity().getCno())      // (7) 카테고리 번호
                .cname(productEntity.getCategoryEntity().getCname())  // (8) 카테고리 이름
                .images( // (9) 이미지 리스트 구성 (Entity → String 리스트로 변환)
                        productEntity.getImgEntityList().stream()
                                .map(ImgEntity::getIname)          // → 각 이미지 이름만 추출
                                .collect(Collectors.toList())      // → List<String> 으로 변환
                )
                .build();
    } // fe

} // CE