package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.Judgment;

import java.util.List;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {
    List<Judgment> findByTitleContaining(String title);
    List<Judgment> findByCharacterId(Long characterId);
}
