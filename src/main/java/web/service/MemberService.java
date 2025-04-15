package web.service;

/*  MemberService 클래스 | rw 25-04-15 생성
   - 회원 관련 로직을 처리하는 서비스 계층입니다.
   - 컨트롤러와 리포지토리 사이에서 비즈니스 로직을 수행합니다.
   - 회원가입 시 비밀번호 암호화, DTO → Entity 변환, 저장 처리 등을 수행합니다.
*/

// [1] 트랜잭션 기능 (실패 시 롤백) 제공
import jakarta.transaction.Transactional;                        // 하나의 작업 단위를 보장 (실패 시 전체 취소)

// [2] 생성자 자동 주입을 위한 롬복 어노테이션
import lombok.RequiredArgsConstructor;                          // final 필드를 자동 생성자로 주입

// [3] 암호화 기능 제공 라이브러리 (스프링 시큐리티)
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 비밀번호 암호화 알고리즘

// [4] 서비스 계층 어노테이션
import org.springframework.stereotype.Service;                  // 서비스 계층임을 명시하는 어노테이션

// [5] DTO와 Entity, Repository import
import web.model.dto.MemberDto;                                 // 클라이언트 → 서버 전달 데이터 객체
import web.model.entity.MemberEntity;                           // DB 저장용 JPA 엔티티 클래스
import web.model.repository.MemberEntityRepository;             // DB 작업을 위한 리포지토리

@Service                                                        // 이 클래스가 서비스 역할을 수행함을 명시
@RequiredArgsConstructor                                         // final 필드 생성자 자동 주입 (@Autowired 생략 가능)
@Transactional                                                  // 트랜잭션 처리: 하나라도 실패하면 전체 취소
public class MemberService { // CS

    // [6] DB 저장/조회 기능을 담당하는 Repository 객체 주입
    private final MemberEntityRepository memberEntityRepository;

    // [7] 회원가입 처리 메서드
    public boolean signUp(MemberDto memberDto) { // fs

        // [7-1] 비밀번호 암호화 처리
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 암호화 도구 생성
        String hashedPwd = passwordEncoder.encode(memberDto.getMpwd());      // 평문 비밀번호 → 암호문으로 인코딩
        memberDto.setMpwd(hashedPwd);                                        // 암호화된 비밀번호로 덮어쓰기

        // [7-2] DTO → Entity 변환
        MemberEntity memberEntity = memberDto.toEntity();                    // Entity로 변환 (DB 저장용)

        // [7-3] Repository를 통해 Entity 저장
        MemberEntity saveEntity = memberEntityRepository.save(memberEntity); // 저장 후 영속된 Entity 반환

        // [7-4] 저장 결과 확인: 자동 생성된 PK가 1 이상이면 성공
        if (saveEntity.getMno() >= 1) {                                      // 성공 시 true 반환
            return true;
        }
        return false;                                                        // 실패 시 false 반환
    } // fe

} // CE