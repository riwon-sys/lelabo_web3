// OsEntityRepository 구성 | rw 25-04-10 생성
package os.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import os.model.dto.OsDto;
import os.model.entity.OsEntity;

import java.util.List;

@Repository
public interface OsEntityRepository extends JpaRepository<OsEntity , Integer>{

    // JPA Repository
    // [1] . .save() | .findById() | .findAII() | .deleteById() 등등 미리 만들어진 CRUD 메소드 제공

    // [2] . 쿼리메소드 ( JPQL 이용한 메소드 이름 기반으로 자동 생성 ===============
    // Spring JPA 에서 SQL에서 SQL 문장을 직접 작성하지 않고 메소드 이름으로 쿼리를 생성한다 . <카멜표기법>사용한다.
    // *** 메소드 이름 짓는 규약 *** SPRING 도큐먼트 참고 (htpps://docs.spring.io/spring-data/jpa/reference/jap/query-methods.html)


          // 1. List<OsEntity> : 조회 결과를 List 타입으로
    List<OsEntity> findByOname(String oname);
          // findByName : name 필드를 select(조회)한다. * 주의할 점 : <카멜표기법>과 메소드 이름짓는 규약 확인
          // (String oname) :  조회 조건
             // mybatis : select* from os where oname =${oname}
          // 2. findBy 필드명 Containing : 포함된(like) 조회
    List<OsEntity> findByOnameContaining(String keyword);
             // mybatis : select* from os where oname like %${name}%
          // 3. findBy 필드명 AND 필드명 : 두 조건을 조회 , And / Or
    List<OsEntity> findByOnameAndOdescription(String oname , String odescription);
             // mybatis : select* from os where oname = ${oname} and odescription = ${odescription}
          // 4. existsBy 필드명 : 조건에 맞는 엔티티 여부 (t/f)조회 , 반환타입 boolean
    boolean existsByOname(String oname);
          // 5. countBy 필드명 : 조건에 맞는 엔티티 개수 조회 , 반환타입 : long
    // long countByOname(String oname);
          // 6. deleteBy 필드명 : 조건에 맞는 엔티티 삭제 , 반환타입 : void
    void deleteByOname(String oname);


    // [3]. 네이티브 쿼리 (* SQL 문 직접 작성 *)
    // Spring JPA 에서 SQL 문법을 직접 작성하여 실행한다.
    // *** @Query( value = "sql문" , nativeQuery = true ) ***
    // -> Query 는 select 을 위한 어노테이션 이므로 insert , update , delete 를 사용 할 경우에는 @Modyfying 을 같이 사용한다.
    // SQL 문에서 매개변수를 작성할 경우에는 : 매개변수명을 직접 작성하여 매개변수를 대입 할 수 있다.

          // 1.
    @Query(value="select * from os where oname =:oname" , nativeQuery = true)
    List<OsEntity>findBy0nameNative(String oname);
             // List<OsEntity> : 조회 결과를 List 타입으로
             // findByNameNative: 규칙이 없으므로 아무거나
             // (String name) : 조회 조건으로 SQL 문법의 매개변수

          // 2.
    @Query(value = "select  * from os where oname like %:keyword%" , nativeQuery = true)
    List<OsEntity>findByOnameNativeSearch(String keyword);

          // 3.delete
    @Modifying // 네이티브 쿼리 (@Query) 사용시 select 만 지원하므로 insert , update , delete 쿼리의 경우에는 @Modifying 같이 사용한다.
    @Query(value = "delete from os where oname =:oname" , nativeQuery = true)
    int deleteByNative(String oname);

          // 4. update
    @Modifying
    @Query(value = "update os set oname = :oname , odescription = :odescription, oqt=:oqt, where oid =:oid" , nativeQuery = true)
    int updateByNative( Integer oid , String oname , String odescription , Integer oqt);


}
