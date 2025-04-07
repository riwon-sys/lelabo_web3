package example.class04._과제04.model.dto;

import example.class04._과제04.model.entity.OfficesuppliesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder

    // | rw 25-04-07 생성

    public class OfficesuppliesDto {
        private int id; // 식별번호
        private String name; // 제품명
        private String description; // 설명
        private int quantity; // 수량

        private LocalDateTime createAt; //  등록 날짜
        private LocalDateTime updateAt; //  수정 일짜

        // dto --> entity 변환함수
        public OfficesuppliesEntity toEntity(){
            return OfficesuppliesEntity.builder()
                    .id( this.id )
                    .name( this.name )
                    .description( this.description )
                    .quantity( this.quantity )
                    .build();
        }
    }

