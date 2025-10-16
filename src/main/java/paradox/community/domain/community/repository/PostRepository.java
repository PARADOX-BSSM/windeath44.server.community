package paradox.community.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostStatus;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(String userId);

    List<Post> findByCharacterId(Long characterId);

    List<Post> findByStatus(PostStatus status);

    List<Post> findByIsBlind();

    List<Post> findAllByOrderByLikesCountDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByOrderByCreatedAtAsc();

    List<Post> findAllByOrderByViewsCountDesc();
}