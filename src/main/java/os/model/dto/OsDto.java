// OsDto 구성 | rw 25-04-10 생성
package os.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import os.model.entity.OsEntity;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OsDto {
    private int oid; // 비품 고유 식별자
    private String oname; // 비품명
    private String odescription; // 비품설명
    private int oqt; // 비품수량

    private LocalDateTime ocreateAt; // 비품 등록 일짜
    private LocalDateTime oupdateAt; // 비품 등록 수정 일짜

    // [1]. entity > dto 변환
    public OsEntity toEntity(){
        return OsEntity.builder()
                .oid(this.oid)
                .oname(this.oname)
                .odescription(this.odescription)
                .oqt(this.oqt)
                .build();
    }


}
