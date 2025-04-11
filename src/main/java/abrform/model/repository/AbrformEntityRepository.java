// AbrformEntityRepository 구성 | rw 25-04-11 생성

package abrform.model.repository;

import abrform.model.entity.AbrformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import os.model.entity.OsEntity;
@Repository
public interface AbrformEntityRepository extends JpaRepository<AbrformEntity, Integer> {}

