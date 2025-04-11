package example.class01._북리포지토리;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityRepository
        extends JpaRepository<BookEntity , Integer>{ // @Id의 속성타입을 입력한다.

}
