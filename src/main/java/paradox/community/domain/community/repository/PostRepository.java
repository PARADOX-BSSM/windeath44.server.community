package paradox.community.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostStatus;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(String userId);

    List<Post> findByCharacterId(Long characterId);

    List<Post> findByStatus(PostStatus status);

    List<Post> findByIsBlindTrue();

    List<Post> findByIsBlindFalse();

    @Query("SELECT p FROM Post p LEFT JOIN PostLike pl ON p.postId = pl.postId " + "GROUP BY p.postId ORDER BY COUNT(pl.likeId) DESC")
    List<Post> findAllByOrderByLikeCountDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByCreatedAtAsc();

    List<Post> findAllByOrderByViewCountDesc();
}