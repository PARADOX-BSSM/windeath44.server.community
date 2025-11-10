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

    // 좋아요
    @Transactional
    public java.util.Optional<PostLikeResponse> addPostLike(Long postId, String userId) {
        if (Boolean.TRUE.equals(postLikeRepository.existsByUserIdAndPostId(userId, postId))) {
            return java.util.Optional.empty();
        } else {
            PostLike postLike = PostLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .build();

            PostLike saved = postLikeRepository.save(postLike);
            return java.util.Optional.of(PostLikeResponse.from(saved));
        }
    }

    // 좋아요 취소
    @Transactional
    public void removePostLike(Long postId, String userId) {
        if (Boolean.TRUE.equals(postLikeRepository.existsByUserIdAndPostId(userId, postId))) {
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        }
    }

    // 좋아요 여부 판단
    public boolean isLiked(String userId, Long postId) {
        return Boolean.TRUE.equals(postLikeRepository.existsByUserIdAndPostId(userId, postId));
    }

}
