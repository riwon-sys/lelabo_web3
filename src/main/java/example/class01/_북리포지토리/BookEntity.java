package example.class01._북리포지토리;


import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "bookmanagertest")

@Data //룸복
public class BookEntity {
    @Id //(pk)
    @GeneratedValue(strategy = GenerationType.IDENTITY)// AUTO-INCREMENT
    private int bid; // id
    
    @Column(nullable = false , length = 255) // not null , var(255) // 제목
    private String btitle;
    
    @Column(nullable = false , length = 255) // not null , var(255) // 저자
    private String bwriter;
    
    @Column(nullable = false , length = 255) // not null , var(255) // 출판사
    private String bcompany;

    @Column(nullable = false , length = 255) // not null , var(255) // 출판일자
    private String bbirth;
}
