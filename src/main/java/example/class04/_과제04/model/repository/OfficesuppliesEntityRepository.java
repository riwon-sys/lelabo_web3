package example.class04._과제04.model.repository;

import example.class04._과제04.model.entity.OfficesuppliesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// EntityRepository 제목 검색과 제품포함 검색 추상 메소드 추가| rw 25-04-07 생성
@Repository
public interface OfficesuppliesEntityRepository extends JpaRepository<OfficesuppliesEntity , Integer>{
    // JPA Repository | rw 25-04-07 첫 배움
    // [1]. .save() 2.findById() 3.findAll() 4.deleteById() 등등 미리 만들어진 CRUD 메소드 제공
    // [2]. 쿼리메서드( JPQL 이용한 메소드 이름 기반으로 자동 생성 ) // ==========================
    // Spring JPA에서 SQL 문장을 직접 작성하지 않고 메소드 이름으로 쿼리 생성한다. < 카멜표기법 >
    // *** 메소드 명명 규칙 *** : https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<OfficesuppliesEntity> findByName( String name );
         // 1. List< OfficesuppliesEntity >: 조회 결과를 List 타입으로
         // findByName : name 필드를 select(조회)한다. * 주의할 점 : < 카멜표기법 >과 메소드 명명 규칙
         // ( String name ) : 조회 조건
              // mybatis : select* from officesupplies where name = ${ name }
         // 2. findBy 필드명 Containing : 포함된(like) 조회
         List<OfficesuppliesEntity> findByNameContaining( String keyword );
              // mybatis : select* from officesupplies where name like %${ name }%
         // 3. findBy 필드명 AND 필드명 : 두 조건을 조회 , And / Or
         List<OfficesuppliesEntity> findByNameAndDescription( String name, String description );
              // mybatis : select* from officesupplies where name = ${ name } and description = ${ description }
         // 4. existsBy 필드명 : 조건에 맞는 엔티티 여부 (t/f) 조회 , 반환타입 boolean
         boolean existsByName( String name );
         // 5. countBy 필드명 : 조건에 맞는 엔티티 개수 조회 , 반환타입 : long
         long countBy( String name );
         // 6. deleteBy 필드명 : 조건에 맞는 엔티티 삭제 , 반환타입 : void
         void deleteByTitle( String title );
    // [3].네이티브 쿼리(* SQL문 직접 작성 *)  // ==========================
    // Spring JPA 에서 SQL 문법을 직접 작성하여 실행한다.
    // *** @Query( value ="sql문" , nativeQuery = true ) ***
    // -> Query 는 select 을 위한 어노테이션 이므로 insert,update,delete 를 사용 할 경우에는 @Modifying 을 같이 사용한다.
    // SQL 문의 매개변수를 작성시에는 : 매개변수명 작성하여 매개변수를 대입 할 수 있다.
         // 1.
         @Query( value="select * from officesupplies where name = :name" , nativeQuery = true )
         List< OfficesuppliesEntity >findByNameNative( String name );
              // List< officesupppliesEntity > :  조회 결과를 List 타입으로
              // findByNameNative : 규칙이 없으므로 아무거나
              // ( String name ) : 조회 조건으로 SQL 문법의 매개변수
         // 2.
         @Query( value="select * from officesupplies where name like %:keyword%" , nativeQuery = true )
         List< OfficesuppliesEntity > findByNameNativeSearch( String keyword );

         // 3. delete
         @Modifying // 네이티브쿼리 (@Query)사용시 select 만 지원 하므로 insert / update / delete 쿼리는 @Modifying  같이 사용된다.
         @Query( value="delete from officesupplies where name = :name" , nativeQuery = true )
         int deleteByNative( String name );

         // 4. update
         @Modifying
         @Query( value="update officesupplies set name = :name, description = :description , quantity =:quantity, where id = :id" , nativeQuery = true )
         int updateByNative( Integer id, String name, String description , Integer quantity );




}


