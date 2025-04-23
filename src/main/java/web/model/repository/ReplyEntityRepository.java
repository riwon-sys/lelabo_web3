/*  ReplyEntityRepository 인터페이스 | rw 25-04-23 생성
    - 댓글(reply) 테이블과 관련된 DB 작업을 처리하는 레포지토리 인터페이스입니다.
    - JpaRepository를 상속받아 댓글 작성, 조회, 삭제 등 CRUD 기능을 사용할 수 있습니다.
    - 엔티티 클래스(ReplyEntity)와 기본키 타입(Long)을 명시합니다.
*/

package web.model.repository;

// [1] JPA의 기본 기능을 포함한 Repository 인터페이스 (CRUD 제공)
import org.springframework.data.jpa.repository.JpaRepository;

// [2] 스프링에서 이 인터페이스를 관리할 수 있도록 설정
import org.springframework.stereotype.Repository;

// [3] 연관 엔티티 클래스 import
import web.model.entity.ReplyEntity;


@Repository
public interface ReplyEntityRepository                    // CS
        extends JpaRepository<ReplyEntity, Long> {        // JpaRepository<엔티티, PK 타입>

    // [1] ReplyEntity → 댓글 정보를 담은 JPA 엔티티 클래스
    // [2] Long         → 기본키 필드 rno의 자료형

} // CE