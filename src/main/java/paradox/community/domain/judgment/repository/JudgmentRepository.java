package paradox.community.domain.judgment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentInstance;
import paradox.community.domain.judgment.model.JudgmentStatus;
import paradox.community.domain.judgment.dto.response.JudgmentRankResponse;

import java.util.List;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {
    Page<Judgment> findByStatus(JudgmentStatus status, Pageable pageable);
    Page<Judgment> findByInstance(JudgmentInstance instanceId, Pageable pageable);
    Page<Judgment> findByStatusAndInstance(JudgmentStatus status, JudgmentInstance instance, Pageable pageable);
    Page<Judgment> findByCharacterId(Long characterId, Pageable pageable);
    Page<Judgment> findByCharacterIdAndStatus(Long characterId, JudgmentStatus status, Pageable pageable);
    Page<Judgment> findByCharacterIdAndInstance(Long characterId, JudgmentInstance instance, Pageable pageable);
    Page<Judgment> findByCharacterIdAndStatusAndInstance(Long characterId, JudgmentStatus status, JudgmentInstance instance, Pageable pageable);

    @Query("""
            SELECT j.characterId, (COUNT(v) + j.likesCount)
            FROM Judgment j
            LEFT JOIN Vote v ON j.judgmentId = v.judgmentId
            WHERE j.status <> 'Ended'
            GROUP BY j.judgmentId, j.characterId, j.likesCount
            ORDER BY (COUNT(v) + j.likesCount) DESC, j.judgmentId DESC
            """)
    Page<JudgmentRankResponse> findTopRankings(Pageable pageable);
}
