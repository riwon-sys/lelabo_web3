/*  MemberEntityRepository 인터페이스 | rw 25-04-15 생성
   - 회원(member) 테이블과 관련된 모든 DB 작업을 처리하는 레포지토리 인터페이스입니다.
   - JpaRepository를 상속받아 별도의 구현 없이 기본 CRUD 메서드를 바로 사용할 수 있습니다.
   - 엔티티 클래스(MemberEntity)와 기본키 타입(Integer)을 명시합니다.
*/

package web.model.repository;

// [1] JPA의 기본 기능을 포함한 Repository 인터페이스 (CRUD 제공)
import org.springframework.data.jpa.repository.JpaRepository;  // JPA 제공 기본 메서드 포함 (save, findAll 등)

// [2] 해당 클래스가 스프링의 레포지토리 역할임을 명시
import org.springframework.stereotype.Repository;             // @Component와 동일하게 스프링에서 자동 관리됨

// [3] DB와 연결되는 엔티티 클래스 import
import web.model.entity.MemberEntity;                         // 회원 정보를 담는 JPA 엔티티 클래스

// [4] 이 인터페이스는 JPA 기반의 회원 레포지토리 역할을 수행함
@Repository // Spring MVC2 Repository                         // 스프링이 이 인터페이스를 Repository로 인식하도록 설정
public interface MemberEntityRepository                       // CS
        extends JpaRepository<MemberEntity, Integer> {        // JpaRepository<엔티티 클래스, PK 타입>

    // [5] MemberEntity → 매핑 대상 테이블 클래스 (회원 정보 엔티티)
    // [6] Integer      → 기본키 필드 mno의 자료형 (int → Integer로 지정)

    // [7] JpaRepository를 상속하면 기본 제공되는 메서드 예:
    // - findAll()                → 모든 회원 데이터 조회
    // - findById(Integer id)     → 특정 회원(PK 기준) 조회
    // - save(MemberEntity e)     → 회원 저장 또는 수정
    // - deleteById(Integer id)   → 회원 삭제
    // - count()                  → 전체 회원 수 조회

    // [8] 필요할 경우, 메서드 이름만으로 자동 생성되는 쿼리 메서드를 추가할 수 있음
    // 예: MemberEntity findByMemail(String memail);
    // 추상메소드를 이용한 memail로 entity를 조회하는 쿼리메소드
    MemberEntity findByMemail(String memail);
} // CE