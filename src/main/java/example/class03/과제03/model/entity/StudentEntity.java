package example.class03.과제03.model.entity;

import example.class03.과제03.model.dto.StudentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class03student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter@ToString
public class StudentEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private int sno; // 학생번호

    @Column(nullable = false , length = 255) // not null , var(255)
    private String sname; // 학생이름
    @Column(nullable = false , length = 255) // not null , var(255)
    private String ssub; // 학과
    @Column(nullable = false , length = 255) // not null , var(255)
    private String sdate; // 수강기간

    // FK 설정 | 단방향
    @ManyToOne
    private CourseEntity courseEntity;

    // + toDto
    public StudentDto toDto() {
        return StudentDto.builder()
                .sno(this.sno)
                .sname(this.sname)
                .ssub(this.ssub)
                .sdate(this.sdate)
                .build();
    }
}

/* 샘플 데이터 만들기
  [
  {
  "sname": "김스프링",
  "ssub": "자봐과",
  "sdate": "2025-03-01~25-06-30",
  "cno": 1
}
{
  "sname": "김자바",
  "ssub": "자봐과",
  "sdate": "2025-03-01~25-06-30",
  "cno": 1
}
{
  "sname": "김어노테이션",
  "ssub": "자봐과",
  "sdate": "2025-03-01~25-06-30",
  "cno": 2
}


  {
    "sno": "1",
    "sname": "김자바",
    "ssub": "자봐과",
    "sdate": "2025-03-01~25-06-30"
  },
  {
    "sno": "2",
    "sname": "김스프링",
    "ssub": "자봐과",
    "sdate": "2025-03-01~25-06-30"
  },
    {
    "sno": "3",
    "sname": "김어노테이션",
    "ssub": "자봐과",
    "sdate": "2025-03-01~25-06-30"
  }
     ]
*/
