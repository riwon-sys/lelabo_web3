// RbrformEntityRepository 구성 | rw 25-04-11 생성
package abrform.model.repository;


import abrform.model.entity.RbrformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RbrformEntityRepository  extends JpaRepository<RbrformEntity, Integer> {
    // 책 추천 ID(aid) 기준으로 해당 책에 달린 리뷰 전체 조회
    List<RbrformEntity> findAllByAid(int aid);
}
