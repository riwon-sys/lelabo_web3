/*  ImgEntityRepository 인터페이스 | rw 25-04-23 생성
    - 이미지(img) 테이블과 관련된 DB 작업을 처리하는 레포지토리 인터페이스입니다.
    - JpaRepository를 상속받아 이미지 저장, 조회, 삭제 등 CRUD 메서드를 사용할 수 있습니다.
    - 엔티티 클래스(ImgEntity)와 기본키 타입(Long)을 명시합니다.
*/

package web.model.repository;

// [1] JPA의 기본 기능을 포함한 Repository 인터페이스 (CRUD 제공)
import org.springframework.data.jpa.repository.JpaRepository;

// [2] 스프링에서 이 인터페이스를 관리할 수 있도록 설정
import org.springframework.stereotype.Repository;

// [3] 연관 엔티티 클래스 import
import web.model.entity.ImgEntity;
@Repository
public interface ImgEntityRepository                       // CS
        extends JpaRepository<ImgEntity, Long> {           // JpaRepository<엔티티, PK 타입>

    // [1] ImgEntity → 이미지 정보가 저장된 엔티티
    // [2] Long      → 기본키 필드 ino의 자료형

} // CE