package example.class03.과제03.model.repository;

import example.class03.과제03.model.entity.CourseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CourseEntityRepository
        extends JpaRepository<CourseEntity,Integer> {
}
