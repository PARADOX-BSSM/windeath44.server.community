package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentCommentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgmentCommentLikeRepository extends JpaRepository<JudgmentCommentLike, Long> {

    boolean existsByUserIdAndJudgmentCommentId(String userId, Long commentId);

    Optional<JudgmentCommentLike> findByUserIdAndJudgmentCommentId(String userId, Long commentId);

    void deleteByUserIdAndJudgmentCommentId(String userId, Long commentId);

    long countByJudgmentCommentId(Long commentId);

    void deleteByJudgmentCommentId(Long commentId);

    List<JudgmentCommentLike> findByJudgmentCommentId(Long commentId);

    List<JudgmentCommentLike> findByUserId(String userId);
}