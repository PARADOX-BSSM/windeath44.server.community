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

    public PostCommentLikeResponse addPostCommentLike(Long commentId, String userId) {
        if (postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, commentId)) {
            return null;
        }

        PostCommentLike postLike = PostCommentLike.builder()
                .postCommentId(commentId).userId(userId).build();

        PostCommentLike saved = postCommentLikeRepository.save(postLike);

        return new PostCommentLikeResponse(
                saved.getPostCommentId(),
                postCommentLikeRepository.findPostIdByPostCommentId(saved.getPostCommentId()),
                saved.getUserId()
        );
    }

    public PostCommentLikeResponse removePostCommentLike(Long commentId, String userId) {
        if (!postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, commentId)) {
            return null;
        }
        postCommentLikeRepository.deleteByUserIdAndPostCommentId(userId, commentId);
        return new PostCommentLikeResponse(
                commentId,
                postCommentLikeRepository.findPostIdByPostCommentId(commentId),
                userId
        );
    }
}
