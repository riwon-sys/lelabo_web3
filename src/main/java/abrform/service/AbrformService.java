// AbrformService 구성 | rw 25-04-11 생성

package abrform.service;

import abrform.model.dto.AbrformDto;
import abrform.model.entity.AbrformEntity;
import abrform.model.repository.AbrformEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AbrformService {
    private final AbrformEntityRepository abrformEntityRepository;

    // [1]. C
    public AbrformDto abPost(AbrformDto abrformDto){
        // 1. dto를 entity변환
        AbrformEntity abrformEntity=abrformDto.toEntity1();
        // 2. entity를 save 영속화, db레코드 매칭 및 등록
        AbrformEntity saveEntity= abrformEntityRepository.save(abrformEntity);
        // 3. sava로부터 반환된 엔티티(영속화)된 결과가 존재하면
        if (saveEntity.getAid()>0){
            // 4. Dto로 변환
            return saveEntity.toDto1(); // entity 를 dto로 변환하여 반환
        }else{ // 결과가 존재하지 않으면
            return null; // null 반환
        }
    } // fe

    // [2]. R
    public List<AbrformDto>abFindAll(){
        // [2-1] 일반적인 ===============
        List<AbrformEntity>abrformEntityList=abrformEntityRepository.findAll();
        // 1. 모든 entity를 DtoList 로 변환
        List<AbrformDto>abrformDtoList=new ArrayList<>(); // DtoList 생성
        for (int i = 0; i<abrformEntityList.size(); i++){ // EntityList 순회
            AbrformDto abrformDto = abrformEntityList.get(i).toDto1(); // i번째 Entity를  Dto로 변환
            abrformDtoList.add(abrformDto); // DtoList 저장
        } // For E
        // 2. 결과 반환
        return  abrformDtoList;
     /*
         [2-2] Stream  ===============
        return abrformEntityRepository.findAll().stream()
               .map(AbrformEntitiy::toDto1)
               .collect(Collectors.toList());
    */
    } // fe
    // [2-2]. R2
    public  AbrformDto abFindById(int aid){
        // [2-2-1] 일반적인 ===============
        // 1. PK 식별자 이용 Entity 조회 [ FindById() ]
        // Optional 클래스 : null을 제어하는 메소드들을 제공하는 클래스
        Optional<AbrformEntity>optional=abrformEntityRepository.findById(aid);
        // 2. 조회한 결과가 존재 [ .isPresent() ]
        if (optional.isPresent()){
            // 3. Optional에서 entity 꺼내기
            AbrformEntity abrformEntity = optional.get();
            // 4. dto로 변환
            AbrformDto abrformDto = abrformEntity.toDto1();
            // 5. 반환
            return abrformDto;
        }
        return null;
    /*

        return  abrformEntityRepository.findById(aid).map(AbrformEntity::toDto1).orElse(null);
                .map(AbrformEntity::toDto1) : Optional 의 데이터가 null 아니면, map 실행하여 dto로 변환 후 반환
                .orElse(null) : Optional의 데이터가 null 이면 null 반환.

    */
    } // fe

    // [3] U + @Transactional
    public AbrformDto abUpdate(AbrformDto abrformDto){
        // [3-1] 일반적인 ===============
        // 1. PK 식별자 이용 Entity 조회 [ FindById() ]
        // Optional 클래스 : null을 제어하는 메소드들을 제공하는 클래스
        Optional<AbrformEntity>optional=abrformEntityRepository.findById(abrformDto.getAid());
        // 2. 조회한 결과가 존재 [ .isPresent() ]
        if (optional.isPresent()) {
            // 3.Optional에서 Entity 꺼내기
            AbrformEntity abrformEntity = optional.get();
            // 4. 꺼낸 Entity 수정하기 , 입력받은 Dto 값을 Entity에 대입하기
            abrformEntity.setAtitle(abrformDto.getAtitle());
            abrformEntity.setAwriter(abrformDto.getAwriter());
            abrformEntity.setAcontent(abrformDto.getAcontent());
            abrformEntity.setApwd(abrformDto.getApwd());
            // 5. Dto로 변환 후 반환
            return abrformEntity.toDto1();
        }
        return null;
     /*
        // [3-2] Stream  ===============
        return abrformEntityRepository.findById(abrformDto.getAid());
        // findById 결과의 optional 데이터가 존재하면
        .map((entity) ->{ // 람다식 사용 () ->{}
              entity.setAtitle(abrformDto.getAtitle());
              entity.setAwriter(abrformDto.getAwriter());
              entity.setAcontent(abrformDto.getAcontent());
              entity.setApwd(abrformDto.getApwd());
              return entity.toDto1();
        })
        // findById 결과의 optional 데이터가 존재하지 않으면
                .orElse(null);
     */
    }

    // [4]. D
    public boolean abDelete(int aid){
        // [4-1] 일반적인 ===============
        // 1. PK 식별자 이용 Entity 조회
        // findById 반환타입 : Optional vs [ exestsById() ]:boolean
        boolean result = abrformEntityRepository.existsById(aid);
        // 2. 조회한 결과가 존재
        if(result==true){
            // 3. 영속성 제거 [ deleteById(pk); ]
            abrformEntityRepository.deleteById(aid);
            return true; // 삭제 성공
        }
        return false; // 존재하지 않으면 삭제 취소
    /*
        // [4-2] Stream  ===============
        return abrformEntityRepository.findById(aid)
        .map((entity)->{
            abrformEntityRepository.deleteById(aid);
            return true;
        })
                .orElse(false);
    */

    }


}
