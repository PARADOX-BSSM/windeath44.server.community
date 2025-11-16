package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.response.PostCommentLikeResponse;
import paradox.community.domain.community.exception.PostCommentNotFoundException;
import paradox.community.domain.community.model.PostComment;
import paradox.community.domain.community.model.PostCommentLike;
import paradox.community.domain.community.repository.PostCommentLikeRepository;
import paradox.community.domain.community.repository.PostCommentRepository;
import paradox.community.domain.community.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentLikeService {

    private final PostCommentLikeRepository postCommentLikeRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public java.util.Optional<PostCommentLikeResponse> addPostCommentLike(Long commentId, String userId) {
        if (Boolean.TRUE.equals(postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, commentId))) {
            return java.util.Optional.empty();
        }else {
            PostCommentLike postCommentLike = PostCommentLike.builder()
                    .postCommentId(commentId)
                    .userId(userId)
                    .build();

            PostCommentLike saved = postCommentLikeRepository.save(postCommentLike);

            PostComment comment = postCommentRepository.findById(commentId)
                    .orElseThrow(PostCommentNotFoundException::getInstance);
            comment.increaseLikes();
            return java.util.Optional.of(PostCommentLikeResponse.from(saved));
        }
    }

    @Transactional
    public void removePostCommentLike(Long commentId, String userId) {
        if (Boolean.TRUE.equals(postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, commentId))) {
            postCommentLikeRepository.deleteByUserIdAndPostCommentId(userId, commentId);

            PostComment comment  = postCommentRepository.findById(commentId)
                    .orElseThrow(PostCommentNotFoundException::getInstance);
            comment.decreaseLikes();
        }
    }

    public boolean isLiked(Long postCommentId, String userId) {
        return Boolean.TRUE.equals(postCommentLikeRepository.existsByUserIdAndPostCommentId(userId, postCommentId));
    }
}
