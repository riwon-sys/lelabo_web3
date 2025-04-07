package example.class04._todo.service;

import example.class04._todo.model.dto.TodoDto;
import example.class04._todo.model.entity.TodoEntity;
import example.class04._todo.model.repository.TodoEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
// | rw 25-04-07 생성
@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoEntityRepository todoEntityRepository;
    // 1. C
    public TodoDto todoSave( TodoDto todoDto ){
        // 1. dto 를 entity 변환하기
        TodoEntity todoEntity = todoDto.toEntity();
        // 2. entity를 save(영속화/db레코드 매칭/등록) 한다.
        TodoEntity saveEntity = todoEntityRepository.save( todoEntity );
        // 3. save 로 부터 반환된 엔티티(영속화)된 결과가 존재하면
        if( saveEntity.getId() > 0 ){
            return saveEntity.toDto(); // entity를 dto로 변환하여 반환
        }else{ // 결과가 존재하지 않으면
            return null; // null 반환
        }
    } // f end

    // 2. R
    public List<TodoDto> todoFindAll( ){

        // [방법1]. 일반 반복문 ====================================================================== //
        // 1. 모든 entity 조회 , findAll()
        List<TodoEntity> todoEntityList = todoEntityRepository.findAll();
        // 2. 모든 entity 리스트 를 dto 리스트 변환하다.
        List<TodoDto> todoDtoList = new ArrayList<>(); // 2-1 : dto 리스트 생성한다.
        for( int index = 0 ; index < todoEntityList.size() ; index++ ){ // 2-2 : entity 리스트를 순회
            TodoDto todoDto = todoEntityList.get( index ).toDto(); // 2-3 : index번째 entity 를 dto로 변환
            todoDtoList.add( todoDto ); // 2-4 : dto 리스트에 저장
        } // for end
        // 3. 결과 반환
        return todoDtoList;

        // [방법2]. stream ====================================================================== //
        // return todoRepository.findAll().stream().map( TodoEntity::toDto ).collect( Collectors.toList() );

    } // f end

    // 2-2. R2
    public TodoDto todoFindById( int id ){
        // [방법1] 일반적인 =========================================//
        // 1. pk( 식별번호 ) 이용한 entity 조회하기, .findById()
        // Optional 클래스 : null 제어하는 메소드들을 제공하는 클래스
        Optional< TodoEntity > optional
                = todoEntityRepository.findById( id );
        // 2. 조회한 결과가 존재하면 , .isPresent()
        if( optional.isPresent() ){
            // 3. Optional 에서 entity 꺼내기
            TodoEntity todoEntity = optional.get();
            // 4. dto 로 변환
            TodoDto todoDto = todoEntity.toDto();
            // 5. 반환
            return todoDto;
        }
        return null;
        // [방법2] stream ==========================================//
        // return todoEntityRepository.findById( id ).map( TodoEntity::toDto ).orElse( null );

        // .map( TodoEntity::toDto ) : Optional 의 데이터가 null 이 아니면 map 실행하여 dto 변환후 반환
        // .orElse( null ) : Optional 의 데이터가 null 이면 null 반환

    } // f end

    // 3. U + @Transactional
    public TodoDto todoUpdate( TodoDto todoDto ){
        // [방법1] 일반적인 =========================================//
        // 1. 수정할 id 로 엔티티를 조회한다.
        Optional< TodoEntity > optional
                = todoEntityRepository.findById( todoDto.getId()  );
        // 2. 존재하면 수정 하고 존재하지 않으면 null 반환 , .isPresent()
        if( optional.isPresent() ){
            // 3. 엔티티 꺼내기
            TodoEntity todoEntity = optional.get();
            // 4. 엔티티 수정하기 , 입력받은 dto값을 엔티티에 대입하기
            todoEntity.setTitle( todoDto.getTitle() );
            todoEntity.setContent( todoDto.getContent() );
            todoEntity.setDone( todoDto.isDone() ); // boolean 의 getter 는 isXXX() 사용
            return todoEntity.toDto();// dto로 변환후 반환
        }
        return null;
        // [방법2] stream ==========================================//
//        return todoEntityRepository.findById( todoDto.getId() )
//                // findById 결과의 optional 데이터가 존재하면
//                .map(  (entity) -> { // 람다식 함수.  ()->{}
//                            entity.setTitle( todoDto.getTitle() );
//                            entity.setContent( todoDto.getContent() );
//                            entity.setDone( todoDto.isDone() );
//                            return entity.toDto();
//                })
//                // findById 결과의 optional 데이터가 존재하지 않으면
//                .orElse( null );
    } // f end

    // 4. D
    public boolean todoDelete( int id ){
        // [방법1] 일반적인 =========================================//
        // 1. id를 이용하여 엔티티 (존재여부) 조회 하기
        // findById 반환타입:Optional vs existsById : boolean
        boolean result = todoEntityRepository.existsById( id );
        // 2. 만약에 존재 하면
        if( result == true ){
            // 3. 영속성 제거  , deleteById( pk번호 );
            todoEntityRepository.deleteById( id );
            return true; // 삭제 성공
        }
        return false; // 존재하지 않으면 삭제 취소
        // [방법2] stream ==========================================//
//        return todoEntityRepository.findById( id )
//                .map( (entity) -> {
//                    todoEntityRepository.deleteById(id);
//                    return true;
//                })
//                .orElse( false );
    }
    // 5. R3+PAGING
    public List<TodoDto>todoFindByPaging( int page ,int size ){
        // [5]-1. pageRequest 클래스 이용한 페이징처리 설정 // page = 현재 조회중인 페이지 번호 // size = 페이지 1개당 조회할 자료의 개수
           // pageRequest.of(조회할 페이지 번호 , 자료의 개수)
           // - 조회할 페이지 번호는 1페이지가 0부터 시작
           // - 페이지 당 조회할 자료의 개수
           // - Sort.by : 정렬
                // - Sort.by(Sort.Direction.ASC , "필드명" ) : 오름 차순
                // - Sort.by(Sort.Direction.DESC , "필드명" ) : 내림차순(최신순)
        PageRequest pageRequest= PageRequest.of( page-1 , size , Sort.by(Sort.Direction.DESC,"id"));
        // [5]-2. strem 이용한 조회 리스트를 dto로 변환한다.(방법2)
        return todoEntityRepository.findAll( pageRequest ).stream()
               .map( TodoEntity::toDto )
               .collect( Collectors.toList() );
        // stream 사용 연습
//      return todoEntityRepository.findAll(pageRequest).stream()Stream<TodoEntity>
//             .map( TodoEntity::toDto )
//             .collect( Collectors.toList() );
        /*
        // [5]-3. for 문
        [ * ] pageRequest 객체로 find XXX에 매개변수로 대입한다. .findAll( 페이징객체 ); , 반환타입 : Page타입 ; List 타입과 유사
              page < TodoEntity > todoEntityPage = todoRepository.findAll( pageRequest );
              for( int i=0; i<todoEntityPage.getContent().size(); i++ ){
                  TodoDto todoDto = todoEntityPage.getContent().get(i).toDto();
                  todoList.add( todoDto );
             }
             return todoDtoList;
        */
    }
    // 6. R3  ( 입력한 값이 일치한 제목조회) title
    public List<TodoDto> search1(String title) {
    /*    
        // [6]-1.query method(JPA 리포지토리에서 내가만든 추상메소드 사용)
        return todoEntityRepository.findByTitle(title) 
                .stream().map(TodoEntity::toDto)
                .collect(Collectors.toList());
    */
    
        // [6]-2. native query(jPA 리포지토리에서 내가 만든 추상메소드 사용)
    return todoEntityRepository.findByTitleNative(title)
            .stream().map(TodoEntity::toDto)
            .collect(Collectors.toList());
    }

    // 7. R3-2 ( 입력한 값이 포함된 제목 조회) keyword
    public List<TodoDto> search2(String title) {
    /*    
        // [7]-1. query method(JPA 리포지토리에서 내가만든 추상메소드 사용)
        return todoEntityRepository.findByTitleContaining(title)
                .stream().map(TodoEntity::toDto)
                .collect(Collectors.toList());
    */
    
     
        // [7]-2. native query(jPA 리포지토리에서 내가 만든 추상메소드 사용)
    return todoEntityRepository.findByTitleNativeSearch(title)
            .stream().map(TodoEntity::toDto)
            .collect(Collectors.toList());
    
    }


} // class end


// System.out.println("todoEntityPage: " + todoEntityPage);
// System.out.println(todoEntityPage.getTotalPages()); // getTotalPages() : 전체 페이지수 반환
// System.out.println(todoEntityPage.getTotalElements()); // getTotalElements() : 전체 개시물수 변환
// System.out.println(todoEntityPage.getContent()); // getContent() : Page타입 --> List 타입으로 변환
