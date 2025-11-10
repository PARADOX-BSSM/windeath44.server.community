package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.request.JudgmentCommentRequest;
import paradox.community.domain.judgment.dto.response.JudgmentCommentResponse;
import paradox.community.domain.judgment.exception.*;
import paradox.community.domain.judgment.model.JudgmentComment;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentCommentRepository;
import paradox.community.domain.judgment.repository.JudgmentRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JudgmentCommentService {
    private final JudgmentCommentRepository commentRepository;
    private final JudgmentRepository judgmentRepository;
    private final JudgmentCommentLikeRepository commentLikeRepository;

    @Transactional
    public JudgmentCommentResponse createComment(String userId, Long judgmentId, JudgmentCommentRequest request) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(JudgmentNotFoundException::getInstance);

        Long parentCommentId = request.parentCommentId();
        JudgmentComment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(JudgmentParentCommentException::getInstance);

            // 부모 댓글이 같은 판단글에 속하는지 확인
            if (!parentComment.getJudgmentId().equals(judgmentId)) {
                throw JudgmentParentCommentException.getInstance();
            }

            // 2depth 제한: 부모 댓글이 이미 대댓글이면 생성 불가
            if (parentComment.getParentCommentId() != null) {
                throw JudgmentComment2DepthOverForbiddenException.getInstance();
            }
        }

        JudgmentComment comment = JudgmentComment.builder()
                .userId(userId)
                .judgmentId(judgmentId)
                .parentCommentId(parentCommentId)
                .body(request.body())
                .build();

        JudgmentComment savedComment = commentRepository.save(comment);

        Long likeCount = 0L;
        Boolean isLiked = false;

        return new JudgmentCommentResponse(
                savedComment.getCommentId(),
                savedComment.getUserId(),
                savedComment.getUserName(),
                savedComment.getProfile(),
                savedComment.getJudgmentId(),
                savedComment.getParentCommentId(),
                savedComment.getBody(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt(),
                likeCount,
                isLiked
        );
    }

    @Transactional
    public JudgmentCommentResponse updateComment(String userId, Long commentId, JudgmentCommentRequest request, String role) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);

        if (!role.equals("ADMIN") && !comment.getUserId().equals(userId)) {
            throw JudgmentCommentUpdateForbiddenException.getInstance();
        }

        comment.updateBody(request.body());

        Long likeCount = commentLikeRepository.countByJudgmentCommentId(commentId);
        Boolean isLiked = commentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, commentId);

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

    @Transactional
    public void deleteComment(String userId, Long commentId, String role) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);

        if (!role.equals("ADMIN") && !comment.getUserId().equals(userId)) {
            throw JudgmentCommentDeleteForbiddenException.getInstance();
        }

        // 대댓글이 있는 경우 함께 삭제
        List<JudgmentComment> replies = commentRepository.findByParentCommentId(commentId);

        // 대댓글들의 좋아요 bulk 삭제
        if (!replies.isEmpty()) {
            List<Long> replyIds = replies.stream().map(JudgmentComment::getCommentId).toList();
            commentLikeRepository.deleteByJudgmentCommentIdIn(replyIds);
            commentRepository.deleteAll(replies);
        }

        // 부모 댓글 좋아요 삭제
        commentLikeRepository.deleteByJudgmentCommentId(commentId);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<JudgmentCommentResponse> getCommentsByJudgmentId(String userId, Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(JudgmentNotFoundException::getInstance);

        List<JudgmentComment> comments = commentRepository.findByJudgmentIdAndParentCommentIdIsNull(judgmentId);

        if (comments.isEmpty()) {
            return List.of();
        }

        // 좋아요 수 및 좋아요 여부 bulk 조회
        List<Long> commentIds = comments.stream()
                .map(JudgmentComment::getCommentId)
                .toList();

        Map<Long, Long> likesCountMap = commentLikeRepository.countByJudgmentCommentIdIn(commentIds);
        Map<Long, Boolean> isLikedMap = commentLikeRepository.existsByUserIdAndJudgmentCommentIdIn(userId, commentIds);

        return comments.stream()
                .map(comment -> new JudgmentCommentResponse(
                        comment.getCommentId(),
                        comment.getUserId(),
                        comment.getUserName(),
                        comment.getProfile(),
                        comment.getJudgmentId(),
                        comment.getParentCommentId(),
                        comment.getBody(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        likesCountMap.getOrDefault(comment.getCommentId(), 0L),
                        isLikedMap.getOrDefault(comment.getCommentId(), false)
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JudgmentCommentResponse> getRepliesByParentCommentId(String userId, Long parentCommentId) {
        commentRepository.findById(parentCommentId)
                .orElseThrow(JudgmentParentCommentException::getInstance);

        List<JudgmentComment> replies = commentRepository.findByParentCommentId(parentCommentId);

        if (replies.isEmpty()) {
            return List.of();
        }

        // 좋아요 수 및 좋아요 여부 bulk 조회
        List<Long> replyIds = replies.stream()
                .map(JudgmentComment::getCommentId)
                .toList();

        Map<Long, Long> likesCountMap = commentLikeRepository.countByJudgmentCommentIdIn(replyIds);
        Map<Long, Boolean> isLikedMap = commentLikeRepository.existsByUserIdAndJudgmentCommentIdIn(userId, replyIds);

        return replies.stream()
                .map(reply -> new JudgmentCommentResponse(
                        reply.getCommentId(),
                        reply.getUserId(),
                        reply.getUserName(),
                        reply.getProfile(),
                        reply.getJudgmentId(),
                        reply.getParentCommentId(),
                        reply.getBody(),
                        reply.getCreatedAt(),
                        reply.getUpdatedAt(),
                        likesCountMap.getOrDefault(reply.getCommentId(), 0L),
                        isLikedMap.getOrDefault(reply.getCommentId(), false)
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public JudgmentCommentResponse getCommentById(String userId, Long commentId) {
        JudgmentComment comment = commentRepository.findById(commentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);

        Long likeCount = commentLikeRepository.countByJudgmentCommentId(commentId);
        Boolean isLiked = commentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, commentId);

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

    @Transactional(readOnly = true)
    public Long getCommentsCount(Long judgmentId) {
        judgmentRepository.findById(judgmentId)
                .orElseThrow(JudgmentNotFoundException::getInstance);

        return commentRepository.countByJudgmentId(judgmentId);
    }
}