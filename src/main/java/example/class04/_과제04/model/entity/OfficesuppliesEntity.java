package example.class04._과제04.model.entity;

import example.class04._과제04.model.dto.OfficesuppliesDto;
import example.class04._과제04.model.entity.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// | rw 25-04-07 생성
@Entity // 데이터베이스의 테이블과 영속 관계
@Table( name = "officesupplies") // 데이터베이스의 테이블명 정의
@Data@NoArgsConstructor@AllArgsConstructor@Builder // 룸북
public class OfficesuppliesEntity extends BaseTime {
    @Id // pk 설정
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int id; //  식별번호

    @Column(nullable = false , length = 255) // not null , var(255) // 제목
    private String name; // 제품명


    @Column(nullable = false , length = 255) // not null , var(255) // 설명
    private String description; // 설명

    @Column(nullable = false)
    private int quantity; // 수량



    // dto --> entity 변환함수
    public OfficesuppliesDto toDto(){
        return OfficesuppliesDto.builder()
                .id( this.id )
                .name( this.name )
                .description( this.description )
                .quantity( this.quantity )
                .createAt( this.getCreateAt() ) // BaseTime
                .updateAt( this.getUpdateAt() ) // BaseTime
                .build();
    }
}
