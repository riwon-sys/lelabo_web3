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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 비밀번호 암호화 알고리즘

// [4] 서비스 계층 어노테이션
import org.springframework.stereotype.Service;                  // 서비스 계층임을 명시하는 어노테이션

// [5] DTO와 Entity, Repository import
import web.model.dto.MemberDto;                                 // 클라이언트 → 서버 전달 데이터 객체
import web.model.entity.MemberEntity;                           // DB 저장용 JPA 엔티티 클래스
import web.model.repository.MemberEntityRepository;             // DB 작업을 위한 리포지토리
import web.util.JwtUtil;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    // * JWT 객체 주입
    private final JwtUtil jwtUtil;

    // [2] 로그인 , 로그인 성공시 token 실패시 null
    public String login( MemberDto memberDto ){
        // 1. 이메일(아이디)를 DB에서 조회하여 엔티티 찾기
        MemberEntity memberEntity
                = memberEntityRepository.findByMemail( memberDto.getMemail() );
        // 2. 조회된 엔티티가 없으면
        if( memberEntity == null ){return null;} // 로그인 실패
        // 3. 조회된 엔티티의 비밀번호 검증.  .matches( 입력받은패스워드 , 암호화된패스워드 )
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();// Bcrypt 객체 생성
        boolean inMath
                = passwordEncoder.matches( memberDto.getMpwd() , memberEntity.getMpwd() );
        // 4. 비밀번호 검증 실패이면
        if( inMath == false ) return null; // 로그인 실패
        // 5. 비밀번호 검증 성공이면 Token 발급 vs 세션 부여/발급
        String token
                = jwtUtil.createToken( memberEntity.getMemail() );
        System.out.println( ">>발급된 token : " + token );

        // [5-2]  Redis에 실시간 24시간만 저장되는 로그인 로그(기록) 하기
        stringRedisTemplate.opsForValue().set(
                "RESENT_LOGIN:"+memberEntity.getMemail(),"true" ,1, TimeUnit.DAYS
        );
        return token;
    }

    // [3] 전달받은 token 으로 token 검증하여 유효한 token 은 회원정보(dto) 반환 유효하지 않은 token null 반환
    public MemberDto info(  String token ){
        // 1. 전달받은 token 으로 검증하기. vs 세션 호출/검증
        String memail = jwtUtil.validateToken( token );
        // 2. 검증이 실패이면 유효기간 만료 , 실패
        if( memail == null ) return null;
        // 3. 검증이 성공이면 토큰에 저장된 이메일을 가지고 엔티티 조회
        MemberEntity memberEntity
                = memberEntityRepository.findByMemail( memail );
        // 4. 조회된 엔티티가 없으면 실패
        if( memberEntity == null ) return null;
        // 5. 조회 성공시 조회된 엔티티를 dto로 변환하여 반환한다.
        return memberEntity.toDto();
    }

    // [4] 로그아웃
    public void logout( String token ){
        // 1. 해당 token의 이메일 조회
        String memail = jwtUtil.validateToken( token );
        // 2. 조회된 이메일의 redis 토큰 삭제
        jwtUtil.deleteToken( memail );
    }
    // [5-1] 최근 24시간 로그인 된 접속자 수
    @Autowired
    private final StringRedisTemplate stringRedisTemplate;
    // [5-2] 최근 24시간 로그인 된 접속자 수
    public int loginCount(){
        // (1) - Redis에 저장된 키 들 중에서 "RESENT_LOGIN:"으로 시작되는 모든 KEY 반환
        Set<String> keys=
                stringRedisTemplate.keys("RESENT_LOGIN:*");
        // (2) - 반환한 개수 확인 , 비어있으면 0이고 , 아니면 size() 함수 이용한 KEY 개수 반환
        return keys==null?0 : keys.size();
    }


} // CE