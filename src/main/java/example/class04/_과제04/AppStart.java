package example.class04._과제04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// | rw 25-04-07 생성

@EnableJpaAuditing // JPA 영속성 감사 기능 활성화
@SpringBootApplication
public class AppStart {
    public static void main(String[] args) {
        // Spring Boot Application Start

            SpringApplication.run( example.class04._과제04.AppStart.class );

    }
}
