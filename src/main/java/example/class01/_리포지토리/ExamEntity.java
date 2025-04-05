package example.class01._리포지토리;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "examstudent1") // DB테이블 매핑
public class ExamEntity {
    // 1. 학번
    @Id

    private String studentId;

    // 2. 이름
    @Column(nullable = false)
    private String studentName;

    // 3. 과목
    @Column
    private int subjectScore1;
    // 3. 과목
    @Column
    private int subjectScore2;
}
