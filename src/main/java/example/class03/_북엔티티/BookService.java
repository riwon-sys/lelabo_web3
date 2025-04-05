package example.class03._북엔티티;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    // 엔티티리포지토리 인터페이스
    private final BookEntityRepository bookEntityRepository;

    // C
    public boolean post(BookEntity bookEntity){
        BookEntity bookEntity2 = bookEntityRepository.save(bookEntity);
        return true;
    }

    // R
    public List<BookEntity>get(){
        List<BookEntity>bookEntityList
                =bookEntityRepository.findAll();
        return bookEntityList;
    }

    // U
    public  boolean put (BookEntity bookEntity){
        bookEntityRepository.save(bookEntity);
        return true;
    }

    // U2
    @Transactional
    public boolean put2(BookEntity bookEntity){
        // 1. id entity
        Optional<BookEntity> optionalBookEntity=bookEntityRepository.findById(bookEntity.getBid());
        // 2. entity O > isPresent()
        if(optionalBookEntity.isPresent()){
           // 3. Optional > entity 0
            BookEntity entity = optionalBookEntity.get();
            entity.setBtitle(bookEntity.getBtitle()); // 도서제목
            entity.setBwriter(bookEntity.getBwriter()); // 도서저자
            entity.setBcompany(bookEntity.getBcompany()); // 출판사
            entity.setBdate(bookEntity.getBdate()); // 출판일자
            return true;
        }
        return false;
    }

    // D
    public boolean delete(int bid){
        bookEntityRepository.deleteById(bid);
        System.out.println( bookEntityRepository.count());
        System.out.println( bookEntityRepository.existsById(bid));

        return true;
    }


}
