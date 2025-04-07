package example.class04._todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// | rw 25-04-07 생성
@SpringBootApplication
@EnableJpaAuditing // JPA 영속성 감사 기능 활성화
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run( AppStart.class );
    }
}