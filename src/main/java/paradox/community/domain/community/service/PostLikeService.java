package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.response.PostLikeResponse;
import paradox.community.domain.community.model.PostLike;
import paradox.community.domain.community.repository.PostLikeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Transactional
    public PostLikeResponse addPostLike(Long postId, String userId) {
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            return null;
        }else {
            PostLike postLike = PostLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .build();

            PostLike saved = postLikeRepository.save(postLike);
            return PostLikeResponse.from(saved);
        }
    }

    @Transactional
    public void removePostLike(Long postId, String userId) {
        if (!postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        }
    }
}
