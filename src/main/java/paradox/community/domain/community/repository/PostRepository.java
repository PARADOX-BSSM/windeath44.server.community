package paradox.community.domain.community.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByStatusAndIsBlind(PostStatus status, Boolean isBlind, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isDeleted = false AND (:status IS NULL OR p.status = :status) AND (:isBlind IS NULL OR p.isBlind = :isBlind) AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Post> searchPosts(@Param("status") PostStatus status, @Param("isBlind") Boolean isBlind, @Param("title") String title, Pageable pageable);

    List<Post> findByUserId(String userId);

    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    Page<Post> findByIsBlind(Boolean isBlind, Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN PostLike pl ON p.postId = pl.postId WHERE p.isDeleted = false GROUP BY p.postId ORDER BY COUNT(pl.likeId) DESC")
    List<Post> findAllByOrderByLikeCountDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByCreatedAtAsc();

    List<Post> findAllByOrderByViewsCountDesc();

    Optional<Post> findByPostIdAndIsDeletedFalse(Long postId);
}
