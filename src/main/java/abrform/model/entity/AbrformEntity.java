// AbrformEntity 구성 | rw 25-04-11 생성

package abrform.model.entity;

import abrform.model.dto.AbrformDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity

@Table(name ="abrform")
@Data @NoArgsConstructor @AllArgsConstructor @Builder // 룸북 어노테이션 적용
public class AbrformEntity {
    // 등록
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_incre
    private int aid; // 책 등록번호 (식별자)

    @Column(nullable = false,length = 255) // not null ,var(255)
    private String atitle;     // 책 제목
    @Column(nullable = false,length = 255) // not null ,var(255)
    private String awriter;    // 책 저자
    @Column(nullable = false,length = 255) // not null ,var(255)
    private String acontent;   // 책 소개
    @Column(nullable = false,length = 255) // not null ,var(255)
    private String apwd;       // 수정·삭제를 위한 비밀번호

    @Column
    private LocalDateTime createAt; // 생성 날짜 , 시간 주입

    @Column
    private LocalDateTime updateAt; // 수정 날짜 , 시간 주입

    // entity --> dto 변환함수
    public AbrformDto toDto1(){
        return AbrformDto.builder()
                .aid(this.aid)
                .atitle(this.atitle)
                .awriter(this.awriter)
                .acontent(this.acontent)
                .apwd(this.apwd)

                .createAt(this.createAt)
                .updateAt(this.updateAt)
                .build();
    }
}
