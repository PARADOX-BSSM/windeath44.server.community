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
    public java.util.Optional<JudgmentCommentLikeResponse> addJudgmentCommentLike(String userId, Long judgmentCommentId) {
        if (Boolean.TRUE.equals(judgmentCommentLikeRepository.existsByUserIdAndJudgmentCommentId(userId, judgmentCommentId))) {
            return java.util.Optional.empty();
        }

        JudgmentCommentLike judgmentCommentLike = JudgmentCommentLike.builder()
                .userId(userId)
                .judgmentCommentId(judgmentCommentId)
                .build();

        JudgmentCommentLike saved = judgmentCommentLikeRepository.save(judgmentCommentLike);

        JudgmentComment comment = judgmentCommentRepository.findById(judgmentCommentId)
                .orElseThrow(JudgmentCommentNotFoundException::getInstance);
        comment.incrementLikesCount();
        return java.util.Optional.of(JudgmentCommentLikeResponse.from(saved));
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
