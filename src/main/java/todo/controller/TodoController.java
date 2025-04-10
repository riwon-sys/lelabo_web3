package todo.controller;

import todo.model.dto.TodoDto;
import todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// | rw 25-04-07 생성
@RestController
@RequestMapping("/class04/todos")
@RequiredArgsConstructor
@CrossOrigin("*") // 플로터 dio (web) 테스트 용도
public class TodoController {

    private final TodoService todoService;

    // 1. C
    @PostMapping // http://localhost:8080/class04/todos

    public TodoDto todoSave(@RequestBody TodoDto todoDto ){
        return todoService.todoSave( todoDto );
    }

    // 2. R
    @GetMapping
    public List<TodoDto> todoFindAll( ){
        return todoService.todoFindAll();
    }

    // 2-2. R2
    @GetMapping("/view") // http://localhost:8080/class04/todos/view?id=1
    public TodoDto todoFindById( @RequestParam int id ){
        return todoService.todoFindById( id );
    }

    // 3. U
    @PutMapping
    // { "id" : "1" , "title" : "운동하기22" , "content" : "매일10분달리기22" , "done" : "true" }
    public TodoDto todoUpdate( @RequestBody TodoDto todoDto ){
        return todoService.todoUpdate( todoDto );
    }

    // 4. D
    @DeleteMapping // http://localhost:8080/class04/todos?id=1
    public boolean todoDelete( @RequestParam int id ){
        return todoService.todoDelete( id );
    }

    // 5. R3+PAGING
    @GetMapping("/paging") // http://localhost:8080/class04/todos/page?page=1&size=3
    public List<TodoDto>todoFindByPaging(
            // @ RequstParam (defaultValue = "초기값") : 만약에 매개변수값이 존재하지 않으면 초기값 대입
            @RequestParam(defaultValue = "1") int page , // page :현재 조회할 페이지 번호 , 초기값 1
            @RequestParam(defaultValue = "3") int size   // size :현재 조호할 페이지당 조회할 , 초기값 1
    )       {return todoService.todoFindByPaging( page , size);

            }

    // 6. R3   ( 입력한 값이 일치한 제목조회) title
    @GetMapping("/search1") // http://localhost:8080/class04/search1?title=운동하기
    public List<TodoDto> search1( @RequestParam String title ){
        return todoService.search1( title );
    }

    // 7. R3-2  ( 입력한 값이 포함된 제목 조회) keyword
    @GetMapping("/search2") // http://localhost:8080/class04/search2?keyword=운동
    public List<TodoDto>search2( @RequestParam String keyword ){
        return todoService.search2( keyword );
    }
} // class end