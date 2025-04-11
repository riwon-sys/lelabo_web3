package example.class02._BaseTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/thebook")
@RequiredArgsConstructor
@Data
public class BookController {

    private final BookService bookService;

    // c // http://localhost:8080/book/thebook // body: {"도서번호":"1","도서명":"자바의 정석 ","도서저자":"남궁성","도서출판사":"도우출판 ","출판일자":"2022-03-15"}
    @PostMapping
    public boolean post(@RequestBody BookEntity bookEntity){
        // 영속 전 | 매핑 전 BookEntity
        boolean result = bookService.post(bookEntity);
        return result;

    }
    // R
    @GetMapping // http://localhost:8080/book/thebook
    public List<BookEntity>get(){
        return bookService.get();


    /*[
  {
    "도서명": "자바의 정석",
    "도서저자": "남궁성",
    "도서출판사": "도우출판",
    "출판일자": "2022-03-15"
  },
  {
    "도서명": "스프링 부트 입문",
    "도서저자": "김영한",
    "도서출판사": "인프런",
    "출판일자": "2022-03-15"
  },
  {
    "도서명": "이펙티브 자바",
    "도서저자": "조슈아 블로크",
    "도서출판사": "인사이트",
    "출판일자": "2018-01-10"
  }
     ] */
    }
    // U
    @PutMapping
    public boolean put(@RequestBody BookEntity bookEntity){
        boolean result = bookService.put2(bookEntity);
        return  result;
    }
    // D
    @DeleteMapping // http://localhost:8080/book/thebook?id=1
    public boolean delete(@RequestParam int bid){
        boolean result = bookService.delete(bid);
        return  result;
    }

}
