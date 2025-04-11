package example.class01._엔티티;

import jakarta.persistence.*;

@Entity
@Table( name = "exam2")
public class ExamEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column( nullable = true , unique = false )
    private String col1;

    @Column( nullable = false , unique = true )
    private String col2;

    @Column( columnDefinition = "longtext")
    private String col3;

}
