package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.response.JudgmentLikeResponse;
import paradox.community.domain.judgment.exception.JudgmentNotFoundException;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentLike;
import paradox.community.domain.judgment.repository.JudgmentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JudgmentLikeService {

    private final JudgmentLikeRepository judgmentLikeRepository;
    private final JudgmentRepository judgmentRepository;

    @Transactional
    public JudgmentLikeResponse addJudgmentLike(String userId, Long judgmentId) {
        return judgmentLikeRepository.findByUserIdAndJudgmentId(userId, judgmentId)
                .map(JudgmentLikeResponse::from)
                .orElseGet(() -> {
                    JudgmentLike judgmentLike = JudgmentLike.builder()
                            .judgmentId(judgmentId)
                            .userId(userId)
                            .build();
                    JudgmentLike saved = judgmentLikeRepository.save(judgmentLike);

                    Judgment judgment = judgmentRepository.findById(judgmentId)
                            .orElseThrow(JudgmentNotFoundException::getInstance);
                    judgment.incrementLikesCount();

                    return JudgmentLikeResponse.from(saved);
                });
    }

    @Transactional
    public void removeJudgmentLike(String userId, Long judgmentId) {
        judgmentLikeRepository.deleteByUserIdAndJudgmentId(userId, judgmentId);
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(JudgmentNotFoundException::getInstance);
        judgment.decrementLikesCount();
    }

    public Boolean isLiked(String userId, Long judgmentId) {
        return judgmentLikeRepository.existsByUserIdAndJudgmentId(userId, judgmentId);
    }
}
