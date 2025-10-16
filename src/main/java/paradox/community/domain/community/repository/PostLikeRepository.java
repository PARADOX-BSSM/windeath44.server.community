package paradox.community.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paradox.community.domain.community.model.PostLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserIdAndPostId(String userId, Integer postId);

    Optional<PostLike> findByUserIdAndPostId(String userId, Integer postId);

    List<PostLike> findByPostId(Integer postId);

    List<PostLike> findByUserId(String userId);
}