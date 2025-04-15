/*  AppStart 클래스 | rw 25-04-15 생성
   - 스프링부트 프로젝트의 시작점이며, 실행 시 서버를 구동하는 메인 클래스입니다.
   - 이 클래스가 실행되면 내장 Tomcat 서버가 함께 실행되며 전체 프로젝트가 시작됩니다.
*/

package web;

// [1] 스프링부트 애플리케이션의 기본 실행 설정을 위한 어노테이션
import org.springframework.boot.SpringApplication;               // 스프링부트를 실행하기 위한 메인 클래스
import org.springframework.boot.autoconfigure.SpringBootApplication; // 자동 설정을 활성화하는 핵심 어노테이션

// [2] JPA 기능 중 '생성일', '수정일'을 자동 기록하기 위한 기능 활성화
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // @CreatedDate, @LastModifiedDate 활성화

// [3] 주기적인 작업 실행을 위한 스케줄링 기능 활성화
import org.springframework.scheduling.annotation.EnableScheduling;       // @Scheduled 사용 가능하게 함

// [4] 이 클래스가 스프링부트 프로젝트의 시작점임을 명시
@SpringBootApplication    // 컴포넌트 스캔, 자동 설정, 설정 클래스 등록 포함 (3-in-1)

// [5] JPA의 엔티티 생성/수정 시간 자동 기록 기능을 사용 가능하게 함
@EnableJpaAuditing        // BaseTime 클래스에 설정된 @CreatedDate, @LastModifiedDate 동작

// [6] 주기적인 백그라운드 작업 등록을 허용
@EnableScheduling         // 예: 매일 자정마다 자동 백업 등의 작업 설정 가능

public class AppStart { // CS

    // [7] 자바 애플리케이션의 시작 지점 (main 메서드)
    public static void main(String[] args) { // fs
        // SpringApplication.run(): 스프링부트를 실행하는 정식 메서드
        SpringApplication.run(AppStart.class); // 이 클래스 기준으로 전체 프로젝트 시작
    } // fe

} // CE