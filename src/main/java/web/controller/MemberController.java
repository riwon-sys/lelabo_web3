/*  MemberController 클래스 | rw 25-04-15 생성
   - 클라이언트의 HTTP 요청을 받아 처리하는 API 진입 지점입니다.
   - 회원 관련 요청을 전담하는 컨트롤러 클래스입니다.
   - REST 방식에 맞추어 JSON 데이터를 주고받습니다.
*/

package web.controller;

// [*] 의존성 자동 주입과 관련된 롬복 어노테이션
import lombok.RequiredArgsConstructor;                  // final 필드에 대해 생성자 자동 생성

// [*] 스프링 웹 MVC 관련 어노테이션
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;       // 컨트롤러, 요청 매핑, CORS, 요청 데이터 처리 등

// [*] 데이터와 서비스 계층에서 사용하는 클래스들
import web.model.dto.MemberDto;                         // 회원 정보를 담는 데이터 전송 객체
import web.model.entity.MemberEntity;                   // 회원 정보를 DB에 저장하는 JPA 클래스 (참고용)
import web.service.MemberService;                       // 회원 로직 처리를 위한 서비스 클래스

// [*] 빈 생성 주입 어노테이션
import org.springframework.beans.factory.annotation.Autowired;

// [*] REST 방식의 컨트롤러임을 명시
@RestController                                          // JSON 응답 전용 컨트롤러 (MVC View 사용 안 함)

// [*] 공통 URL prefix 설정 (예: "/member/signup")
@RequestMapping("/member")                               // 모든 요청 경로 앞에 "/member"가 붙음

// [*] final 필드를 사용하는 경우, 생성자 주입을 자동 생성해주는 어노테이션
@RequiredArgsConstructor                                 // final 필드에 대한 생성자를 자동 생성하여 주입 처리

// [*] CORS 허용 설정 (모든 출처 허용)
@CrossOrigin("*")                                       // 프론트엔드 도메인에서의 요청 허용 (예: React, Flutter 등)

// 컨트롤러 클래스 시작
public class MemberController { // CS

    // [&] 서비스 객체 주입
    // - 관례적으로 외부 객체는 final 키워드를 사용하여 수정 불가하게 설정
    // - @RequiredArgsConstructor로 인해 생성자 자동 주입됨 → @Autowired 생략 가능
    private final MemberService memberService;

/* [회원가입 기능] 초기 구성 | rw 25-04-15 생성 rw 25-04-21 소멸
// [1] 회원가입 기능 처리 메서드
// - 프론트엔드에서 전송한 JSON 데이터를 @RequestBody로 받아 Dto에 매핑
// - 예시 요청 JSON: { "memail": "qwe@naver.com", "mpwd": "qwe", "mname": "상하이" }
// - 예시 요청 JSON: { "memail": "qwer@naver.com", "mpwd": "qwer", "mname": "맥스파이시" }
// - 예시 요청 JSON: { "memail": "qwert@naver.com", "mpwd": "qwert", "mname": "더블버거" }

@PostMapping("/signup") // - /member/signup
public boolean signUp(@RequestBody MemberDto memberDto) { // fs
return memberService.signUp(memberDto); // 서비스 계층에 로직 위임하여 처리
} // fe
*/
    // [1] 회원가입 기능 처리 메서드 (상태 코드 반환 구조 적용) API | rw 25-04-21 생성
    // - 클라이언트로부터 받은 JSON 데이터를 처리하고, HTTP 상태코드를 함께 반환
    // - 예시 요청 JSON: { "memail" : "qwe@naver.com", "mpwd" : "qwe", "mname" : "유재석" }

    @PostMapping("/signup") // - /member/signup
    public ResponseEntity<Boolean> signUp(@RequestBody MemberDto memberDto) { // fs
        boolean result = memberService.signUp(memberDto); // 회원가입 결과 처리

        if (result) {
            return ResponseEntity.status(201).body(true);  // 201: Created 성공 응답
        } else {
            return ResponseEntity.status(400).body(false); // 400: Bad Request 실패 응답
        }
    } // fe

/*  [로그인 기능] 초기 구성 | rw 25-04-15 생성 rw 25-04-21 소멸
// [2] 로그인 기능 처리 메서드
// - 예시 요청 JSON: { "memail": "qwe@naver.com", "mpwd": "qwe" }
// - 예시 요청 JSON: { "memail": "qwer@naver.com", "mpwd": "qwer" }
// - 예시 요청 JSON: { "memail": "qwert@naver.com", "mpwd": "qwert" }
@PostMapping("/login") // - /member/login
public String login(@RequestBody MemberDto memberDto) {
    return memberService.login(memberDto);
}
*/

    // [2] 로그인 기능 처리 메서드 (토큰 + 상태 코드 응답 구조) API | rw 25-04-21 생성
    // - 로그인 성공 시 JWT 토큰 반환
    // - 로그인 실패 시 401 Unauthorized 반환
    // - 예시 요청 JSON: { "memail" : "qwe@naver.com", "mpwd" : "qwe" }

    @PostMapping("/login") // http://localhost:8080/member/login
    public ResponseEntity<String> login(@RequestBody MemberDto memberDto) { // fs
        String token = memberService.login(memberDto); // 로그인 시 토큰 반환

        if (token != null) {
            return ResponseEntity.status(200).body(token); // 200 OK: 로그인 성공
        } else {
            return ResponseEntity.status(401).body("로그인 실패"); // 401 Unauthorized: 로그인 실패
        }
    } // fe

/* [내정보 조회 기능] 초기 구성 | rw 25-04-15 생성 rw 25-04-21 소멸
// [3] 로그인된 회원 검증 / 내정보 조회 기능 처리 메서드
// @RequestHeader : HTTP 헤더 정보를 매핑 하는 어노테이션 , JWT 정보는 HTTP 헤더 에 담을 수 있다.
// Authorization : 인증 속성 , { Authorization : token값 }
// @RequestParam : HTTP 헤더의 경로 쿼리스트링 매핑 하는 어노테이션
// @RequestBody : HTTP 본문의 객체를 매핑 하는 어노테이션
// @PathVariable : HTTP 헤더의 경로 값 매핑 하는 어노테이션
@GetMapping("/info") // - /member/info
// headers : { 'Authorization' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VAbmF2ZXIuY29tIiwiaWF0IjoxNzQ0NzcxNTM0LCJleHAiOjE3NDQ4NTc5MzR9.g8sM_lX31AgbILTQMJXGEzX5K2F6Z6ak-mBweZmpM-I'}
public MemberDto info(@RequestHeader("Authorization") String token) {
System.out.println(token); return memberService.info(token); }
*/



    // [3] 로그인된 회원 검증 / 내정보 조회 기능 처리 메서드 (상태코드 반환 구조) API | rw 25-04-21 생성
    // - JWT 토큰을 헤더에서 받아 사용자 정보를 조회하고 상태코드와 함께 응답
    // - 200 OK: 조회 성공 / 403 Forbidden: 토큰이 유효하지 않거나 조회 실패
    // - headers 예시: { "Authorization": "Bearer eyJhbGciOiJI..." }

    @GetMapping("/info") // http://localhost:8080/member/info
    public ResponseEntity<MemberDto> info(@RequestHeader("Authorization") String token) { // fs
        System.out.println(token); // 토큰 디버깅 출력

        MemberDto memberDto = memberService.info(token); // 서비스로부터 사용자 정보 조회

        if (memberDto != null) {
            return ResponseEntity.status(200).body(memberDto); // 200 OK + 사용자 정보
        } else {
            return ResponseEntity.status(403).build(); // 403 Forbidden (자료 없음)
        }
    } // fe

/* [로그아웃 기능] 초기 구성 | rw 25-04-15 생성 rw 25-04-21 소멸
// [4] 로그아웃 , 로그아웃 할 토큰 가져오기. 기능 처리 메서드
@GetMapping("/logout") // - /member/logout
public void logout(
@RequestHeader("Authorization") String token ) {
memberService.logout( token );
}
*/
    // [4] 로그아웃 , 로그아웃 할 토큰 가져오기 API | rw 25-04-21 생성
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) { // fs
    memberService.logout(token);
    return ResponseEntity.status(204).build(); // 204: 성공했으나 반환값 없음
    } // fe


/* [최근 24시간 로그인 수 조회 기능] 초기 구성 | rw 25-04-15 생성 rw 25-04-21 소멸
// [5] 최근 24시간 로그인 된 접속자 수 기능 처리 메서드
@GetMapping("/login/count") // - /member/login/count
public int loginCount(){
return memberService.loginCount();
}
*/

    // [5] 실시간 최근 24시간내 로그인 한 접속자 수 API | rw 25-04-21 생성
    @GetMapping("/login/count")
    public ResponseEntity<Integer> loginCount() { // fs
        int result = memberService.loginCount();
        return ResponseEntity.status(200).body(result); // 200 OK + 응답값
    } // fe



} // CE