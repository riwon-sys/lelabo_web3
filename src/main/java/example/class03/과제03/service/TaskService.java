package example.class03.과제03.service;

import example.class01._북리포지토리.BookEntityRepository;
import example.class03.과제03.model.dto.CourseDto;
import example.class03.과제03.model.dto.StudentDto;
import example.class03.과제03.model.entity.CourseEntity;
import example.class03.과제03.model.entity.StudentEntity;
import example.class03.과제03.model.repository.CourseEntityRepository;
import example.class03.과제03.model.repository.StudentEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final CourseEntityRepository courseEntityRepository;
    private final StudentEntityRepository studentEntityRepository;


    // C 과정 등록
    public boolean saveCourse(CourseDto courseDto) {
        System.out.println("TaskService.saveCourse");
        System.out.println("courseDto = " + courseDto);
        // 1. DTO -> ENTITY로 바꾸기
        CourseEntity courseEntity = courseDto.toEntity();
        // 2. ENTITY 를 .save 하기
        CourseEntity saveEntity = courseEntityRepository.save(courseEntity); // 영속된 객체
        // 3. result
        if (saveEntity.getCno() > 0) {
            return true;
        } // 만약에 영속된 결과 cno가 존재하면 성공
        return false; // 존재하지 않으면 실패!
    }

    // R 과정 전체 조회
    public List<CourseDto>findAll(){
        // 1. 모든 과정 조회
        List<CourseEntity>courseEntityList = courseEntityRepository.findAll();
        // 2. 모든 과정의 엔티티를 dto로 변환한다.
        List<CourseDto> courseDtoList = courseEntityList.stream()
                .map(entity->entity.toDto())
                .collect(Collectors.toList());
        // 3. result
        return courseDtoList;
    }

    // U 과정 개별 수정
    public boolean put1(CourseEntity courseEntity){
        courseEntityRepository.save(courseEntity);
        return true;
    }
    // U2 과정 개별 수정 세부내용
    public boolean put2(CourseEntity courseEntity){
        // cno entity
        Optional<CourseEntity>optionalCourseEntity = courseEntityRepository.findById(courseEntity.getCno());
        // entity 0 > isPresent()
        if(optionalCourseEntity.isPresent()){
            CourseEntity entity = optionalCourseEntity.get();
            entity.setCname(courseEntity.getCname()); // 과정명
            entity.setCtec(courseEntity.getCtec()); // 과정담당자
            entity.setCdate(courseEntity.getCdate()); // 과정기간
            return true;
        }
        return false;
    }
    // D 과정 개별 삭제
    public boolean delete(int cno){
        courseEntityRepository.deleteById(cno);
        System.out.println( courseEntityRepository.count());
        System.out.println( courseEntityRepository.existsById(cno));
        return true;
    }



    // C 특정 과정에 학생 등록
    public boolean saveStudent( StudentDto studentDto ){
        // 1. 학생Dto -> 학생Entity
        StudentEntity studentEntity = studentDto.toEntity();
        // 2. 학생Entity .save
        StudentEntity saveEntity = studentEntityRepository.save(studentEntity);
        if( saveEntity.getSno()<1)return false;
        // 3. 특정한 과정의 Entity 조회 , cno를 이용하여 과정Entity 찾기
        CourseEntity courseEntity = courseEntityRepository.findById( studentDto.getCno() ).orElse( null );
        if(courseEntity ==null)return false;
        // 4. 등록된 학생의 Entity의 특정한 과정의 Entity 를 대입 <FK대입>
        saveEntity.setCourseEntity(courseEntity); // 단방향 멤버변수에 과정 엔티티 대입하기
        // 5. result
        return true;
    }

    // R 특정 과정에 등록한 학생 전체 조회
    public List<StudentDto>findAllStudent(int cno){
        // 1. cno를 이용하여 과정관련 엔티티 찾기
        CourseEntity courseEntity = courseEntityRepository.findById(cno).orElse(null);
        if(courseEntity ==null)return null;
        // 2. 조회한 과정 엔티티 안에서 참조중인 학생목록
        List<StudentEntity> studentEntityList = courseEntity.getStudentEntityList();
        // 3. Entity List -> DTO List
        List<StudentDto> studentDtoList = studentEntityList.stream()
               .map(entity->entity.toDto())
               .collect(Collectors.toList());
        // 4. result
        return studentDtoList;
    }

    // U 특정 과정에 등록한 학생 개별 수정
    public boolean put (StudentEntity studentEntity){
        studentEntityRepository.save(studentEntity);
        return true;
    }

    // U2 특정 과정에 등록한 학생 개별 수정
    public boolean put2(StudentEntity studentEntity) {
        // 1. id entity
        Optional<StudentEntity> optionalStudentEntity = studentEntityRepository.findById(studentEntity.getSno());
        // 2. entity 0 > isPresent()
        if (optionalStudentEntity.isPresent()) {
            // 3. Optional >entity 0
            StudentEntity entity = optionalStudentEntity.get();
            entity.setSname(studentEntity.getSname()); // 학생이름
            entity.setSsub(studentEntity.getSsub()); // 학과
            entity.setSdate(studentEntity.getSdate()); // 수강기간
            return true;
        }
        return false;
    }
    // D
    public boolean delete2(int sno){
        studentEntityRepository.deleteById(sno);
        System.out.println( studentEntityRepository.count());
        System.out.println(studentEntityRepository.existsById(sno));

        return true;
    }
}
