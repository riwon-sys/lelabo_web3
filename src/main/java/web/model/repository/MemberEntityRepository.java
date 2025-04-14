// MemberEntityRepository.java | rw 25-04-14 생성
package web.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.entity.MemberEntity;
@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {
}