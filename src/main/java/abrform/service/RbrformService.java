// RbrformService구성 | rw 25-04-11 생성
package abrform.service;

import abrform.model.dto.RbrformDto;
import abrform.model.entity.RbrformEntity;
import abrform.model.repository.RbrformEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RbrformService {
    private final RbrformEntityRepository rbrformEntityRepository;

    // [1]. C
    public RbrformDto rbPost(RbrformDto rbrformDto){
        // 1. dto를 entity변환
        RbrformEntity rbrformEntity=rbrformDto.toEntity2();
        // 2. entity를 save 영속화, db레코드 매칭 및 등록
        RbrformEntity saveEntity2= rbrformEntityRepository.save(rbrformEntity);
        // 3. sava로부터 반환된 엔티티(영속화)된 결과가 존재하면
        if (saveEntity2.getRno()>0){
            // 4. Dto로 변환
            return saveEntity2.toDto2(); // entity 를 dto로 변환하여 반환
        }else{ // 결과가 존재하지 않으면
            return null; // null 반환
        }
    }

    // [2]. R
    public List<RbrformDto>rbFindAll(){
        // [2-2] Stream  ===============
        return rbrformEntityRepository.findAll().stream()
               .map(RbrformEntity::toDto2)
               .collect(Collectors.toList());

    /*
        // [2-1] 일반적인 ===============
        List<RbrformEntity>rbrformEntityList=rbrformEntityRepository.findAll(); // DtoList 생성
        for (int i=0; i<rbrformEntityList.size(); i++){ // EntityList 순회
            RbrformDto rbrformDto = rbrformEntityList.get(i).toDto2(); // i번째 Entity를 Dto로 변환
            rbrformDtoList.add(rbrformDto); // DtoList 저장
        } // For E
        // 2. 결과 반환
        return rbrformDtoList;
    */
    }
    // [2-2]. R2
    public RbrformDto rbFindById(int rno){
        // [2-2-2] Stream  ===============
        return rbrformEntityRepository.findById(rno)
                .map(RbrformEntity::toDto2)
                .orElse(null);
        // Optional 의 데이터가 null 아니면 map 을 실행하여 Dto로 변환 후 반환하고,
        // Optional 의 데이터가 null 이면 null 반환
    /*
        // [2-2-1] 일반적인 ===============
        // 1. PK 식별자 이용 Entity 조회 [ FindById() ]
        // Optional 클래스 : null을 제어하는 메소드들을 제공하는 클래스
        Optional<RbrformEntity>optional=rbrformEntityRepository.findById(rno);
        // 2. 조회한 결과가 존재 [ .isPresent() ]
        if (optional.isPresent()){
            // 3. Optional에서 entity 꺼내기
            RbrformEntity rbrformEntity = optional.get();
            // 4. dto로 변환
            RbrformDto rbrformDto = rbrformEntity.toDto2();
            // 5. 반환
            return rbrformDto;
        }
    */
    }
    // [3] U + @Transactional
    public RbrformDto rbUpdate(RbrformDto rbrformDto){
        // [3-2] Stream  ===============
        return  rbrformEntityRepository.findById(rbrformDto.getRno())
        // findById의 결과 optional 데이터가 존재하면
        .map(( entity )->{ // 람다식 사용하기 ()->{}
               entity.setRtitle(rbrformDto.getRtitle());
               entity.setRwriter(rbrformDto.getRwriter());
               entity.setRcontent(rbrformDto.getRcontent());
               entity.setRpwd(rbrformDto.getRpwd());
               return entity.toDto2();
        })
        // findById 결과의 optional 데이터가 존재하지 않으면
                .orElse(null);
    }

    // [4].

}
