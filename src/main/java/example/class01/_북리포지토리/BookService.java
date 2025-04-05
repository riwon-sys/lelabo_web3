package example.class01._북리포지토리;


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
    public  boolean post( BookEntity bookEntity){

        BookEntity bookEntity2 = bookEntityRepository.save( bookEntity );
        // Entity : 영속 전 객체 | Entity2 : 영속된 객체 , 매핑
        return  true;
    }

    // R
    public List<BookEntity> get(){
        // 3. 모든 엔티티를 리스트로 반환 , .findAll()
        List<BookEntity> bookEntityList
                = bookEntityRepository.findAll();
        return bookEntityList;
    }

    // U
    public boolean put( BookEntity bookEntity){
        // 현재 엔티티의 ID가 존재하면 UP | 없으면 IN | .save()
        bookEntityRepository.save(bookEntity);
        return true;
    }

    // U2 : 존재하는 ID만 수정 | findById(pk)
    @Transactional // 아래 메소드에서 하나라도 SQL의 문제가 발생될 경워 전체 취소하겠다.
    public boolean put2( BookEntity bookEntity) {
        // 1. id에 해당하는 엔티티 찾기
        Optional<BookEntity>optionalBookEntity=bookEntityRepository.findById(bookEntity.getBid());
        // 2. 만약에 조회한 엔티티가 있으면.isPresent()
        if(optionalBookEntity.isPresent()){
            // 3. Optional 객체에서 (영속된) 엔티티 꺼내오기
            BookEntity entity = optionalBookEntity.get();
            entity.setBtitle(bookEntity.getBtitle());
            entity.setBwriter(bookEntity.getBwriter());
            entity.setBcompany(bookEntity.getBcompany());
            entity.setBbirth(bookEntity.getBbirth());
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
