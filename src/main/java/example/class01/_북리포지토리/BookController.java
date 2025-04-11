package example.class01._북리포지토리;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/manage")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // C
    @PostMapping // http://localhost:8080/book/manage // body: {"bid":"1","btitle":"자바의 정석 ","bwriter":"남궁성","bcompany":"도우출판 ","bbrither":"2022-03-15"}
    public boolean post(@RequestBody BookEntity bookEntity){
        // 영속 전 | 매핑 전 BookEntity
        boolean result = bookService.post(bookEntity);
        return result;

    }

    // R
    @GetMapping // http://localhost:8080/book/manage
    public List<BookEntity>get(){
        return bookService.get();


    /*[
  {
    "btitle": "자바의 정석",
    "bwriter": "남궁성",
    "bcompany": "도우출판",
    "bbirth": "2022-03-15"
  },
  {
    "btitle": "스프링 부트 입문",
    "bwriter": "김영한",
    "bcompany": "인프런",
    "bbirth": "2022-03-15"
  },
  {
    "btitle": "이펙티브 자바",
    "bwriter": "조슈아 블로크",
    "bcompany": "인사이트",
    "bbirth": "2018-01-10"
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
    @DeleteMapping // http://localhost:8080/book/manage?id=1
    public boolean delete(@RequestParam int bid){
        boolean result = bookService.delete(bid);
        return  result;
    }



}
