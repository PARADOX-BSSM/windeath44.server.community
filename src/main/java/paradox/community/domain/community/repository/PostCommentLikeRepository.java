package paradox.community.domain.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.PostCommentLike;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

    Boolean existsByUserIdAndPostCommentId(String userId, Long postCommentId);

    Optional<PostCommentLike> findByUserIdAndPostCommentId(String userId, Long postCommentId);

    @Query("SELECT pc.postId FROM PostComment pc WHERE pc.commentId = :postCommentId")
    Long findPostIdByPostCommentId(@Param("postCommentId") Long postCommentId);

    void deleteByUserIdAndPostCommentId(String userId, Long postCommentId);

    Long countByPostCommentId(Long postCommentId);

    void deleteByPostCommentId(Long postCommentId);

    List<PostCommentLike> findByPostCommentId(Long postCommentId);

    List<PostCommentLike> findByUserId(String userId);

    // Bulk 삭제 - 여러 댓글의 좋아요 한번에 삭제
    @Modifying
    @Query("DELETE FROM PostCommentLike pcl WHERE pcl.postCommentId IN :postCommentIds")
    void deleteByPostCommentIdIn(@Param("postCommentIds") List<Long> postCommentIds);

    // Bulk 조회 - 여러 댓글의 좋아요 수를 한번에 조회
    @Query("SELECT pcl.postCommentId as commentId, COUNT(pcl) as likeCount " +
            "FROM PostCommentLike pcl " +
            "WHERE pcl.postCommentId IN :postCommentIds " +
            "GROUP BY pcl.postCommentId")
    List<CommentLikeCount> countByPostCommentIdInBulk(@Param("postCommentIds") List<Long> postCommentIds);

    // Default method로 Map 형태로 반환
    default Map<Long, Long> countByPostCommentIdIn(List<Long> postCommentIds) {
        if (postCommentIds == null || postCommentIds.isEmpty()) {
            return Map.of();
        }

        return countByPostCommentIdInBulk(postCommentIds).stream()
                .collect(Collectors.toMap(
                        CommentLikeCount::getCommentId,
                        CommentLikeCount::getLikeCount
                ));
    }

    // Projection interface for bulk count query
    interface CommentLikeCount {
        Long getCommentId();
        Long getLikeCount();
    }
}