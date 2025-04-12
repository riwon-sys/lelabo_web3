// RbrformDto 구성 | rw 25-04-11 생성
package abrform.model.dto;


import abrform.model.entity.RbrformEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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

    private int aid; // 책 추천 글의 외래키로 연결되는 aid 값

    private MultipartFile file; // 실제 업로드된 파일 (서버 저장용)
    private String rimg;        // 서버에 저장된 파일명 (DB 저장용)

    // dto --> entity 변환함수
    public RbrformEntity toEntity2(){
        return RbrformEntity.builder()
                .rno(this.rno)
                .rtitle(this.rtitle)
                .rwriter(this.rwriter)
                .rcontent(this.rcontent)
                .rpwd(this.rpwd)

                .createAt(this.createAt)
                .updateAt(this.updateAt)

                .aid(this.aid)

                .rimg(this.rimg) // 파일명 포함
                .build();


    }


}
