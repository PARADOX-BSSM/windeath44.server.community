package paradox.community.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.PostCommentLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Long> {

    boolean existsByUserIdAndPostCommentId(String userId, Long postCommentId);

    Optional<PostCommentLike> findByUserIdAndPostCommentId(String userId, Long postCommentId);

    List<PostCommentLike> findByPostCommentId(Long postCommentId);

    List<PostCommentLike> findByUserId(String userId);
}