package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.request.JudgmentCommentRequest;
import paradox.community.domain.judgment.dto.response.JudgmentCommentResponse;
import paradox.community.domain.judgment.exception.JudgmentCommentDeleteForbiddenException;
import paradox.community.domain.judgment.exception.JudgmentCommentNotFoundException;
import paradox.community.domain.judgment.exception.JudgmentNotFoundException;
import paradox.community.domain.judgment.exception.JudgmentParentCommentException;
import paradox.community.domain.judgment.model.JudgmentComment;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentCommentRepository;
import paradox.community.domain.judgment.repository.JudgmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JudgmentCommentService {
    private final JudgmentCommentRepository commentRepository;
    private final JudgmentRepository judgmentRepository;
    private final JudgmentCommentLikeRepository commentLikeRepository;

    @Transactional
    public JudgmentCommentResponse createComment(String userId, Long judgmentId, JudgmentCommentRequest request) {
        judgmentRepository.findById(judgmentId)
            .orElseThrow(JudgmentNotFoundException::getInstance);

        Long parentCommentId = request.parentCommentId();
        if (parentCommentId != null) {
            JudgmentComment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(JudgmentParentCommentException::getInstance);
            if (!parentComment.getJudgmentId().equals(judgmentId)) {
            throw JudgmentParentCommentException.getInstance();
            }
        }

        JudgmentComment comment = JudgmentComment.builder()
                .userId(userId)
                .judgmentId(judgmentId)
                .parentCommentId(parentCommentId)
                .body(request.body())
                .build();

        JudgmentComment savedComment = commentRepository.save(comment);

        Long likeCount = commentLikeRepository.countByJudgmentCommentId(savedComment.getCommentId());
        Boolean isLiked = commentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, savedComment.getCommentId());

        log.info("Comment created - commentId: {}, userId: {}, judgmentId: {}", savedComment.getCommentId(), userId, judgmentId);

        return toResponse(savedComment, likeCount, isLiked);
    }

    @Transactional
    public JudgmentCommentResponse updateComment(String userId, Long commentId, JudgmentCommentRequest request) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);

        if (!comment.getUserId().equals(userId)) {
            throw JudgmentCommentDeleteForbiddenException.getInstance();
        }

        comment.setBody(request.body());
        JudgmentComment updatedComment = commentRepository.save(comment);

        Long likeCount = commentLikeRepository.countByJudgmentCommentId(commentId);
        Boolean isLiked = commentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, commentId);

        log.info("Comment update - commentId: {}, userId: {}", commentId, userId);

        return toResponse(updatedComment, likeCount, isLiked);
    }

    @Transactional
    public void deleteComment(String userId, Long commentId) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);

        if (!comment.getUserId().equals(userId)) {
            throw JudgmentCommentDeleteForbiddenException.getInstance();
        }

        List<JudgmentComment> replies = commentRepository.findByParentCommentId(commentId);

        replies.forEach(r -> commentLikeRepository.deleteByJudgmentCommentId(r.getCommentId()));
        commentLikeRepository.deleteByJudgmentCommentId(commentId);

        commentRepository.deleteAll(replies);
        commentRepository.delete(comment);
        log.info("Comment deleted - commentId: {}, userId: {}", commentId, userId);
    }

    @Transactional(readOnly = true)
    public List<JudgmentCommentResponse> getCommentsByJudgmentId(String userId, Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(JudgmentNotFoundException::getInstance);

        List<JudgmentComment> comments = commentRepository.findByJudgmentIdAndParentCommentIdIsNull(judgmentId);

        return comments.stream()
            .map(comment -> toResponse(comment,
                commentLikeRepository.countByJudgmentCommentId(comment.getCommentId()),
                commentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, comment.getCommentId())
            )).toList();
    }

    @Transactional(readOnly = true)
    public List<JudgmentCommentResponse> getRepliesByParentCommentId(String userId, Long parentCommentId) {
        commentRepository.findById(parentCommentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);

        List<JudgmentComment> replies = commentRepository.findByParentCommentId(parentCommentId);

        return replies.stream()
            .map(reply -> toResponse(reply,
                commentLikeRepository.countByJudgmentCommentId(reply.getCommentId()),
                commentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, reply.getCommentId())
            )).toList();
    }

    @Transactional(readOnly = true)
    public Long getCommentsCount(Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(JudgmentNotFoundException::getInstance);

        return commentRepository.countByJudgmentId(judgmentId);
    }

    private JudgmentCommentResponse toResponse(JudgmentComment comment, Long likeCount, Boolean isLiked) {
    return new JudgmentCommentResponse(
        comment.getCommentId(),
        comment.getUserId(),
        comment.getUserName(),
        comment.getProfile(),
        comment.getJudgmentId(),
        comment.getParentCommentId(),
        comment.getBody(),
        comment.getCreatedAt(),
        comment.getUpdatedAt(),
        likeCount,
        isLiked
    );
    }
}
