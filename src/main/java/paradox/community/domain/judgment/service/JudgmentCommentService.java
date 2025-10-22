package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.request.JudgmentCommentRequest;
import paradox.community.domain.judgment.dto.response.JudgmentCommentResponse;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentComment;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentCommentRepository;
import paradox.community.domain.judgment.repository.JudgmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JudgmentCommentService {
    private final JudgmentCommentRepository commentRepository;
    private final JudgmentRepository judgmentRepository;
    private final JudgmentCommentLikeRepository commentLikeRepository;

    public JudgmentCommentResponse createComment(String userId, Long judgmentId, JudgmentCommentRequest request) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + judgmentId));

        Long parentCommentId = request.parentCommentId();
        JudgmentComment parentComment = parentCommentId == null ? null : commentRepository.findById(parentCommentId).orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다: " + parentCommentId));

        if (parentComment != null && !parentComment.getJudgmentId().equals(judgmentId)) {
            throw new IllegalArgumentException("부모 댓글이 해당 재판에 속하지 않습니다.");
        }

        JudgmentComment comment = JudgmentComment.builder()
                .userId(userId)
                .judgmentId(judgmentId)
                .parentCommentId(parentCommentId)
                .body(request.body())
                .build();

        JudgmentComment savedComment = commentRepository.save(comment);

        Long likeCount = commentLikeRepository.countByCommentId(savedComment.getCommentId());
        Boolean isLiked = commentLikeRepository.existsByUserIdAndCommentId(userId, savedComment.getCommentId());

        log.info("Comment created - commentId: {}, userId: {}, judgmentId: {}", savedComment.getCommentId(), userId, judgmentId);

        return new JudgmentCommentResponse(
                savedComment.getCommentId(),
                savedComment.getUserId(),
                savedComment.getJudgmentId(),
                savedComment.getParentCommentId(),
                savedComment.getBody(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt(),
                likeCount,
                isLiked
        );
    }

    public JudgmentCommentResponse updateComment(String userId, Long commentId, JudgmentCommentRequest request) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.setBody(request.body());
        JudgmentComment updatedComment = commentRepository.save(comment);

        Long likeCount = commentLikeRepository.countByCommentId(commentId);
        Boolean isLiked = commentLikeRepository.existsByUserIdAndCommentId(userId, commentId);

        log.info("Comment update - commentId: {}, userId: {}", commentId, userId);

        return new JudgmentCommentResponse(
                updatedComment.getCommentId(),
                updatedComment.getUserId(),
                updatedComment.getJudgmentId(),
                updatedComment.getParentCommentId(),
                updatedComment.getBody(),
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt(),
                likeCount,
                isLiked
        );
    }

    public void deleteComment(String userId, Long commentId) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        }

        List<JudgmentComment> replies = commentRepository.findByParentCommentId(commentId);

        for (JudgmentComment reply : replies) {
            commentLikeRepository.deleteByCommentId(reply.getCommentId());
        }
        commentRepository.deleteAll(replies);
        commentLikeRepository.deleteByCommentId(commentId);
        commentRepository.delete(comment);
        log.info("Comment deleted - commentId: {}, userId: {}", commentId, userId);
    }

    @Transactional(readOnly = true)
    public List<JudgmentCommentResponse> getCommentsByJudgmentId(String userId, Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + judgmentId));

        List<JudgmentComment> comments = commentRepository.findByJudgmentIdAndParentCommentIdIsNull(judgmentId);

        return comments.stream()
                .map(comment -> {
                    Long likeCount = commentLikeRepository.countByCommentId(comment.getCommentId());
                    Boolean isLiked = commentLikeRepository.existsByUserIdAndCommentId(userId, comment.getCommentId());

                    return new JudgmentCommentResponse(
                            comment.getCommentId(),
                            comment.getUserId(),
                            comment.getJudgmentId(),
                            comment.getParentCommentId(),
                            comment.getBody(),
                            comment.getCreatedAt(),
                            comment.getUpdatedAt(),
                            likeCount,
                            isLiked
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<JudgmentCommentResponse> getRepliesByComment(String userId, Long parentCommentId) {
        commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다: " + parentCommentId));

        List<JudgmentComment> replies = commentRepository.findByParentCommentId(parentCommentId);

        return replies.stream()
                .map(reply -> {
                    Long likeCount = commentLikeRepository.countByCommentId(reply.getCommentId());
                    Boolean isLiked = commentLikeRepository.existsByUserIdAndCommentId(userId, reply.getCommentId());

                    return new JudgmentCommentResponse(
                            reply.getCommentId(),
                            reply.getUserId(),
                            reply.getJudgmentId(),
                            reply.getParentCommentId(),
                            reply.getBody(),
                            reply.getCreatedAt(),
                            reply.getUpdatedAt(),
                            likeCount,
                            isLiked
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long getCommentsCount(Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("재판을 찾을 수 없습니다: " + judgmentId));

        return commentRepository.countByJudgmentId(judgmentId);
    }
}
