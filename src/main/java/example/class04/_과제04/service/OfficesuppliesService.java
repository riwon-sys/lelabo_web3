package example.class04._과제04.service;

import example.class04._과제04.model.dto.OfficesuppliesDto;
import example.class04._과제04.model.entity.OfficesuppliesEntity;
import example.class04._과제04.model.repository.OfficesuppliesEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class OfficesuppliesService {

    private final OfficesuppliesEntityRepository officesuppliesEntityRepository;

    // [1]. C
    public OfficesuppliesDto officesuppliesSave( OfficesuppliesDto officesuppliesDto ){
        // [1]-1. dto를 entity 변환
        OfficesuppliesEntity officesuppliesEntity=officesuppliesDto.toEntity();
        // [1]-2.entity를 save(영속화/db레코드 매칭/등록) 한다.
        OfficesuppliesEntity saveEntity = officesuppliesEntityRepository.save(officesuppliesEntity);
        // [1]-3.save로 부터 반환된 엔티티(영속화)된 결과가 존재하면
        if(saveEntity.getId() > 0){
            // [1]-4. Dto로 변환
            return saveEntity.toDto(); // entity를 dto로 변환하여 반환
        }else { // 결과가 존재하지 않으면
            return null; //null 반환
        }
    } // f e

    // [2]. R
    public List< OfficesuppliesDto >officesuppliesFindAll(){
        // [2]-1. 일반 반복문
        List<OfficesuppliesEntity>officesuppliesEntityList = officesuppliesEntityRepository.findAll();
        // [2]-2. 모든 entityList를 DtoList로 변환
        List<OfficesuppliesDto>officesuppliesDtoList = new ArrayList<>(); // dtolist 생성
        for( int i=0; i<officesuppliesEntityList.size(); i++ ){ // entitylist 순회
            OfficesuppliesDto officesuppliesDto = officesuppliesEntityList.get( i ).toDto(); //i번째 entity를 dto로 변환
            officesuppliesDtoList.add( officesuppliesDto ); // dto list에 저장
        }// for e
        // [2]-3. 결과 반환
        return officesuppliesDtoList;
        // [2]-4. stream
        // return officesuppliesEntityRepository.findAll().stream().map(OfficesuppliesEntity::toDto).collect(Collectors.toList());
    } // f e

    // [2]-(2). R2
    public OfficesuppliesDto officesuppliesFindById( int id ){
        // [2]-(2)-1 일반적인 ==============================
        // 1. pk(식별자) 이용한 entity 조회하기 , .findById( )
        // Optional 클래스 : null을 제어하는 메소드들을 제공하는 클래스
        Optional< OfficesuppliesEntity >optional = officesuppliesEntityRepository.findById( id );
        // 2. 조회한 결과가 존재하면 .isPresent()
        if ( optional.isPresent() ){
            // 3. Optional에서 entity 꺼내기
            OfficesuppliesEntity officesuppliesEntity = optional.get();
            // 4.dto로 변환
            OfficesuppliesDto officesuppliesDto= officesuppliesEntity.toDto();
            // 5. 반환
            return officesuppliesDto;
        }
        return null;
      /*
        // [2]-(2)-2 stream ==============================
        // return officesuppliesEntityRepository.findById( id ).map(OfficesuppliesEntity::toDto).orElse( null );

        //        .map( OfficesuppliesEntity::toDto ) : Optional 의 데이터가 null 이 아니면 map 실행하여 dto 변환 후 반환
        //
                .orElse(null); : Optional의 데이터가 null 이면 null 반환
      */
    } // f e

    // [3]. U + @Transactional
    public OfficesuppliesDto officesuppliesUpdate( OfficesuppliesDto officesuppliesDto ){
        // [3]-1 일반적인 ==============================
        // 1. 수정할 id 로 엔티티를 조회한다.
        Optional< OfficesuppliesEntity > optional
                = officesuppliesEntityRepository.findById( officesuppliesDto.getId());
        // 2. 존재하면 수정하고 존재하지 않으면 null 반환 , .isPresent()
        if( optional.isPresent() ){
            // 3. 엔티티 꺼내기
            OfficesuppliesEntity officesuppliesEntity = optional.get();
            // 4. 엔티티 수정하기 , 입력받은 dto값을 엔티티에 대입하기
            officesuppliesEntity.setName(officesuppliesDto.getName());
            officesuppliesEntity.setDescription(officesuppliesDto.getDescription());
            officesuppliesEntity.setQuantity(officesuppliesDto.getQuantity());
            return officesuppliesEntity.toDto(); // dto로 변환 후 반환
        }
        return null;
     /*
        // [3]-2 stream ==============================
        return officesuppliesEntityRepository.findById(officesuppliesDto.getId());
        // findById 결과의 optional 데이터가 존재하면
        .map(( entity )->   { // 람다식 함수 () -> {}
                   entity.setName(officesuppliesDto.getName());
                   entity.setDescription(officesuppliesDto.getDescription());
                   entity.setQuantity(officesuppliesDto.getQuantity());
        })
        // findById 결과의 optional 데이터가 존재하지 않으면
        .orElse( null );
     */
    } // f e

    // [4]. D
    public boolean officesuppliesDelete( int id ){
        // [4]-1 일반적인 ==============================
        // 1. id 를 이용하여 엔티티(존재여부) 조회 하기
             // findById  반환타입 : Optional vs existsBYId : boolean
        boolean result = officesuppliesEntityRepository.existsById(id);
        // 2. 만약에 존재 하면
        if( result == true ){
            // 3. 영속성 제거 ,deleteById( PK번호 );
            officesuppliesEntityRepository.deleteById(id);
            return true; // 삭제 성공
        }
        return false; // 존재하지 않으면 삭제 취소

     /*
        // [4]-2 stream ==============================
        return officesuppliesEntityRepository.findById(id);
        .map((entity) ->{
            officesuppliesEntityRepository.deleteById(id);
            return true;
        })
        .orElse( false );
     */
    } // f e

    // [5]. R3+PAGING
    public List<OfficesuppliesDto>officesuppliesFindByPage(int page , int size){

        PageRequest pageRequest = PageRequest.of(page-1,size ,Sort.by(Sort.Direction.DESC,"id"));
        // [5]-1. 일반적인 ==============================
     /*
        // 1. pageRequest 객체를 findXX에 매개변수로 대입한다. findAll( 페이징 객체 ); , 반환타입 : Page타입 = List타입과 유사
        Page< OfficesuppliesEntity > officesuppliesEntityPage = officesuppliesEntityRepository.findAll( pageRequest );
        // 2. page 타입의 entity 를 dto로 변환
        List< OfficesuppliesDto > officesuppliesDtoList=new ArrayList<>();
        // 3. for문
        for (int i =0; i<officesuppliesEntityPage.getContent().size();i++){
            OfficesuppliesDto officesuppliesDto = officesuppliesEntityPage.getContent().get(i).toDto();
            officesuppliesDtoList.add( officesuppliesDto );
        }
        return  officesuppliesDtoList;
    */
        // [5]-2. stream ==============================
        return officesuppliesEntityRepository.findAll( pageRequest ).stream()
                .map(OfficesuppliesEntity :: toDto)
                .collect(Collectors.toList());

        // [5]-3. PageRequest 클래스 이용한 페이징 처리 설정
             // 1. PageRequest.of( 조회할 페이지 번호 , 자료개수 , 정렬[선택])
             // 2. 조회 할 페이지 번호는 1페이지가 0부터 시작
             // 3. 페이지당 조회 할 자료 개수
             // 4. Sort.by 정렬 방법
                  // - Sort.by( Sort.Direction.ASC , "필드명" ) : 오름차순
                  // - Sort.by( Sort.Direction.DESC , "필드명" ) : 내림차순

    } // f e

    // [6]. R3 ( 입력한 값이 일치한 제목조회) title | 전체 조회 (PAGING)
    public List<OfficesuppliesDto>search1( String name){
      /*
        // [6]-1. Query ==============================
        return officesuppliesEntityRepository.findByName(name);
                .stream().map( officesuppliesEntity::toDto )
                .collect(Collectors.toList());
       */
        // [6-2] nativeQuery ===============
        return  officesuppliesEntityRepository.findByNameNativeSearch(name)
                .stream().map(OfficesuppliesEntity::toDto)
                .collect(Collectors.toList());
    } // fe

    // [6]-2. R3 (입력한 값이 일치하는 제목, 설명 조회) keyword | 전체 조회 (PAGING)
    public  List<OfficesuppliesDto>search2( String name ){
     /*
        // [6]-(2)-1. Query ==============================
        return officesuppliesEntityRepository.findByNameContaining( name )
                .stream().map( officesuppliesEntity:: toDto )
                .collect(Collectors.toList());
     */
        // [6]-(2)-2. nativeQuery =============================
        return officesuppliesEntityRepository.findByNameNativeSearch( name )
                .stream().map( OfficesuppliesEntity :: toDto )
                .collect(Collectors.toList());
    }
} // c e
