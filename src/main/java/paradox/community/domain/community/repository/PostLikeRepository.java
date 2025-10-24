package paradox.community.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Boolean existsByUserIdAndPostId(String userId, Long postId);

    Optional<PostLike> findByUserIdAndPostId(String userId, Long postId);

    void deleteByUserIdAndPostId(String userId, Long postId);

    Long countByPostId(Long postId);

    List<PostLike> findByPostId(Long postId);

    List<PostLike> findByUserId(String userId);

    Long post(Post post);
}