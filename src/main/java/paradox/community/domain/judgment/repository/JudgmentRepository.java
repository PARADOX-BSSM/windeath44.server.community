package paradox.community.domain.judgment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentInstance;

import java.util.List;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {
    List<Judgment> findByTitleContaining(String title);
    Page<Judgment> findByCharacterId(Long characterId, Pageable pageable);
    Page<Judgment> findByIsEnd(Boolean isEnd, Pageable pageable);
    Page<Judgment> findByInstance(JudgmentInstance instanceId, Pageable pageable);
    Page<Judgment> findByCharacterIdAndIsEnd(Long characterId, Boolean isEnd, Pageable pageable);
    Page<Judgment> findByCharacterIdAndInstance(Long characterId, JudgmentInstance instance, Pageable pageable);
    Page<Judgment> findByIsEndAndInstance(Boolean isEnd, JudgmentInstance instance, Pageable pageable);
    Page<Judgment> findByCharacterIdAndIsEndAndInstance(Long characterId, Boolean isEnd, JudgmentInstance instance, Pageable pageable);
}
