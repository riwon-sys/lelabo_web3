// RbrformEntity 구성 | rw 25-04-11 생성
package abrform.model.entity;

import abrform.model.dto.RbrformDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity

@Table(name = "rbrform")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 룸북 어노테이션 적용
public class RbrformEntity {
    // 리뷰
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_incre
    private int rno; // 리뷰 등록한 번호 (식별자)

    @Column(nullable = false,length = 255) // not null ,var(255)
    private String rtitle;     // 리뷰 등록한 책 제목
    @Column(nullable = false,length = 255) // not null ,var(255)
    private String rwriter;    // 리뷰 등록한 책 저자
    @Column(nullable = false,length = 255) // not null ,var(255)
    private String rcontent;   // 리뷰 등록한 책 내용
    @Column(nullable = false,length = 255) // not null ,var(255)
    private String rpwd;       // 리뷰 수정 삭제를 위한 비밀번호

    @Column
    private LocalDateTime createAt; // 생성 날짜 , 시간 주입

    @Column
    private LocalDateTime updateAt; // 수정 날짜 , 시간 주입

    @Column(nullable = true, length = 255)
    private String rimg; // 업로드된 파일명

    private int aid; // 책 추천 글의 외래키로 연결되는 aid 값

    // entity --> dto 변환함수
    public RbrformDto toDto2(){
        return RbrformDto.builder()
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
