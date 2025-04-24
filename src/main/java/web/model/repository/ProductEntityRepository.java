/*  ProductEntityRepository 인터페이스 | rw 25-04-24 생성
    - ProductEntity(상품 테이블)의 JPA 전용 인터페이스입니다.
    - 기본 CRUD 제공 외에도, 카테고리 기반 조회, 검색 + 페이징 등 커스텀 쿼리를 포함합니다.
    - 사용되는 방식은 총 4가지로 구성되어 있습니다:
      [방법1] JPA 기본 제공 메서드 (save, findAll 등)
      [방법2] 쿼리 메서드 (메서드 이름으로 SQL 자동 생성)
      [방법3] 네이티브 쿼리 (직접 SQL 작성)
      [방법4] JPQL (자바 객체 기반 SQL)
*/

package web.model.repository;

// [1] 스프링 데이터 JPA 관련 기능 import
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// [2] 연관 Entity import
import web.model.entity.ImgEntity;
import web.model.entity.ProductEntity;

import java.util.List;

@Repository // [3] 해당 인터페이스가 Repository 역할을 한다는 것을 스프링에게 알림
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> { // CS
    // ==================================================================================
    // [방법1] JpaRepository에서 기본 제공되는 CRUD 메서드 사용
    // - 사용 가능한 메서드 예시:
    //   - save()         → 등록/수정
    //   - findById()     → 기본키로 단일 조회
    //   - findAll()      → 전체 조회
    //   - deleteById()   → 삭제
    // ==================================================================================

    // ==================================================================================
    // [방법2] 쿼리 메서드 사용
    // - 메서드 명명 규칙으로 SQL 자동 생성됨 (카멜 표기법 사용)
    // - 예: findBy필드명 / findBy엔티티명_필드명
    // - 하위 엔티티의 필드를 조회하려면 '엔티티명.필드명'으로 접근해야 함
    // ==================================================================================

    List<ProductEntity> findByCategoryEntityCno(long cno);
    /*
        설명:
        - 'ProductEntity'에서 직접 'cno'라는 필드는 없지만,
          'CategoryEntity'와의 연관관계(@ManyToOne categoryEntity) 안에 'cno'가 존재함.
        - 따라서 'categoryEntity.cno'를 기반으로 검색해야 하므로,
          메서드 명은 반드시 findByCategoryEntityCno 형식으로 작성해야 동작함.
    */

    // ==================================================================================
    // [방법3] 네이티브 쿼리 (직접 SQL 작성)
    // - 실제 DB에 날리는 SQL을 그대로 작성 (MySQL 문법 그대로 사용)
    // - 파라미터는 :파라미터명 형식으로 바인딩함
    // ==================================================================================

    @Query(value = "SELECT * FROM product WHERE cno = :cno", nativeQuery = true)
    List<ProductEntity> nativeQuery1(long cno);
    /*
        설명:
        - 실제 product 테이블에서 cno가 일치하는 데이터만 가져오는 쿼리
        - nativeQuery = true 설정을 통해 실제 SQL로 실행됨
    */

    @Query(value = "SELECT * FROM product " +
            "WHERE ( :cno IS NULL OR :cno = 0 OR cno = :cno ) " +
            "AND ( :keyword IS NULL OR pname LIKE %:keyword% )",
            nativeQuery = true)
    Page<ProductEntity> findBySearch(Long cno, String keyword, Pageable pageable);
    /*
        설명:
        - 카테고리(cno)와 키워드(pname like '%keyword%') 조건을 조합하여 검색하는 쿼리
        - Pageable 타입을 사용하여 페이징 처리도 가능
        - NULL 또는 0이면 전체 검색, keyword가 NULL이면 키워드 조건 생략
        - Page<> 타입은 총 페이지 수, 현재 페이지 번호 등의 메타정보도 함께 제공
    */

    // ==================================================================================
    // [방법4] JPQL (Java Persistence Query Language)
    // - 자바 객체를 대상으로 하는 SQL 형식 (여기서는 사용되지 않음)
    // - 예: SELECT p FROM ProductEntity p WHERE p.pname LIKE :keyword
    // ==================================================================================

} // CE