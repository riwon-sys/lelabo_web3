// RbrformEntityRepository 구성 | rw 25-04-11 생성
package abrform.model.repository;

import abrform.model.entity.AbrformEntity;
import abrform.model.entity.RbrformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RbrformEntityRepository  extends JpaRepository<RbrformEntity, Integer> {
}
