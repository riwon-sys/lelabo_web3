package web.service;

// [ * ] 트랜잭션 처리를 위한 어노테이션: 모든 DB 작업이 하나의 묶음으로 처리됨. 중간에 오류 발생 시 전체 롤백
import jakarta.transaction.Transactional;

// [ * ] 생성자 기반 의존성 자동 주입: final 필드에 대해 자동 생성자 생성
import lombok.RequiredArgsConstructor;

// [ * ] 페이징 기능을 구현할 때 필요한 도구들
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

// [ * ] REST 응답을 위한 ResponseEntity 관련
import org.springframework.http.ResponseEntity;

// [ * ] 서비스 클래스 선언을 나타내며, 비즈니스 로직을 처리하는 컴포넌트
import org.springframework.stereotype.Service;

// [ * ] Multipart 파일 처리 관련 (Spring MVC 기반)
import org.springframework.web.multipart.MultipartFile;

// [ * ] DTO, Entity, Repository 및 파일 처리 유틸 클래스들 import
import web.model.dto.CategoryDto;
import web.model.dto.ProductDto;
import web.model.entity.CategoryEntity;
import web.model.entity.ImgEntity;
import web.model.entity.MemberEntity;
import web.model.entity.ProductEntity;
import web.model.repository.CategoryEntityRepository;
import web.model.repository.ImgEntityRepository;
import web.model.repository.MemberEntityRepository;
import web.model.repository.ProductEntityRepository;
import web.util.FileUtil;

// [ * ] 리스트, 옵셔널, 스트림 등 자바 컬렉션 유틸리티
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service                   // [1] 이 클래스가 서비스 역할임을 Spring에 알림
@RequiredArgsConstructor   // [2] final로 선언된 필드들을 자동으로 생성자 주입 받도록 설정
@Transactional             // [3] 이 클래스의 모든 메서드는 트랜잭션 처리: 실패 시 전체 작업을 롤백
public class ProductService { // CS

    // [ * ] 의존성 주입 받을 리포지토리 및 유틸 클래스 선언
    private final ProductEntityRepository productEntityRepository; // 제품 관련 DB 처리
    private final MemberEntityRepository memberEntityRepository;   // 회원 관련 DB 처리
    private final CategoryEntityRepository categoryEntityRepository; // 카테고리 관련 DB 처리
    private final FileUtil fileUtil; // 파일 업로드 및 삭제 유틸 클래스
    private final ImgEntityRepository imgEntityRepository; // 이미지 DB 처리

    // =======================================================================================
    // [1] 제품 등록 기능 (DB 저장 + 파일 업로드 포함)
    public boolean registerProduct(ProductDto productDto, int loginMno) {
        // [1-1] 로그인된 회원번호로 회원정보 조회 (존재 확인 및 FK 설정용)
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findById(loginMno);
        if (optionalMemberEntity.isEmpty()) return false; // 회원이 존재하지 않으면 중단

        // [1-2] 입력받은 카테고리 번호로 카테고리 정보 조회 (존재 확인 및 FK 설정용)
        Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findById(productDto.getCno());
        if (optionalCategoryEntity.isEmpty()) return false; // 카테고리가 없으면 중단

        // [1-3] Dto 객체 → Entity 객체로 변환 (DB 저장용)
        ProductEntity productEntity = productDto.toEntity();

        // [1-4] FK 설정: 회원 엔티티 주입 (작성자 정보)
        productEntity.setMemberEntity(optionalMemberEntity.get());

        // [1-5] FK 설정: 카테고리 엔티티 주입 (제품 분류 정보)
        productEntity.setCategoryEntity(optionalCategoryEntity.get());

        // [1-6] 제품 정보 DB에 저장 (insert)
        ProductEntity saveEntity = productEntityRepository.save(productEntity);

        // [1-7] 저장된 제품번호가 0 이하이면 실패로 간주
        if (saveEntity.getPno() <= 0) return false;

        // [1-8] 첨부된 이미지가 존재할 경우, 파일 업로드 + 이미지 DB 저장
        if (productDto.getFiles() != null && !productDto.getFiles().isEmpty()) {
            for (MultipartFile file : productDto.getFiles()) {
                // [1-8-1] 파일 업로드 처리 → 서버에 저장하고 파일명 반환
                String saveFileName = fileUtil.fileUpload(file);

                // [1-8-2] 업로드 실패 시 예외 발생 → 트랜잭션 전체 롤백
                if (saveFileName == null) {
                    throw new RuntimeException("업로드 중에 오류 발생");
                }

                // [1-8-3] 업로드 성공 시 이미지 엔티티 객체 생성
                ImgEntity imgEntity = ImgEntity.builder()
                        .iname(saveFileName)            // 저장된 파일명
                        .build();

                // [1-8-4] 제품과 이미지 연결(FK 주입)
                imgEntity.setProductEntity(saveEntity);

                // [1-8-5] 이미지 정보 DB에 저장 (insert)
                imgEntityRepository.save(imgEntity);
            }
        }

        // [1-9] 모든 작업 성공 시 true 반환
        return true;
    }

    // =======================================================================================
    // [2] (이전 방식) 제품 전체/카테고리별 조회 → 주석처리된 상태

    // =======================================================================================
    // [3] 제품 개별조회 기능
    public ProductDto viewProduct(long pno) {
        // [3-1] 제품번호로 제품 정보 조회 (Optional)
        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(pno);
        if (productEntityOptional.isEmpty()) return null; // 없으면 null

        // [3-2] 제품 엔티티 꺼내기 (.get() 사용 시 null이면 예외 발생하므로 위에서 isEmpty() 체크함)
        ProductEntity productEntity = productEntityOptional.get();

        // [3-3] 기존 조회수에 +1 하여 저장 (DB에 반영됨)
        productEntity.setPview(productEntity.getPview() + 1);

        // [3-4] 엔티티를 DTO로 변환하여 반환
        return ProductDto.toDto(productEntity);
    }

    // =======================================================================================
    // [4] 제품 삭제 기능 (작성자 일치 확인 + 이미지 삭제 포함)
    public boolean deleteProduct(long pno, int loginMno) {
        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(pno);
        if (productEntityOptional.isEmpty()) return false;

        ProductEntity productEntity = productEntityOptional.get();
        if (productEntity.getMemberEntity().getMno() != loginMno) return false;

        // [4-1] 등록된 이미지들을 삭제 처리
        List<ImgEntity> imgEntityList = productEntity.getImgEntityList();
        for (ImgEntity imgEntity : imgEntityList) {
            boolean result = fileUtil.fileDelete(imgEntity.getIname());
            if (!result) {
                throw new RuntimeException("파일삭제 실패");
            }
        }

        // [4-2] 제품 정보 삭제 (Cascade 설정으로 이미지도 함께 삭제됨)
        productEntityRepository.deleteById(pno);
        return true;
    }

    // =======================================================================================
    // [5] 제품 수정 기능 (본문 정보 + 새 이미지 추가 가능)
    public boolean updateProduct(ProductDto productDto, int loginMno) {
        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(productDto.getPno());
        if (productEntityOptional.isEmpty()) return false;
        ProductEntity productEntity = productEntityOptional.get();

        if (productEntity.getMemberEntity().getMno() != loginMno) return false;

        Optional<CategoryEntity> categoryEntityOptional = categoryEntityRepository.findById(productDto.getCno());
        if (categoryEntityOptional.isEmpty()) return false;
        CategoryEntity categoryEntity = categoryEntityOptional.get();

        // [5-1] 기존 제품 정보 수정 (set으로 직접 수정)
        productEntity.setPname(productDto.getPname());
        productEntity.setPcontent(productDto.getPcontent());
        productEntity.setPprice(productDto.getPprice());
        productEntity.setCategoryEntity(categoryEntity);

        // [5-2] 새 이미지 업로드 처리
        List<MultipartFile> newFile = productDto.getFiles();
        if (newFile != null && !newFile.isEmpty()) {
            for (MultipartFile file : newFile) {
                String saveFileName = fileUtil.fileUpload(file);
                if (saveFileName == null) throw new RuntimeException("파일 업로드 오류발생");

                ImgEntity imgEntity = ImgEntity.builder()
                        .iname(saveFileName)
                        .productEntity(productEntity)
                        .build();
                imgEntityRepository.save(imgEntity);
            }
        }
        return true;
    }

    // =======================================================================================
    // [6] 이미지 개별 삭제 기능
    public boolean deleteImage(long ino, int loginMno) {
        Optional<ImgEntity> optionalImgEntity = imgEntityRepository.findById(ino);
        if (optionalImgEntity.isEmpty()) return false;
        ImgEntity imgEntity = optionalImgEntity.get();

        if (imgEntity.getProductEntity().getMemberEntity().getMno() != loginMno) return false;

        String deleteFileName = imgEntity.getIname();
        boolean result = fileUtil.fileDelete(deleteFileName);
        if (!result) throw new RuntimeException("파일 삭제 실패");

        imgEntityRepository.deleteById(ino);
        return true;
    }

    // =======================================================================================
    // [7] 전체 카테고리 조회 기능
    public List<CategoryDto> allCategory() {
        List<CategoryEntity> categoryEntityList = categoryEntityRepository.findAll(); // 전체 카테고리 조회
        return categoryEntityList.stream()
                .map(CategoryDto::toDto) // Entity → DTO 변환
                .collect(Collectors.toList());
    }

    // =======================================================================================
    // [8] 조건 기반 전체 제품 조회 (카테고리 + 검색 + 페이징)
    public List<ProductDto> allProducts(Long cno, int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "pno"));

        Page<ProductEntity> productEntities = productEntityRepository.findBySearch(cno, keyword, pageable);

        return productEntities.stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

} // CE