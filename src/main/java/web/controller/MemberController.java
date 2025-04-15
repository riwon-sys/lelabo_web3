/*  MemberController 클래스 | rw 25-04-15 생성
   - 클라이언트의 HTTP 요청을 받아 처리하는 API 진입 지점입니다.
   - 회원 관련 요청을 전담하는 컨트롤러 클래스입니다.
   - REST 방식에 맞추어 JSON 데이터를 주고받습니다.
*/

package web.controller;

// [1] 의존성 자동 주입과 관련된 롬복 어노테이션
import lombok.RequiredArgsConstructor;                  // final 필드에 대해 생성자 자동 생성

// [2] 스프링 웹 MVC 관련 어노테이션
import org.springframework.web.bind.annotation.*;       // 컨트롤러, 요청 매핑, CORS, 요청 데이터 처리 등

// [3] 데이터와 서비스 계층에서 사용하는 클래스들
import web.model.dto.MemberDto;                         // 회원 정보를 담는 데이터 전송 객체
import web.model.entity.MemberEntity;                   // 회원 정보를 DB에 저장하는 JPA 클래스 (참고용)
import web.service.MemberService;                       // 회원 로직 처리를 위한 서비스 클래스

// [4] REST 방식의 컨트롤러임을 명시
@RestController                                          // JSON 응답 전용 컨트롤러 (MVC View 사용 안 함)

// [5] 공통 URL prefix 설정 (예: "/member/signup")
@RequestMapping("/member")                               // 모든 요청 경로 앞에 "/member"가 붙음

// [6] final 필드를 사용하는 경우, 생성자 주입을 자동 생성해주는 어노테이션
@RequiredArgsConstructor                                 // final 필드에 대한 생성자를 자동 생성하여 주입 처리

// [7] CORS 허용 설정 (모든 출처 허용)
@CrossOrigin("*")                                       // 프론트엔드 도메인에서의 요청 허용 (예: React, Flutter 등)

// 컨트롤러 클래스 시작
public class MemberController { // CS

    // [8] 서비스 객체 주입
    // - 관례적으로 외부 객체는 final 키워드를 사용하여 수정 불가하게 설정
    // - @RequiredArgsConstructor로 인해 생성자 자동 주입됨 → @Autowired 생략 가능
    private final MemberService memberService;

    // [9] 회원가입 기능 처리 메서드
    // - 프론트엔드에서 전송한 JSON 데이터를 @RequestBody로 받아 Dto에 매핑
    // - 예시 요청 JSON: { "memail": "qwe@naver.com", "mpwd": "qwe", "mname": "유재석" }
    // - http://localhost:8080/member/signup
    @PostMapping("/signup")                             // HTTP POST 요청, URL: /member/signup
    public boolean signUp(@RequestBody MemberDto memberDto) { // fs
        return memberService.signUp(memberDto);         // 서비스 계층에 로직 위임하여 처리
    } // fe

} // CE