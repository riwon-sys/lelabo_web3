package example.class01._엔티티;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ExamEntity1 {
    @Id
    private  int col1;
    private String col2;
    private double col3;
}
