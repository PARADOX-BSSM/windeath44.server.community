package paradox.community.domain.judgment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paradox.community.domain.judgment.dto.response.JudgmentCommentLikeResponse;
import paradox.community.domain.judgment.model.JudgmentComment;
import paradox.community.domain.judgment.model.JudgmentCommentLike;
import paradox.community.domain.judgment.repository.JudgmentCommentLikeRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JudgmentCommentLikeService {

    private final JudgmentCommentLikeRepository judgmentCommentLikeRepository;

    @Transactional
    public JudgmentCommentLikeResponse addJudgmentCommentLike(String userId, Long judgmentCommentId) {
        if (judgmentCommentLikeRepository.existsByUserIdAndCommentId(userId, judgmentCommentId)) {
            return null;
        }else {
            JudgmentCommentLike judgmentCommentLike = JudgmentCommentLike.builder()
                    .userId(userId)
                    .judgmentCommentId(judgmentCommentId).build();

            JudgmentCommentLike saved = judgmentCommentLikeRepository.save(judgmentCommentLike);
            return JudgmentCommentLikeResponse.from(saved);
        }
    }

    @Transactional
    public void removeJudgmentCommentLike(String userId, Long judgmentCommentId) {
        if (judgmentCommentLikeRepository.existsByUserIdAndCommentId(userId, judgmentCommentId)) {
            judgmentCommentLikeRepository.deleteById(judgmentCommentId);
        }
    }

    public Boolean isLiked(Long commentId, String userId) {
        return judgmentCommentLikeRepository.findByUserIdAndCommentId(userId, commentId).isPresent();
    }
}
