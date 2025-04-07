package example.class03._과제03.model.repository;

import example.class03._과제03.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEntityRepository
        extends JpaRepository<StudentEntity,Integer> {
}
