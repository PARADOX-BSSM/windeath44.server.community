package paradox.community.domain.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.PostCommentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

    Boolean existsByUserIdAndPostCommentId(String userId, Long postCommentId);

    Optional<PostCommentLike> findByUserIdAndPostCommentId(String userId, Long postCommentId);

    @Query("select postId from PostComment where commentId = :postCommentId")
    Long findPostIdByPostCommentId(@Param("postCommentId") Long postCommentId);

    void deleteByUserIdAndPostCommentId(String userId, Long postCommentId);
    Long countByPostCommentId(Long postCommentId);

    void deleteByPostCommentId(Long postCommentId);

    List<PostCommentLike> findByPostCommentId(Long postCommentId);

    List<PostCommentLike> findByUserId(String userId);
}