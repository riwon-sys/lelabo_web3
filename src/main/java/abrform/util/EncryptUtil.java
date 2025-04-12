// EncryptUtil 클래스 구성 | rw 25-04-12 생성
package abrform.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// CS
public class EncryptUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // [1]. 평문 비밀번호 암호화 함수
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword); // BCrypt 암호화 실행
    }

    // [2]. 평문과 암호화된 비밀번호 비교 함수
    public static boolean match(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword); // 입력값 비교
    }
} // CE