package paradox.community.domain.community.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.PostComment;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByPostId(Long postId);

    List<PostComment> findByUserId(String userId);

    List<PostComment> findByParentCommentId(Long parentCommentId);

    List<PostComment> findByPostIdAndParentCommentIdIsNull(Long postId);

    @Query("SELECT COUNT(pc) FROM PostComment pc WHERE pc.postId = :postId")
    Long countByPostId(@Param("postId") Long postId);
}