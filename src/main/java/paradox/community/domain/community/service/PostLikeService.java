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

    public PostLikeResponse addPostLike(Long postId, String userId) {
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            return null;
        }
        PostLike postLike = PostLike.builder()
                        .postId(postId).userId(userId).build();

        PostLike saved = postLikeRepository.save(postLike);

        return PostLikeResponse.from(saved);
    }

    public PostLikeResponse removePostLike(Long postId, String userId) {
        if (!postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            return null;
        }
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        return new PostLikeResponse(postId, userId);
    }
}
