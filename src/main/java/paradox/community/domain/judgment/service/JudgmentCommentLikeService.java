package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.response.JudgmentCommentLikeResponse;
import paradox.community.domain.judgment.exception.JudgmentCommentNotFoundException;
import paradox.community.domain.judgment.model.JudgmentComment;
import paradox.community.domain.judgment.model.JudgmentCommentLike;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentCommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JudgmentCommentLikeService {

    private final JudgmentCommentLikeRepository judgmentCommentLikeRepository;
    private final JudgmentCommentRepository judgmentCommentRepository;

    @Transactional
    public JudgmentCommentLikeResponse addJudgmentCommentLike(String userId, Long judgmentCommentId) {
        // If already liked, return existing like info (idempotent)
        return judgmentCommentLikeRepository.findByUserIdAndJudgmentCommentId(userId, judgmentCommentId)
                .map(JudgmentCommentLikeResponse::from)
                .orElseGet(() -> {
                    JudgmentCommentLike judgmentCommentLike = JudgmentCommentLike.builder()
                            .userId(userId)
                            .judgmentCommentId(judgmentCommentId)
                            .build();

                    JudgmentCommentLike saved = judgmentCommentLikeRepository.save(judgmentCommentLike);

                    JudgmentComment comment = judgmentCommentRepository.findById(judgmentCommentId)
                            .orElseThrow(JudgmentCommentNotFoundException::getInstance);
                    comment.incrementLikesCount();
                    return JudgmentCommentLikeResponse.from(saved);
                });
    }

    @Transactional
    public void removeJudgmentCommentLike(String userId, Long judgmentCommentId) {
        // Delete by user + comment id for safety
        judgmentCommentLikeRepository.deleteByUserIdAndJudgmentCommentId(userId, judgmentCommentId);
        JudgmentComment comment = judgmentCommentRepository.findById(judgmentCommentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);
        comment.decrementLikesCount();
    }

    public Boolean isLiked(String userId, Long commentId) {
        return judgmentCommentLikeRepository.findByUserIdAndJudgmentCommentId(userId, commentId).isPresent();
    }
}
