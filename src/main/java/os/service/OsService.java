// OsService 구성 | rw 25-04-10 생성
package os.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import os.model.dto.OsDto;
import os.model.entity.OsEntity;
import os.model.repository.OsEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OsService {
    private final OsEntityRepository osEntityRepository;
    // [1]. C
    public OsDto osPost(OsDto osDto){
        // 1. dto를 entity 변환
        OsEntity osEntity=osDto.toEntity();
        // 2. entity를 save 영속화,db레코드 매칭 및 등록
        OsEntity saveEntity = osEntityRepository.save(osEntity);
        // 3. save로부터 반환된 엔티티(영속화)된 결과가 존재하면
        if (saveEntity.getOid()>0) {
            // 4. Dto로 변환
            return saveEntity.toDto(); // entity 를 dto 로 변환하여 반환
        }else { // 결과가 존재하지 않으면
            return null;
        }
    } // f e

    // [2]. R
    public List<OsDto>osFindAll(){
        // 1. 일반 반복문
        List<OsEntity>osEntityList=osEntityRepository.findAll();
        // 2. 모든 entity를 DtoList 로 변환
        List<OsDto>osDtoList=new ArrayList<>(); // dtolist 생성
        for (int i =0; i<osEntityList.size(); i++){ // entitylist 순회
            OsDto osDto = osEntityList.get(i).toDto(); // i번째의 entity를 dto로 변환
            osDtoList.add(osDto); // dtolist 에 저장
        } // for e
        // 3. 결과 반환
        return  osDtoList;
        // 4. stream
        // return osEntityRespository.findAll().stream().map(OsEntitiy::toDto).collect(Collectors.toList());
    } // f e

    // [2-2]. R2
    public  OsDto osFindById(int oid){
        // [2-2-1] 일반적인 ===============
        // 1. pk식별자 이용한 entity 조회하기 , FindById()
        // Optional 클래스 : null을 제어하는 메소드들을 제공하는 클래스
        Optional<OsEntity>optional=osEntityRepository.findById(oid);
        // 2. 조회한 결과가 존재하면 .isPresent()
        if (optional.isPresent()){
            // 3. Optional에서 entity 꺼내기
            OsEntity osEntity = optional.get();
            // 4. dto로 변환
            OsDto osDto = osEntity.toDto();
            // 5. 반환
            return osDto;
        }
        return null;

        // [2-2-2] Stream  ===============
        /*
        *  return osEntityRepository.findById(oid).map(OsEntity::toDto).orElse(null);
        *         .map(OsEntity::toDto) : Optional 의 데이터가 null이 아니면 map 실행하여 dto 변환후 반환
        *         .orElse(null); : Optional의 데이터가 null 이면 null 반환.
        * */

    } // fe

    // [3] U + @Transactional
    public OsDto osUpdate(OsDto osDto){
        // [3-1] 일반적인 ===============
        // 1. 수정할 id로 엔티티를 조회한다.
        Optional<OsEntity>optional=osEntityRepository.findById(osDto.getOid());
        // 2. 존재하면 수정 .isPresent() , 존재하지 않으면 null 반환
        if (optional.isPresent()){
            // 3. 엔티티 꺼내기
            OsEntity osEntity =optional.get();
            // 4. 엔티티 수정하기 , 입력받은 dto 값을 엔티티에 대입하기
            osEntity.setOname(osDto.getOname());
            osEntity.setOdescription(osDto.getOdescription());
            osEntity.setOqt(osDto.getOqt());
            return osEntity.toDto(); // dto로 변환 후 반환
        }
        return null;

        // [3-2] Stream  ===============
        /*
        return  osEntityRepository.findById(osDto.getOid());
        // findById 결과의 optional 데이터가 존재하면
        .map((entity)-> { // 람다 ()->{}
              entity.setOname(osDto.getOname());
              entity.setOdescription(osDto.getOdescription());
              entity.setOqt(osDto.getOqt());
        })
        // findById 결과의 optional 데이터가 존재하지 않으면
        .orElse (null);
        */


    } // fe

    // [4]. D
    public boolean osDelete(int oid){
        // [4-1] 일반적인 ===============
        // 1. id 를 이용하여 엔티티
        // findById 반환타입 : Optional vs existsBYId : boolean
        boolean result = osEntityRepository.existsById(oid);
        // 2. 만약에 존재하면
        if(result ==true){
            // 3. 영속성 제거, deleteById(PK번호);
            osEntityRepository.deleteById(oid);
            return true; // 삭제 성공
        }
        return false; // 존재하지 않으면 삭제 취소

     /*   // [4-2] Stream  ===============
        return  osEntityRepository.findById(oid);
        .map((entity) ->{
            osEntityRepository.deleteById(oid);
            return true;
        })
        .orElse(false);
     */
    } // fe

    // [5]. R3+PAGING
    public List<OsDto>osFindByPage(int page , int size){
        PageRequest pageRequest = PageRequest.of(page-1,size, Sort.by(Sort.Direction.DESC,"oid"));
    /*
        // [5-1] 일반적인 ===============
        // 1. pageRequest 객체를 findXX에 매개변수로 대입한다. findAll(페이징 객체); , 반환타입 : Page타입 = List타입과 유사
        Page<OsEntity>osEntityPage=osEntityRepository.findAll(pageRequest);
        // 2. page 타입의 entity 를 dto 변환
        List<OsDto>osDtoList=new ArrayList<>();
        //3 . for 문
        for (int i =0; i<osEntityPage.getContent().size();i++){
            OsDto osDto = osEntityPage.getContent().get(i).toDto();
            osDtoList.add(osDto);
        }
        return  osDtoList;
    */
        // [5-2] Stream  ===============
        return  osEntityRepository.findAll(pageRequest).stream()
                .map(OsEntity::toDto)
                .collect(Collectors.toList());

        // [5-3] PageRequest 클래스 이용한 페이징 처리 설정
           // 1. PageRequest.of (조회할 페이지 번호 , 자료개수 , 정렬(선택))
           // 2. 조회 할 페이지 번호는 1페이지가 0부터 시작
           // 3. 페이지당 조회 할 자료 개수
           // 4. Sort.by 정렬방법
              // - Sort.by( Sort.Direction.ASC, "필드명") : 오름차순
              // - Sort.by( Sort.Direction.DESC, "필드명") : 내림차순
    } // fe

    // [6]. R3 (입력한 값이 일치한 제목조회) TITLE | 전체조회 (PAGING)
    public  List<OsDto>osAllserch(String oname){
     /*
        // [6-1] Query ===============
        return osEntityRepository.findByOname(oname);
                .stream().map( OsEntity::toDto)
                .collet(Collectors.toList());
     */
        // [6-2] nativeQuery ===============
        return  osEntityRepository.findBy0nameNative(oname)
                .stream().map(OsEntity::toDto)
                .collect(Collectors.toList());
    } // fe

    // [6-2]. R3 (입력한 값이 일치한 제목 , 설명조회) KEYWORD | 전체조회 (PAGING)
    public List<OsDto>osSerch(String oname){

     /*
        // [6-2-1] Query ===============
        return  osEntityRepository.findByOnameContaining(oname)
                .stream().map( OsEntity::toDto )
                .collect( Collectors.toList() );
     */
        // [6-2] nativeQuery ===============
        return  osEntityRepository.findByOnameNativeSearch(oname)
                .stream().map(OsEntity::toDto)
                .collect(Collectors.toList());
    } // fe


}
