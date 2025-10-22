package paradox.community.domain.judgment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgmentLikeRepository extends JpaRepository<JudgmentLike, Long> {

    Boolean existsByUserIdAndJudgmentId(String userId, Long judgmentId);

    Page<JudgmentLike> findByUserIdAndJudgmentId(String userId, Long judgmentId);

    Void deleteByUserIdAndJudgmentId(String userId, Long judgmentId);

    Long countByJudgmentId(Long judgmentId);

    List<JudgmentLike> findByJudgmentId(Long judgmentId);

    List<JudgmentLike> findByUserId(String userId);
}
