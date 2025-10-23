package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.response.PostCommentLikeResponse;
import paradox.community.domain.community.model.PostCommentLike;
import paradox.community.domain.community.repository.PostCommentLikeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentLikeService {

    private final PostCommentLikeRepository postCommentLikeRepository;

    @Transactional
    public PostCommentLikeResponse addPostCommentLike(Long commentId, Long postId, String userId) {
        if (postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, commentId)) {
            return null;
        }else {
            PostCommentLike postCommentLike = PostCommentLike.builder()
                    .postId(postId)
                    .postCommentId(commentId)
                    .build();

            PostCommentLike saved = postCommentLikeRepository.save(postCommentLike);

            return PostCommentLikeResponse.from(saved);
        }
    }

    @Transactional
    public void removePostCommentLike(Long commentId, String userId) {
        if (postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, commentId)) {
            postCommentLikeRepository.deleteByUserIdAndPostCommentId(userId, commentId);
        }
    }

    public Boolean isLiked(Long postCommentId, String userId) {
        return postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, postCommentId);
    }
}
