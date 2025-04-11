package example.class02._BaseTime;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table( name="class02book")
@Data
public class BookEntity extends BaseTime { // Entity  클래스에서 BaseTime 엔티티를 사용하겠다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int 도서번호;

    @Column(nullable = false , length = 100) // not null , var(100)
    private String 도서명;

    @Column(nullable = false , length = 100) // not null , var(100)
    private String 도서저자;

    @Column(nullable = false , length = 100) // not null , var(100)
    private String 도서출판사;

    @Column
    private LocalDate 출판일자;

}
