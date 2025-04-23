/*  CategoryEntityRepository 인터페이스 | rw 25-04-23 생성
    - 카테고리(category) 테이블과 관련된 DB 작업을 처리하는 레포지토리 인터페이스입니다.
    - JpaRepository를 상속받아 별도의 구현 없이 기본 CRUD 메서드를 사용할 수 있습니다.
    - 엔티티 클래스(CategoryEntity)와 기본키 타입(Long)을 명시합니다.
*/

package web.model.repository;

// [1] JPA의 기본 기능을 포함한 Repository 인터페이스 (CRUD 제공)
import org.springframework.data.jpa.repository.JpaRepository;

// [2] 스프링에서 이 인터페이스를 관리할 수 있도록 설정
import org.springframework.stereotype.Repository;

// [3] 연관 엔티티 클래스 import
import web.model.entity.CategoryEntity;

@Repository // Spring이 이 인터페이스를 Repository로 인식
public interface CategoryEntityRepository                    // CS
        extends JpaRepository<CategoryEntity, Long> {        // JpaRepository<엔티티, PK 타입>

    // [4] CategoryEntity → 카테고리 정보가 저장된 엔티티
    // [5] Long            → 기본키 필드 cno의 자료형

} // CE