package example.class03._북엔티티;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bookentitytest")

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

    @Column(nullable = false , length = 255) // not null , var(255) // 출판연도
    private String bdate;
}

  /* 샘플 데이터 만들기
  [
  {
    "btitle": "자바의 정석",
    "bwriter": "남궁성",
    "bcompany": "도우출판",
    "bdate": "2025-04-03"
  },
  {
    "btitle": "스프링 부트 입문",
    "bwriter": "김영한",
    "bcompany": "인프런",
    "bdate": "2025-04-03"
  },
  {
    "btitle": "이펙티브 자바",
    "bwriter": "조슈아 블로크",
    "bcompany": "인사이트",
    "bdate": "2025-04-03"
  }
     ] */