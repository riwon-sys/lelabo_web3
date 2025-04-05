package example.class03._북엔티티;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/entity")
@RequiredArgsConstructor
@Data
public class BookController {

    private final BookService bookService;

    // c // http://localhost:8080/book/entity
    @PostMapping
    public boolean post (@RequestBody BookEntity bookEntity){
        // 영속 전 | 매핑전 BookEntity
        boolean result = bookService.post(bookEntity);
                return result;
    }
    // r
    @GetMapping // http://localhost:8080/book/entity
    public List<BookEntity>get(){
        return bookService.get();
    }

    // u
    @PutMapping
    public boolean put(@RequestBody BookEntity bookEntity){
        boolean result = bookService.put2(bookEntity);
        return result;
    }

    // d
    @DeleteMapping // http://localhost:8080/book/entity?bid=1
    public boolean delete(@RequestParam int bid){
        boolean result = bookService.delete(bid);
        return  result;
    }



}
