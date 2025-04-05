package example.class03.과제03.controller;

import example.class03.과제03.model.dto.CourseDto;
import example.class03.과제03.model.dto.StudentDto;
import example.class03.과제03.model.entity.CourseEntity;
import example.class03.과제03.model.entity.StudentEntity;
import example.class03.과제03.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/class03/task")
public class TaskController {
    private final TaskService taskService;
    // C 과정 등록
    @PostMapping("/course") // http://localhost:8080/class03/task/course
    public boolean saveCourse( @RequestBody CourseDto courseDto ){
        System.out.println("TaskController.saveCourse");
        System.out.println("courseDto = " + courseDto);
        return taskService.saveCourse(courseDto);
    }

    // R 과정 전체 조회
    @GetMapping("/course") // http://localhost:8080/class03/task/course
    public List<CourseDto>findAll(){
        System.out.println("TaskController.findAll");
        return taskService.findAll();
    }

    // U 과정 개별 수정
    @PutMapping("/update")// http://localhost:8080/class03/task/update
    public boolean put1(@RequestBody CourseEntity courseEntity){
        boolean result = taskService.put2(courseEntity);
        return result;
    }

    // D 과정 개별 삭제
    @DeleteMapping ("/delete")  // http://localhost:8080/class03/task/delete?cno=3
    public boolean delete(@RequestParam int cno){
        boolean reusult = taskService.delete(cno);
        return reusult;
    }

    // C 특정 과정에 학생 등록
    @PostMapping("/student") // http://localhost:8080/class03/task/student
    public boolean saveStudent(@RequestBody StudentDto studentDto){
        return taskService.saveStudent(studentDto);
    }

    // R 특정 과정에 등록한 학생 전체 조회
    @GetMapping("/student") // http://localhost:8080/class03/task/student?cno=1
    public List<StudentDto> findAllStudent( @RequestParam int cno ){
        return taskService.findAllStudent( cno );
    }

    // U 특정 과정에 등록한 학생 수정
    @PutMapping("/student") // http://localhost:8080/class03/task/student
    public boolean put(@RequestBody StudentEntity studentEntity){
        boolean result = taskService.put2(studentEntity);
        return result;
    }

    // D 특정 과정에 등록한 학색 삭제
    @DeleteMapping("/student") // http://localhost:8080/class03/task/student?sno=1

    public boolean deleteStudent(@RequestParam int sno){

        boolean result2 = taskService.delete(sno);
        return result2; // 둘 다 성공해야 true 반환
    }
}
