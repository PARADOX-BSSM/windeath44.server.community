package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentLike;

import java.util.Optional;

@Repository
public interface JudgmentLikeRepository extends JpaRepository<JudgmentLike, Long> {

    Boolean existsByUserIdAndJudgmentId(String userId, Long judgmentId);

    Optional<JudgmentLike> findByUserIdAndJudgmentId(String userId, Long judgmentId);

    void deleteByUserIdAndJudgmentId(String userId, Long judgmentId);
}
