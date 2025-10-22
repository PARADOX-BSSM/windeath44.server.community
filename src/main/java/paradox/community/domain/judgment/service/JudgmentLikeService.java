package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;
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

        return judgmentLikeRepository.existsByUserIdAndJudgmentId(userId, judgmentId);
    }
}
