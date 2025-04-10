package os;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// JAP06 비품 관리 앱 화면 구현 | rw 25-04-10 생성
@SpringBootApplication
@EnableJpaAuditing // JPA 영속성 감사 기능 활성화
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run( os.AppStart.class );
    }
}