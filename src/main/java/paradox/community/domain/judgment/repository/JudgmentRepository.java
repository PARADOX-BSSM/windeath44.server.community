package paradox.community.domain.judgment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentStatus;

import java.util.List;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {
    List<Judgment> findByTitleContaining(String title);
    Page<Judgment> findByCharacterId(Long characterId, Pageable pageable);
    Page<Judgment> findByIsEnd(Boolean isEnd, Pageable pageable);
    Page<Judgment> findByInstance(JudgmentStatus instanceId, Pageable pageable);
    Page<Judgment> findByCharacterIdAndIsEnd(Long characterId, Boolean isEnd, Pageable pageable);
    Page<Judgment> findByCharacterIdAndInstance(Long characterId, JudgmentStatus instance, Pageable pageable);
    Page<Judgment> findByIsEndAndInstance(Boolean isEnd, JudgmentStatus instance, Pageable pageable);
    Page<Judgment> findByCharacterIdAndIsEndAndInstance(Long characterId, Boolean isEnd, JudgmentStatus instance, Pageable pageable);
}
