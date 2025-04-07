package example.class04._todo.model.repository;

import example.class04._todo.model.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// | rw 25-04-07 생성
@Repository
public interface TodoEntityRepository
        extends JpaRepository<TodoEntity , Integer> {

   // JPA Repository
    // [1]. .save() , findById() , findAll() , deleteById() 등등 미리 만들어진 CRUD메소드 제공

    // [2]. 쿼리메소드 (JPQL  이용한 메소드 이름 기반으로 자동 생성) // =========================
    // Spring JPA 에서 SQL 문장을 직접
         // [2]-1. findBy 필드명 : 조회
    List<TodoEntity> findByTitle(String title);
         // List<TodoEntity> : 조회 결과를 List 타입으로
         // findByTitle : title 필드를 select(조회) 한다. * 주의할 점 : <카멜표기법> 과 메소드 명명 규칙
         // (String title) : 조회 조건으로
              // myBatis : select * from todo where title = ${ title }
         // [2]-2. findBy 필드명 Containing : 포함된(like) 조회
    List<TodoEntity> findByTitleContaining( String keyword );
         // myBatis : select * from todo where title like %${title}%
         // [2]-3.findBy 필드명 And 필드명 : 두 조건을 조회 , And / Or
    List<TodoEntity> findByTitleAndContent(String title, String content);
         // [2]-4.existsBy 필드명 : 조건에 맞는 엔티티 여부 (t/f)조회 , 반환타입 : boolean
    boolean existsByTitle(String title);
         // [2]-5.countBy 필드명 : 조건에 맞는 엔티티 개수 조회 , 반환타입 : long
    long countByTitle(String title);
         // [2]-6. deleteBy 필드명 : 조건에 맞는 엔티티를 삭제 ,  반환타입 : void
    int deleteByTitle(String title);




    // [3]. 네이티브쿼리( * SQL 에 직접 작성 *) // =========================
    // Spring JPA 에서 SQL 문법을 직접 작성하여 실행한다.
    // *** @Query(value = "SQL 문" , nativeQuery = true) *** ,
    //      -> Query 는 select를 위한 어노테이션 이므로 insert,update,delete 할 경우에는 어노테이션 @Modifying 같이 사용한다.
    // [3]-1.
    @Query( value = "select * from todo where title = :title " , nativeQuery = true )
    List<TodoEntity>findByTitleNative( String title );
         // List<TodoEntity> : 조회 결과를 List 타입으로
         // findByTitleNative : 규칙이 없으므로 아무거나
         // (String title) : 조회 조건으로 SQL 문법의 매개변수
    // [3]-2.
    @Query( value = "select * from todo where title like %:keyword% " , nativeQuery = true )
     List<TodoEntity>findByTitleNativeSearch( String keyword );
    // [3]-3. delete
    @Modifying // 네이티브 쿼리 (@Query) 사용시  select 만 지원 하므로 insert/update/delete 쿼리는 @Modifying 같이 사용된다.
    @Query( value = "delete from todo where title = :title " , nativeQuery = true )
    int deleteByNative( String title );

    // [3]-4. update
    @Modifying
    @Query( value = "update todo set title = :title where id = :id " , nativeQuery = true )
    int updateByNative( int id , String title );

}