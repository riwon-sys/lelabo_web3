// RbrformService구성 | rw 25-04-11 생성
package abrform.service;

import abrform.model.dto.RbrformDto;
import abrform.model.entity.RbrformEntity;
import abrform.model.repository.RbrformEntityRepository;
import abrform.util.EncryptUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RbrformService {
    private final RbrformEntityRepository rbrformEntityRepository;

    // [1]. C | rw 25-04-13 수정
    public RbrformDto rbPost(RbrformDto dto) {
        // fs

        // 1. 비밀번호 암호화
        dto.setRpwd(EncryptUtil.encode(dto.getRpwd()));

        // 2. 파일 업로드 처리
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            try {
                String originalFilename = dto.getFile().getOriginalFilename();
                String uploadPath = "C:\\Users\\TJ-BU-702-P24\\IdeaProjects\\lelabo_web3\\build\\resources\\main\\static\\upload\\" + originalFilename;
                dto.getFile().transferTo(new File(uploadPath));
                dto.setRimg(originalFilename); // 파일명만 저장
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
            }
        }

        // 3. DTO → Entity 변환 및 저장
        RbrformEntity entity = dto.toEntity2();
        RbrformEntity saved = rbrformEntityRepository.save(entity);

        // 4. 결과 반환
        if (saved.getRno() > 0) {
            return saved.toDto2();
        } else {
            throw new RuntimeException("리뷰 등록 실패");
        }

        // fe
    }

/*
    // [1]. C | rw 25-04-13 수정
    public RbrformDto rbPost(RbrformDto rbrformDto) {
        // fs

        // [1] 사용자로부터 입력받은 평문 비밀번호(rpwd)를 BCrypt로 암호화합니다.
        // 암호화된 비밀번호를 DTO에 다시 주입하여 Entity로 변환 시 적용되도록 합니다.
        rbrformDto.setRpwd(EncryptUtil.encode(rbrformDto.getRpwd()));

        // [2] DTO → Entity 변환
        RbrformEntity entity = rbrformDto.toEntity2();

        // [3] DB에 저장 (영속화)
        RbrformEntity saved = rbrformEntityRepository.save(entity);

        // [4] 저장된 데이터의 PK(rno)가 유효한 경우 DTO로 변환하여 반환
        if (saved.getRno() > 0) {
            return saved.toDto2();
        } else {
            return null; // 저장 실패 시 null 반환 (예외 처리 방식으로 교체 권장)
        }
*/
        // fe
    /*  [1]. C 초기 구성 | rw 25-04-11 생성 rw 25-04-12 소멸
        // 1. dto를 entity변환
        RbrformEntity rbrformEntity = rbrformDto.toEntity2();
        // 2. entity를 save 영속화, db레코드 매칭 및 등록
        RbrformEntity saveEntity2 = rbrformEntityRepository.save(rbrformEntity);
        // 3. save로부터 반환된 엔티티(영속화)된 결과가 존재하면
        if (saveEntity2.getRno() > 0) {
        // 4. Dto로 변환
        return saveEntity2.toDto2(); // entity를 dto로 변환하여 반환
        } else {
        // 결과가 존재하지 않으면
        return null; // null 반환
        }
    */

    // [2]. R | rw 25-04-13 수정
    public List<RbrformDto> rbFindAll() {
        // fs

        // [1] 데이터베이스에서 모든 리뷰 엔티티 조회
        List<RbrformEntity> rbrformEntityList = rbrformEntityRepository.findAll();

        // [2] 조회된 엔티티 리스트를 DTO 리스트로 변환
        List<RbrformDto> rbrformDtoList = rbrformEntityList.stream()
                .map(RbrformEntity::toDto2) // 각 엔티티 객체를 DTO로 변환
                .collect(Collectors.toList()); // DTO 리스트로 수집

        // [3] 변환된 DTO 리스트 반환
        return rbrformDtoList;

        // fe
    }
/*
    // [2]. R
    public List<RbrformDto>rbFindAll(){
        // [2-2] Stream  ===============
        return rbrformEntityRepository.findAll().stream()
               .map(RbrformEntity::toDto2)
               .collect(Collectors.toList());
*/
    /*
        // [2-1] 일반적인 ===============
        List<RbrformEntity>rbrformEntityList=rbrformEntityRepository.findAll(); // DtoList 생성
        for (int i=0; i<rbrformEntityList.size(); i++){ // EntityList 순회
            RbrformDto rbrformDto = rbrformEntityList.get(i).toDto2(); // i번째 Entity를 Dto로 변환
            rbrformDtoList.add(rbrformDto); // DtoList 저장
        } // For E
        // 2. 결과 반환
        return rbrformDtoList;

    }
*/
// [2-2]. R2 | rw 25-04-13 수정
public RbrformDto rbFindById(int rno) {
    // fs

    // [1] 전달받은 rno(PK) 값을 이용해 DB에서 해당 리뷰 조회 시도
    Optional<RbrformEntity> optional = rbrformEntityRepository.findById(rno);

    // [2] 해당 리뷰가 존재할 경우
    if (optional.isPresent()) {
        RbrformEntity entity = optional.get(); // Optional에서 실제 엔티티 꺼내기

        // [3] 엔티티를 DTO로 변환하여 반환
        return entity.toDto2();
    }

    // [4] 해당 리뷰가 존재하지 않을 경우 null 반환
    return null;

    // fe
}
/*
    // [2-2]. R2
    public RbrformDto rbFindById(int rno){
        // [2-2-2] Stream  ===============
        return rbrformEntityRepository.findById(rno)
                .map(RbrformEntity::toDto2)
                .orElse(null);
        // Optional 의 데이터가 null 아니면 map 을 실행하여 Dto로 변환 후 반환하고,
        // Optional 의 데이터가 null 이면 null 반환
*/

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
    }

 */
// [3]. U | rw 25-04-18 수정
@Transactional
public RbrformDto rbUpdate(RbrformDto rbrformDto) {
    // fs

    System.out.println("▶ 수정 요청: rno = " + rbrformDto.getRno());

    Optional<RbrformEntity> optional = rbrformEntityRepository.findById(rbrformDto.getRno());

    if (optional.isPresent()) {
        RbrformEntity entity = optional.get();

        if (!EncryptUtil.match(rbrformDto.getRpwd(), entity.getRpwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않아 수정할 수 없습니다.");
        }

        // [파일 업로드 처리]
        MultipartFile newFile = rbrformDto.getFile();
        if (newFile != null && !newFile.isEmpty()) {
            try {
                // UUID 기반 파일명
                String uuid = UUID.randomUUID().toString();
                String originalName = newFile.getOriginalFilename();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                String newFilename = uuid + extension;

                String uploadPath = "C:\\Users\\TJ-BU-702-P24\\IdeaProjects\\lelabo_web3\\build\\resources\\main\\static\\upload\\" + newFilename;
                newFile.transferTo(new File(uploadPath));

                entity.setRimg(newFilename); // DB에는 새 파일명만 저장

                System.out.println("▶ 파일 저장 완료: " + newFilename);

            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
            }
        }

        // [내용 수정]
        entity.setRtitle(rbrformDto.getRtitle());
        entity.setRwriter(rbrformDto.getRwriter());
        entity.setRcontent(rbrformDto.getRcontent());
        entity.setUpdateAt(LocalDateTime.now());

        return entity.toDto2();
    }

    return null;
    // fe
}
/*
    // [3]. U | rw 25-04-13 수정
@Transactional
public RbrformDto rbUpdate(RbrformDto rbrformDto) {
    // fs

    // [1] 수정할 리뷰의 고유번호 rno를 이용하여 DB에서 리뷰 조회 시도
    Optional<RbrformEntity> optional = rbrformEntityRepository.findById(rbrformDto.getRno());

    // [2] 해당 리뷰가 존재하는 경우
    if (optional.isPresent()) {
        RbrformEntity entity = optional.get(); // Optional에서 실제 리뷰 엔티티 꺼내기

        // [3] 사용자가 입력한 평문 비밀번호와 DB에 저장된 암호화된 비밀번호 비교
        if (!EncryptUtil.match(rbrformDto.getRpwd(), entity.getRpwd())) {
            // 비밀번호가 일치하지 않으면 수정 거부 (예외 발생)
            throw new IllegalArgumentException("비밀번호가 일치하지 않아 수정할 수 없습니다.");
        }

        // [4] 비밀번호가 일치하는 경우 → 사용자가 입력한 내용으로 엔티티 업데이트
        entity.setRtitle(rbrformDto.getRtitle());     // 제목 수정
        entity.setRwriter(rbrformDto.getRwriter());   // 작성자 수정
        entity.setRcontent(rbrformDto.getRcontent()); // 내용 수정

        // 비밀번호는 수정하지 않음 (필요 시 별도 로직 처리)

        // [5] 수정된 엔티티를 DTO로 변환하여 반환
        return entity.toDto2();
    }

    // [6] 해당 리뷰가 존재하지 않으면 null 반환
    return null;

    // fe
}
*/
    /*
    // [3] U + @Transactional
    public RbrformDto rbUpdate(RbrformDto rbrformDto) {
        // fs

        return rbrformEntityRepository.findById(rbrformDto.getRno())
                .map((entity) -> {
                    // [1] 입력한 평문 비밀번호와 암호화된 비밀번호를 비교합니다.
                    if (!EncryptUtil.match(rbrformDto.getRpwd(), entity.getRpwd())) {
                        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                    }

                    // [2] 비밀번호가 일치하면 제목, 작성자, 내용만 수정합니다.
                    entity.setRtitle(rbrformDto.getRtitle());
                    entity.setRwriter(rbrformDto.getRwriter());
                    entity.setRcontent(rbrformDto.getRcontent());

                    // [3] 비밀번호는 덮어쓰지 않습니다.

                    // [4] 수정된 entity → DTO로 변환 후 반환
                    return entity.toDto2();
                })
                .orElse(null); // 게시글이 없을 경우 null 반환

        // fe
    }
 */
    /*
        // [3-1] 일반적인 ===============
        1. PK 식별자 이용 Entity 조회 [ FindById() ]
        // Optional 클래스 : null을 제어하는 메소드들을 제공하는 클래스
        Optional<RbrformEntity>optional=rbrformEntityRepository.findById(rbrformDto.getRno());
        2. 조회 한 결과가 존재 [ .isPresent() ]
        (optional.isPresent()){
           // 3. Optional에서 Entity 꺼내기
           RbrformEntity rbrformEntity=optional.get();
           // 4. 꺼낸 Entity 수정하기, 입력받은 Dto값을 Entity에 대입하기
           rbrformEntity.setRtitle(rbrformDto.getRtitle());
           rbrformEntity.setRwriter(rbrfomrDto.getRwriter());
           rbrformEntity.setRcontent(rbrformDto.getRwriter());
           rbrformEntity.setRpwd(rbrformDto.getRpwd());
           // 5. Dto로 변환 후 반환
           return rbrformEntity.toDto2();
        }
        return null;
    */
    // [4]. D | rw 25-04-13 수정
    @Transactional
    public boolean rbDelete(int rno, String rpwd) {
        // fs

        // [1] 삭제하고자 하는 리뷰 번호 rno로 DB에서 해당 리뷰 조회 시도
        Optional<RbrformEntity> optional = rbrformEntityRepository.findById(rno);

        // [2] 조회 결과가 존재하는 경우 (리뷰가 존재함)
        if (optional.isPresent()) {
            RbrformEntity entity = optional.get(); // Optional 객체에서 실제 리뷰 엔티티 꺼내기

            // [3] 사용자로부터 전달받은 평문 비밀번호와 DB에 저장된 암호화된 비밀번호 비교
            if (!EncryptUtil.match(rpwd, entity.getRpwd())) {
                // 비밀번호가 일치하지 않으면 삭제 불가
                throw new IllegalArgumentException("비밀번호가 일치하지 않아 삭제할 수 없습니다.");
            }

            // [4] 비밀번호가 일치하면 리뷰 삭제 진행
            rbrformEntityRepository.deleteById(rno);
            return true; // 삭제 성공
        }

        // [5] 해당 리뷰가 존재하지 않을 경우 삭제 실패
        return false;

        // fe
    }

/*
    // [4]. D | rw 25-04-13 수정
    @Transactional
    public boolean rbDelete(int rno, String rpwd) {
        // fs

        // [1] 삭제하고자 하는 리뷰 번호 rno로 DB에서 리뷰 조회
        Optional<RbrformEntity> optional = rbrformEntityRepository.findById(rno);

        // [2] 리뷰가 존재할 경우
        if (optional.isPresent()) {
            RbrformEntity entity = optional.get(); // 실제 리뷰 엔티티 추출

            // [3] 비밀번호 비교 (사용자 입력 vs DB 암호화 저장값)
            if (!EncryptUtil.match(rpwd, entity.getRpwd())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않아 삭제할 수 없습니다.");
            }

            // [4] 비밀번호가 일치하면 삭제 진행
            rbrformEntityRepository.deleteById(rno);
            return true; // 삭제 성공
        }

        // [5] 존재하지 않는 rno인 경우 → 삭제 실패
        return false;

        // fe
    }
*/




/*
    // [4]. D 초기 구성 | rw 25-04-11 생성 rw 25-04-12 소멸
    public boolean rbDelete(int rno){
        // [4-2] Stream  ===============
        return  rbrformEntityRepository.findById(rno)
                .map((entity)->{
                    rbrformEntityRepository.deleteById(rno);
                    return true;// 삭제 성공
                })
                .orElse(false);

        // [4-1] 일반적인 ===============
        // 1. PK 식별자 이용 Entity 조회
        // [ find By Id() ]반환타입 : optional vs [ exestsById() ]반환타입 : boolean
        // 2. 조회한 결과가 존재
        if(result==true){
           // 3. 영속성 제거 [ deleteById(PK); ]
           rbrformEntityRepostitory.deleteById(rno);
           return true; // 삭제 성공
        }


    }
*/


    // [5]. R3 특정 책(aid)에 대한 리뷰 전체 조회 | rw 25-04-13 생성
    public List<RbrformDto> rbFindByAid(int aid) {
        // fs

        // [1] 특정 책 번호(aid)를 기준으로 해당 책에 작성된 모든 리뷰 조회
        List<RbrformEntity> entityList = rbrformEntityRepository.findAllByAid(aid);

        // [2] 조회된 리뷰 엔티티 리스트를 Dto 리스트로 변환
        List<RbrformDto> dtoList = entityList.stream()
                .map(RbrformEntity::toDto2)
                .collect(Collectors.toList());

        // [3] 변환된 Dto 리스트 반환
        return dtoList;

        // fe
    }

    // [6]. aid 기준 리뷰 전체 조회 | rw 25-04-13 생성
    public List<RbrformDto> rbFindAllByAid(int aid) {
        // fs

        // [1] aid 기준으로 리뷰 목록 조회
        List<RbrformEntity> entityList = rbrformEntityRepository.findAllByAid(aid);

        // [2] Entity → DTO 변환
        List<RbrformDto> dtoList = entityList.stream()
                .map(RbrformEntity::toDto2)
                .collect(Collectors.toList());

        // [3] 반환
        return dtoList;

        // fe
    }
}
