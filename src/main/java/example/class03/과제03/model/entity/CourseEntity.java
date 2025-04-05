package example.class03.과제03.model.entity;

import example.class03.과제03.model.dto.CourseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity@Table(name = "class03course")
@Data@Builder
@NoArgsConstructor
@AllArgsConstructor

@Setter@Getter@ToString

public class CourseEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private int cno; // 과정번호

    @Column(nullable = false , length = 255) // not null , var(255)
    private String cname; // 과정명
    @Column(nullable = false , length = 255) // not null , var(255)
    private String ctec; // 과정담당자
    @Column(nullable = false , length = 255) // not null , var(255)
    private String cdate; // 과정기간

    //양방향
    @ToString.Exclude // 순환참조 방지
    @Builder.Default // 해당 엔티티를 빌더패턴에 생성 할 경우 초기값을 사용
    @OneToMany(mappedBy ="courseEntity", cascade = CascadeType.ALL)
    private List<StudentEntity>studentEntityList = new ArrayList<>();

    // + toDto
    public CourseDto toDto() {
        return CourseDto.builder()
                .cno(this.cno)
                .cname(this.cname)
                .ctec(this.ctec)
                .cdate(this.cdate)
                .build();
    }
}

 /* 샘플 데이터 만들기
  [
  {
    "cno": "1",
    "cname": "자바의정석",
    "ctec": "남궁민",
    "cdate": "2025-03-01~25-06-30"
  },
  {
    "cno": "2",
    "cname": "스프링의정석",
    "ctec": "조슈아 블로크",
    "cdate": "2025-03-01~25-06-30"
  },
  {
    "cno": "3",
    "cname": "어노테이션달인",
    "ctec":"김현수",
    "cdate": "2025-03-01~25-06-30"
  }
     ] */
