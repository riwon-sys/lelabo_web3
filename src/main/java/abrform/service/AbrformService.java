// AbrformService 구성 | rw 25-04-11 생성

package abrform.service;

import abrform.model.dto.AbrformDto;
import abrform.model.dto.RbrformDto;
import abrform.model.entity.AbrformEntity;
import abrform.model.entity.RbrformEntity;
import abrform.model.repository.AbrformEntityRepository;
import abrform.model.repository.RbrformEntityRepository;
import abrform.util.EncryptUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AbrformService {
    private final AbrformEntityRepository abrformEntityRepository;
    private final RbrformEntityRepository rbrformEntityRepository;

    // [1]. C | rw 25-04-13 수정
    public AbrformDto abPost(AbrformDto dto) {
        // fs

        // 1. 비밀번호 암호화
        dto.setApwd(EncryptUtil.encode(dto.getApwd()));

        // 2. 파일 업로드 처리
        if (!dto.getMultipartFile().isEmpty()) {
            try {
                String originalFilename = dto.getMultipartFile().getOriginalFilename();
                String uploadPath = "src/main/resources/static/upload/" + originalFilename;
                dto.getMultipartFile().transferTo(new File(uploadPath)); // 파일 저장
                dto.setAimg(originalFilename); // DB에는 파일명만 저장
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
            }
        }

        // 3. DTO → Entity 변환 및 저장
        AbrformEntity entity = dto.toEntity1();
        AbrformEntity saved = abrformEntityRepository.save(entity);

        // 4. 결과 반환
        if (saved.getAid() > 0) {
            return saved.toDto1();
        } else {
            throw new RuntimeException("책 추천 등록에 실패했습니다.");
        }

        // fe
    }


/*
    // [1]. C | rw 25-04-12 생성
    public AbrformDto abPost(AbrformDto dto) {
        // (1) 비밀번호 암호화
        dto.setApwd(EncryptUtil.encode(dto.getApwd())); // 암호화 주입

        // (2) DTO → Entity 변환 (생성일/수정일도 함께 설정됨)
        AbrformEntity entity = dto.toEntity1();

        // (3) 저장 및 영속화
        AbrformEntity saved = abrformEntityRepository.save(entity);

        // (4) 저장 성공 시 → 반환, 실패 시 → 예외
        if (saved.getAid() > 0) {
            return saved.toDto1();
        } else {
            throw new RuntimeException("책 추천 등록에 실패했습니다.");
        }
    } // fe
*/
    /*  C 초기 구성 | rw 25-04-11 생성 rw 25-04-12 소멸
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
    */



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
    // [2-2]. R2 | rw 25-04-14 수정
    public AbrformDto abFindById(int aid) {
        // fs

        Optional<AbrformEntity> optional = abrformEntityRepository.findById(aid);

        if (optional.isPresent()) {
            AbrformEntity entity = optional.get();
            AbrformDto dto = entity.toDto1(); // 책 정보를 DTO로 변환

            // [추가] 해당 책에 달린 리뷰 리스트도 조회하여 포함
            List<RbrformDto> reviewDtoList = rbrformEntityRepository.findAllByAid(aid)
                    .stream()
                    .map(RbrformEntity::toDto2)
                    .collect(Collectors.toList());

            dto.setReviewList(reviewDtoList); // 📌 책 DTO에 리뷰 리스트 주입

            return dto;
        }

        return null;

        // fe
    }
/*
    [2-2]. R2 초기 구성 | rw 25-04-11 생성 rw 25-04-14 소멸
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

 */
 /*

        return  abrformEntityRepository.findById(aid).map(AbrformEntity::toDto1).orElse(null);
                .map(AbrformEntity::toDto1) : Optional 의 데이터가 null 아니면, map 실행하여 dto로 변환 후 반환
                .orElse(null) : Optional의 데이터가 null 이면 null 반환.


    } // fe
*/

    // [3] U + @Transactional | rw 25-04-12 수정
    @Transactional
    public AbrformDto abUpdate(AbrformDto abrformDto) {
        // fs
        // [3-2] Stream  ===============
        // [1] 전달받은 DTO에서 aid 값을 이용해 해당 책 추천 게시물을 데이터베이스에서 조회
        Optional<AbrformEntity> optional = abrformEntityRepository.findById(abrformDto.getAid());

        // [2] 조회 결과가 존재할 경우 (즉, 해당 게시물이 존재하는 경우)
        if (optional.isPresent()) {
            AbrformEntity entity = optional.get(); // Optional에서 실제 엔티티 객체 추출

            // [3] 입력받은 평문 비밀번호와, DB에 저장된 암호화된 비밀번호를 비교
            // → 비밀번호가 일치하지 않으면 예외를 발생시켜 수정 금지
            if (!EncryptUtil.match(abrformDto.getApwd(), entity.getApwd())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); // 비밀번호가 다르면 수정 불가
            }

            // [4] 비밀번호가 일치하면, 사용자가 입력한 데이터로 기존 데이터를 수정
            entity.setAtitle(abrformDto.getAtitle());     // 제목 수정
            entity.setAwriter(abrformDto.getAwriter());   // 작성자 수정
            entity.setAcontent(abrformDto.getAcontent()); // 내용 수정

            // ! 비밀번호(apwd)는 수정하지 않음 — 필요 시 별도 수정 로직으로 처리

            // [5] 수정된 엔티티를 다시 DTO로 변환하여 반환
            return entity.toDto1();
        }

        // [6] aid에 해당하는 게시물이 존재하지 않을 경우 null 반환
        return null;

        // fe
    }

    /* [3]. U 초기 구성 | rw 25-04-11 생성 rw 25-04-12 소멸
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
     */

    // [4]. D | rw 25-04-13 수정

    public boolean abDelete(int aid, String apwd) {
        // fs

        // [1] 사용자가 삭제하고자 하는 게시글의 번호(aid)를 이용하여 데이터베이스에서 해당 데이터 조회 시도
        Optional<AbrformEntity> optional = abrformEntityRepository.findById(aid);

        // [2] 해당 게시물이 존재할 경우
        if (optional.isPresent()) {
            AbrformEntity entity = optional.get(); // Optional 객체에서 실제 데이터 추출

            // [3] 사용자로부터 전달받은 비밀번호(apwd)를
            // 데이터베이스에 암호화되어 저장된 비밀번호와 비교하여 일치 여부 확인
            if (!EncryptUtil.match(apwd, entity.getApwd())) {
                // 비밀번호가 일치하지 않으면 삭제 불가
                throw new IllegalArgumentException("비밀번호가 일치하지 않아 삭제할 수 없습니다.");
            }

            // [4] 비밀번호가 일치할 경우 해당 게시물 삭제 진행
            abrformEntityRepository.deleteById(aid);
            return true; // 삭제 성공
        }

        // [5] 해당 게시물이 존재하지 않으면 false 반환
        return false;

        // fe
    }

    /* [4]. D 초기 구성 | rw 25-04-11 생성 rw 25-04-13 소멸

    // 일반적인 방식의 삭제 처리 로직입니다.
    // 이 코드는 비밀번호 검증 없이, 존재 여부만 확인하고 삭제를 진행하기 때문에
    // 실제 서비스에서는 보안상 부적절할 수 있습니다.
    // 하지만 학습 목적 및 기능 테스트 목적에선 이해하기 좋은 구조입니다.
    public boolean abDelete(int aid){
        // [4-1] 일반적인 ===============
        // 1. aid(PK 값)를 통해 해당 게시글이 존재하는지 확인
        boolean result = abrformEntityRepository.existsById(aid);
        // 2. 존재하면 삭제 수행
        if(result == true){
            abrformEntityRepository.deleteById(aid);
            return true; // 삭제 성공
        }
        return false; // 존재하지 않음
    }

    // [4-2] Stream 방식 삭제 처리 | rw 25-04-11 생성 (현재 주석 처리, 학습 참고용)
    // 아래 코드는 Java 8의 Optional.map()을 활용하여 코드를 간결하게 작성한 버전입니다.
    // 구조가 짧고 간결한 대신, 초보자에게는 다소 낯설 수 있습니다.
    // → optional 객체가 존재하면 삭제 수행 / 없으면 false 반환
    return abrformEntityRepository.findById(aid)
        .map((entity) -> {
            abrformEntityRepository.deleteById(aid); // 삭제 수행
            return true; // 삭제 성공
        })
        .orElse(false); // 게시글이 존재하지 않으면 false 반환
    */


}
