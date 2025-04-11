// RbrformDto 구성 | rw 25-04-11 생성
package abrform.model.dto;

import abrform.model.entity.AbrformEntity;
import abrform.model.entity.RbrformEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 룸북 어노테이션 사용
public class RbrformDto {
    // 리뷰
    private int rno;           // 리뷰 등록한 번호 (식별자)
    private String rtitle;     // 리뷰 등록한 책 제목
    private String rwriter;    // 리뷰 등록한 책 저자
    private String rcontent;   // 리뷰 등록한 책 내용
    private String rpwd;       // 리뷰 수정 삭제를 위한 비밀번호

    // 생성일짜 및 수정일짜 주입
    private LocalDateTime createAt; // 생성 날짜 , 시간 주입
    private LocalDateTime updateAt; // 수정 날짜 , 시간 주입

    // dto --> entity 변환함수
    public RbrformEntity toEntity2(){
        return RbrformEntity.builder()
                .rno(this.rno)
                .rtitle(this.rtitle)
                .rwriter(this.rwriter)
                .rcontent(this.rcontent)
                .rpwd(this.rpwd)

                .build();


    }


}
