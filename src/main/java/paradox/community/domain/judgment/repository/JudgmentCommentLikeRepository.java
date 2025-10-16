package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentCommentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgmentCommentLikeRepository extends JpaRepository<JudgmentCommentLike, Long> {

    boolean existsByUserIdAndJudgmentCommentId(String userId, Long judgmentCommentId);

    Optional<JudgmentCommentLike> findByUserIdAndJudgmentCommentId(String userId, Long judgmentCommentId);

    List<JudgmentCommentLike> findByJudgmentCommentId(Long judgmentCommentId);

    List<JudgmentCommentLike> findByUserId(String userId);
}