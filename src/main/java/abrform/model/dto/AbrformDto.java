// AbrformDto 구성 | rw 25-04-11 생성

package abrform.model.dto;

import abrform.model.entity.AbrformEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder // 룸북 어노테이션 사용
public class AbrformDto {

    // 등록
    private int aid;           // 책 등록번호 (식별자)
    private String atitle;     // 책 제목
    private String awriter;    // 책 저자
    private String acontent;   // 책 소개
    private String apwd;       // 수정·삭제를 위한 비밀번호

    // 생성일짜 및 수정일짜 주입
    private LocalDateTime createAt; // 생성 날짜 , 시간 주입
    private LocalDateTime updateAt; // 수정 날짜 , 시간 주입

    // dto --> entity 변환함수
    public AbrformEntity toEntity1(){
        return  AbrformEntity.builder()
                .aid(this.aid)
                .atitle(this.atitle)
                .awriter(this.awriter)
                .acontent(this.acontent)
                .apwd(this.apwd)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}
