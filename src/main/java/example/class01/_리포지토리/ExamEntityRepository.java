package example.class01._리포지토리;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 엔티티를 조작(DML : C R U D)하는 인터페이스
// 해당 인터페이스에 jpa엔티티 상속<조작할 클래스명,해당엔티티의 id타입> 상속
@Repository // 스프링에 컨테이너에 빈 등록
public interface ExamEntityRepository
        extends JpaRepository< ExamEntity , String> {

    }

    // crud 메소드
// 1. .save (저장할 엔티티 객체);
//          : 존재하지 않은 pk이면 insert , 존재하는 pk이면 update
// (*) 반환값 : insert /update 이후 영속(연결/매핑)된 객체(엔티티)

// 2. .findAll();
//           : 모든 엔티티를 .select 한다.
// (*) 반환값 : 리스트타입으로 반환된다.

// 3. .findById( 조회할 pk 값)
//           : pk값과 일치하는 엔티티를 select 한다.
// (*) 반환값 : Optional< 엔티티 >

// Optional 클래스 : null 관련된 메소드 제공하는 클래스
// -> nullPointerException 방지 하고자 객체를 포장하는 클래스
// 주요 메소드
// 1. .isPresent() : null 이면 false  , 객체이면 true
// 2. .get() : 객체 반환
// 3. .orElse ( null일때 값) : 객체 반환하는데 null이면 지정된 값 반환
// 4. .orElseThrow( 예외 객체) : 객체 반환하는데 null이면 예외 발생
