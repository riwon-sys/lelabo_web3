package example.class02._BaseTime;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    // 조작할 엔티티리포지토리 인터페이스
    private final BookEntityRepository bookEntityRepository;

    // C
    public  boolean post( example.class02._BaseTime.BookEntity bookEntity){

        example.class02._BaseTime.BookEntity bookEntity2 = bookEntityRepository.save( bookEntity );
        // Entity : 영속 전 객체 | Entity2 : 영속된 객체 , 매핑
        return  true;
    }

    // R
    public List<example.class02._BaseTime.BookEntity> get(){
        // 3. 모든 엔티티를 리스트로 반환 , .findAll()
        List<example.class02._BaseTime.BookEntity> bookEntityList
                = bookEntityRepository.findAll();
        return bookEntityList;
    }

    // U
    public boolean put( example.class02._BaseTime.BookEntity bookEntity){
        // 현재 엔티티의 ID가 존재하면 UP | 없으면 IN | .save()
        bookEntityRepository.save(bookEntity);
        return true;
    }

    // U2 : 존재하는 ID만 수정 | findById(pk)
    @Transactional // 아래 메소드에서 하나라도 SQL의 문제가 발생될 경워 전체 취소하겠다.
    public boolean put2( example.class02._BaseTime.BookEntity bookEntity) {
        // 1. id에 해당하는 엔티티 찾기
        Optional<example.class02._BaseTime.BookEntity> optionalBookEntity=bookEntityRepository.findById(bookEntity.get도서번호());
        // 2. 만약에 조회한 엔티티가 있으면.isPresent()
        if(optionalBookEntity.isPresent()){
            // 3. Optional 객체에서 (영속된) 엔티티 꺼내오기
            BookEntity entity = optionalBookEntity.get();
            entity.set도서명(bookEntity.get도서명());
            entity.set도서저자(bookEntity.get도서저자());
            entity.set도서출판사(bookEntity.get도서출판사());
            entity.set출판일자(bookEntity.get출판일자());
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
