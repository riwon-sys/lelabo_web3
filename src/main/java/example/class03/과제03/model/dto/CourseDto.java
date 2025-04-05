package example.class03.과제03.model.dto;

import example.class03.과제03.model.entity.CourseEntity;
import lombok.Builder;
import lombok.Data;

@Data@Builder
public class CourseDto {
    private int cno;
    private String cname;
    private String ctec;
    private String cdate;

    //+ toEntity
    public CourseEntity toEntity() {
        return CourseEntity.builder()
                .cno(this.cno)
                .cname(this.cname)
                .ctec(this.ctec)
                .cdate(this.cdate)
                .build();
    }
}
