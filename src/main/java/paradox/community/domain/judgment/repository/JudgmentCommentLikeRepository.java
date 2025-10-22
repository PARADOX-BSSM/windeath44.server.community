package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentCommentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface JudgmentCommentLikeRepository extends JpaRepository<JudgmentCommentLike, Long> {

    boolean existsByUserIdAndCommentId(String userId, Long commentId);

    Optional<JudgmentCommentLike> findByUserIdAndCommentId(String userId, Long commentId);

    void deleteByUserIdAndCommentId(String userId, Long commentId);

    long countByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);

    List<JudgmentCommentLike> findByCommentId(Long commentId);

    List<JudgmentCommentLike> findByUserId(String userId);
}