package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.community.dto.response.PostLikeResponse;
import paradox.community.domain.judgment.dto.response.JudgmentLikeResponse;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentLike;
import paradox.community.domain.judgment.repository.JudgmentLikeRepository;
import paradox.community.domain.judgment.repository.JudgmentRepository;
import paradox.community.global.dto.ApiResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JudgmentLikeService {

    private final JudgmentLikeRepository judgmentLikeRepository;

    @Transactional
    public JudgmentLikeResponse addJudgmentLike(String userId, Long judgmentId) {
        if (judgmentLikeRepository.existsByUserIdAndJudgmentId(userId, judgmentId)) {
            return null;
        }else {
            JudgmentLike judgmentLike = JudgmentLike.builder()
                    .judgmentId(judgmentId)
                    .userId(userId)
                    .build();

            JudgmentLike saved = judgmentLikeRepository.save(judgmentLike);
            return JudgmentLikeResponse.from(saved);
        }
    }

    @Transactional
    public void removeJudgmentLike(String userId, Long judgmentId) {
        if (judgmentLikeRepository.existsByUserIdAndJudgmentId(userId, judgmentId)) {
            judgmentLikeRepository.deleteById(judgmentId);
        }
    }

    public Boolean isLiked(String userId, Long judgmentId) {
        return judgmentLikeRepository.existsByUserIdAndJudgmentId(userId, judgmentId);
    }
}
