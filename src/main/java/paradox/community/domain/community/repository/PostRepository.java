package paradox.community.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostStatus;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByCharacterIdAndStatusAndIsBlind(Long characterId, PostStatus status, Boolean isBLind, Pageable pageable);

    Page<Post> findByCharacterIdAndIsBlind(Long characterId, Boolean isBlind, Pageable pageable);

    Page<Post> findByStatusAndIsBlind(PostStatus status, Boolean isBlind, Pageable pageable);

    Page<Post> findByCharacterIdAndStatus(Long characterId, PostStatus status, Pageable pageable);

    List<Post> findByUserId(String userId);

    Page<Post> findByCharacterId(Long characterId, Pageable pageable);

    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    Page<Post> findByIsBlind(Boolean isBlind, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN PostLike pl ON p.postId = pl.postId " + "GROUP BY p.postId ORDER BY COUNT(pl.likeId) DESC")
    List<Post> findAllByOrderByLikeCountDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByCreatedAtAsc();

    List<Post> findAllByOrderByViewsCountDesc();
}