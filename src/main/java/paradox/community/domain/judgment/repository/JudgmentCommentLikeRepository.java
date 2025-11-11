package paradox.community.domain.judgment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import paradox.community.domain.judgment.model.JudgmentCommentLike;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface JudgmentCommentLikeRepository extends JpaRepository<JudgmentCommentLike, Long> {

    boolean existsByUserIdAndJudgmentCommentId(String userId, Long commentId);

    Optional<JudgmentCommentLike> findByUserIdAndJudgmentCommentId(String userId, Long commentId);

    void deleteByUserIdAndJudgmentCommentId(String userId, Long commentId);

    long countByJudgmentCommentId(Long commentId);

    void deleteByJudgmentCommentId(Long commentId);

    List<JudgmentCommentLike> findByJudgmentCommentId(Long commentId);

    List<JudgmentCommentLike> findByUserId(String userId);

    // Bulk 조회 메서드 추가
    @Query("SELECT jcl.judgmentCommentId as commentId, COUNT(jcl) as count " +
            "FROM JudgmentCommentLike jcl " +
            "WHERE jcl.judgmentCommentId IN :commentIds " +
            "GROUP BY jcl.judgmentCommentId")
    List<CommentLikeCount> countByJudgmentCommentIdInRaw(@Param("commentIds") List<Long> commentIds);

    default Map<Long, Long> countByJudgmentCommentIdIn(List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            return Map.of();
        }
        return countByJudgmentCommentIdInRaw(commentIds).stream()
                .collect(Collectors.toMap(
                        CommentLikeCount::getCommentId,
                        CommentLikeCount::getCount
                ));
    }

    @Query("SELECT jcl FROM JudgmentCommentLike jcl " +
            "WHERE jcl.userId = :userId AND jcl.judgmentCommentId IN :commentIds")
    List<JudgmentCommentLike> findByUserIdAndJudgmentCommentIdInRaw(
            @Param("userId") String userId,
            @Param("commentIds") List<Long> commentIds);

    default Map<Long, Boolean> existsByUserIdAndJudgmentCommentIdIn(String userId, List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            return Map.of();
        }
        List<Long> likedCommentIds = findByUserIdAndJudgmentCommentIdInRaw(userId, commentIds)
                .stream()
                .map(JudgmentCommentLike::getJudgmentCommentId)
                .toList();

        return commentIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        likedCommentIds::contains
                ));
    }

    @Query("DELETE FROM JudgmentCommentLike jcl WHERE jcl.judgmentCommentId IN :commentIds")
    void deleteByJudgmentCommentIdIn(@Param("commentIds") List<Long> commentIds);

    // Projection 인터페이스
    interface CommentLikeCount {
        Long getCommentId();
        Long getCount();
    }
}