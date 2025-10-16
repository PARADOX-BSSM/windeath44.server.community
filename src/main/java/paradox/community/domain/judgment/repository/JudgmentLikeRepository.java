package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgmentLikeRepository extends JpaRepository<JudgmentLike, Long> {

    boolean existsByUserIdAndJudgmentId(String userId, Integer judgmentId);

    Optional<JudgmentLike> findByUserIdAndJudgmentId(String userId, Integer judgmentId);

    List<JudgmentLike> findByJudgmentId(Integer judgmentId);

    List<JudgmentLike> findByUserId(String userId);
}
