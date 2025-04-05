package example.class01._엔티티;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "examentitytest")

public class ExamEntityTest {
    @Id // (pk)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private int id;

    @Column(nullable = false , length =100) //not null , var(100)
    private String title;

    @Column(nullable = false)
    private boolean state;

    @Column(nullable = false)
    private LocalDate createat;
    @Column(nullable = false)
    private LocalDateTime updateat;


}
