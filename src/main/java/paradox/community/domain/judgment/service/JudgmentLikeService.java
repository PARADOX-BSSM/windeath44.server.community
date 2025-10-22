package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Boolean toggleLike(String userId, Long judgmentId) {
        Judgment judgment = judgmentRepository.findById(judgmentId)
                .orElseThrow(() -> new IllegalArgumentException("Judgment with id: " + judgmentId + " not found"));

        return judgmentLikeRepository.findByUserIdAndJudgmentId(userId, judgmentId)
                .map(like -> {
                    judgmentLikeRepository.delete(like);
                    judgment.decrementLikesCount();
                    return false;
                })
                .orElseGet(() -> {
                    JudgmentLike newLike = JudgmentLike.builder()
                            .userId(userId)
                            .judgmentId(judgmentId)
                            .build();
                    judgmentLikeRepository.save(newLike);
                    judgment.incrementLikesCount();
                    return true;
                });

    }

    public Boolean isLiked(String userId, Long judgmentId) {
        return judgmentLikeRepository.existsByUserIdAndJudgmentId(userId, judgmentId);
    }
}
