package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.request.PostCommentRequest;
import paradox.community.domain.community.dto.response.PostCommentResponse;
import paradox.community.domain.community.exception.*;
import paradox.community.domain.community.model.PostComment;
import paradox.community.domain.community.repository.PostCommentLikeRepository;
import paradox.community.domain.community.repository.PostCommentRepository;
import paradox.community.domain.community.repository.PostRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentService {
    private final PostCommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostCommentLikeRepository commentLikeRepository;

    @Transactional
    public PostCommentResponse createComment(String userId, Long postId, PostCommentRequest request) {
        postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        Long parentCommentId = request.parentCommentId();
        PostComment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(PostCommentParentNotFoundException::getInstance);

            // 부모 댓글이 같은 게시글에 속하는지 확인
            if (!parentComment.getPostId().equals(postId)) {
                throw PostCommentParentNotHereException.getInstance();
            }

            // 2depth 제한: 부모 댓글이 이미 대댓글이면 생성 불가
            if (parentComment.getParentCommentId() != null) {
                throw PostComment2DepthOverForbiddenException.getInstance();
            }
        }

        PostComment comment = PostComment.builder()
                .userId(userId)
                .postId(postId)
                .parentCommentId(parentCommentId)
                .body(request.body())
                .build();

        PostComment savedComment = commentRepository.save(comment);

        Long likeCount = 0L;

        return new PostCommentResponse(
                savedComment.getCommentId(),
                savedComment.getPostId(),
                savedComment.getUserId(),
                savedComment.getUserName(),
                savedComment.getProfile(),
                savedComment.getParentCommentId(),
                savedComment.getBody(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt(),
                likeCount
        );
    }

    @Transactional
    public PostCommentResponse updateComment(String userId, Long commentId, PostCommentRequest request, String role) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(PostCommentNotFoundException::getInstance);

        if (!role.equals("ADMIN") && !comment.getUserId().equals(userId)) {
            throw PostCommentUpdateForbiddenException.getInstance();
        }

        comment.updateBody(request.body());

        Long likeCount = commentLikeRepository.countByPostCommentId(commentId);

        return new PostCommentResponse(
                comment.getCommentId(),
                comment.getPostId(),
                comment.getUserId(),
                comment.getUserName(),
                comment.getProfile(),
                comment.getParentCommentId(),
                comment.getBody(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                likeCount
        );
    }

    @Transactional
    public void deleteComment(String userId, Long commentId, String role) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(PostCommentNotFoundException::getInstance);

        if (!role.equals("ADMIN") && !comment.getUserId().equals(userId)) {
            throw PostCommentDeleteForbiddenException.getInstance();
        }

        // 대댓글이 있는 경우 함께 삭제
        List<PostComment> replies = commentRepository.findByParentCommentId(commentId);

        // 대댓글들의 좋아요 bulk 삭제
        if (!replies.isEmpty()) {
            List<Long> replyIds = replies.stream().map(PostComment::getCommentId).toList();
            commentLikeRepository.deleteByPostCommentIdIn(replyIds);
            commentRepository.deleteAll(replies);
        }

        // 부모 댓글 좋아요 삭제
        commentLikeRepository.deleteByPostCommentId(commentId);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<PostCommentResponse> getCommentsByPostId(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::getInstance);

        List<PostComment> comments = commentRepository.findByPostIdAndParentCommentIdIsNull(postId);

        if (comments.isEmpty()) {
            return List.of();
        }

        // 좋아요 수 bulk 조회
        List<Long> commentIds = comments.stream()
                .map(PostComment::getCommentId)
                .toList();

        Map<Long, Long> likesCountMap = commentLikeRepository.countByPostCommentIdIn(commentIds);

        return comments.stream()
                .map(comment -> new PostCommentResponse(
                        comment.getCommentId(),
                        comment.getPostId(),
                        comment.getUserId(),
                        comment.getUserName(),
                        comment.getProfile(),
                        comment.getParentCommentId(),
                        comment.getBody(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        likesCountMap.getOrDefault(comment.getCommentId(), 0L)
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostCommentResponse> getRepliesByParentCommentId(Long parentCommentId) {
        commentRepository.findById(parentCommentId)
                .orElseThrow(PostCommentParentNotFoundException::getInstance);

        List<PostComment> replies = commentRepository.findByParentCommentId(parentCommentId);

        if (replies.isEmpty()) {
            return List.of();
        }

        // 좋아요 수 bulk 조회
        List<Long> replyIds = replies.stream()
                .map(PostComment::getCommentId)
                .toList();

        Map<Long, Long> likesCountMap = commentLikeRepository.countByPostCommentIdIn(replyIds);

        return replies.stream()
                .map(reply -> new PostCommentResponse(
                        reply.getCommentId(),
                        reply.getPostId(),
                        reply.getUserId(),
                        reply.getUserName(),
                        reply.getProfile(),
                        reply.getParentCommentId(),
                        reply.getBody(),
                        reply.getCreatedAt(),
                        reply.getUpdatedAt(),
                        likesCountMap.getOrDefault(reply.getCommentId(), 0L)
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public PostCommentResponse getCommentById(Long commentId) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(PostCommentNotFoundException::getInstance);

        Long likeCount = commentLikeRepository.countByPostCommentId(commentId);

        return new PostCommentResponse(
                comment.getCommentId(),
                comment.getPostId(),
                comment.getUserId(),
                comment.getUserName(),
                comment.getProfile(),
                comment.getParentCommentId(),
                comment.getBody(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                likeCount
        );
    }
}