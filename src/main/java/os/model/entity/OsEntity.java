// OsEntity 구성 | rw 25-04-10 생성
package os.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import os.model.dto.OsDto;

@Entity
@Table(name ="os")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OsEntity extends BaseTime {
    @Id // pk
    @GeneratedValue( strategy =  GenerationType.IDENTITY) // auto_incre
    private int oid; // 비품 고유 식별자

    @Column(nullable = false , length = 255) // not null , var(255)
    private String oname; // 비품명

    @Column(nullable = false , length = 255) // not null , var(255)
    private String odescription; // 비품설명

    @Column(nullable = false ) // not null
    private int oqt; // 비품수량


    // [1]. dto > entity 변환
    public OsDto toDto(){
        return OsDto.builder()
                .oid(this.oid)
                .oname(this.oname)
                .odescription(this.odescription)
                .oqt(this.oqt)
                .ocreateAt(this.getOcraetAt()) // BaseTime
                .oupdateAt(this.getOupdateAt()) // BaseTime
                .build();
    }


}
