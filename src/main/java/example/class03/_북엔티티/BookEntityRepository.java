package example.class03._북엔티티;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEntityRepository
        extends JpaRepository<BookEntity, Integer> {
}
