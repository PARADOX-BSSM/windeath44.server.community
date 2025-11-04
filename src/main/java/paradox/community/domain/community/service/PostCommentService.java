package paradox.community.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.request.PostCommentRequest;
import paradox.community.domain.community.dto.response.PostCommentResponse;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostComment;
import paradox.community.domain.community.repository.PostCommentLikeRepository;
import paradox.community.domain.community.repository.PostCommentRepository;
import paradox.community.domain.community.repository.PostRepository;
import paradox.community.domain.judgment.dto.request.JudgmentCommentRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentService {
    private final PostCommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostCommentLikeRepository commentLikeRepository;

    @Transactional
    public PostCommentResponse createComment(String userId, Long postId, PostCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + postId));

        Long parentCommentId = request.parentCommentId();
        PostComment parentComment = parentCommentId == null ? null : commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다: " + parentCommentId));

        if (parentComment != null && !parentComment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("부모 댓글이 해당 게시글에 속하지 않습니다.");
        }

        PostComment comment = PostComment.builder()
                .userId(userId)
                .postId(postId)
                .parentCommentId(parentCommentId)
                .body(request.body())
                .build();

        PostComment savedComment = commentRepository.save(comment);

        Long likeCount = 0L;
        Boolean isLiked = false;

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

    public PostCommentResponse updateComment(String userId, Long commentId, PostCommentRequest request, String role) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!role.equals("ADMIN") && !comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.setBody(request.body());
        PostComment updatedComment = commentRepository.save(comment);

        Long likeCount = commentLikeRepository.countByPostCommentId(commentId);

        return new PostCommentResponse(
                updatedComment.getCommentId(),
                updatedComment.getPostId(),
                updatedComment.getUserId(),
                updatedComment.getUserName(),
                updatedComment.getProfile(),
                updatedComment.getParentCommentId(),
                updatedComment.getBody(),
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt(),
                likeCount
        );
    }

    public void deleteComment(String userId, Long commentId, String role) {
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));
        if (!role.equals("ADMIN") && !comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        }

        List<PostComment> replies = commentRepository.findByParentCommentId(commentId);

        for (PostComment reply : replies) {
            commentLikeRepository.deleteByPostCommentId(reply.getCommentId());
        }
        commentRepository.deleteAll(replies);
        commentLikeRepository.deleteByPostCommentId(commentId);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<PostCommentResponse> getCommentsByPostId(String userId, Long postId) {
        commentRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + postId));
        List<PostComment> comments = commentRepository.findByPostIdAndParentCommentIdIsNull(postId);

        return comments.stream()
                .map(comment -> {
                    Long likeCount = commentLikeRepository.countByPostCommentId(comment.getCommentId());

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
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostCommentResponse> getRepliesByParentCommentId(String userId, Long parentCommentId) {
        commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다: " + parentCommentId));

        List<PostComment> replies = commentRepository.findByParentCommentId(parentCommentId);

        return replies.stream()
                .map(reply -> {
                    Long likeCount = commentLikeRepository.countByPostCommentId(reply.getCommentId());

                    return new PostCommentResponse(
                            reply.getCommentId(),
                            reply.getPostId(),
                            reply.getUserId(),
                            reply.getUserName(),
                            reply.getProfile(),
                            reply.getParentCommentId(),
                            reply.getBody(),
                            reply.getCreatedAt(),
                            reply.getUpdatedAt(),
                            likeCount
                    );
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long getCommentsCount(Long postId) {
        commentRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + postId));
        return commentRepository.countByPostId(postId);
    }
}

