package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.JudgmentLike;

public record JudgmentLikeResponse(
        Long likeId,
        Long judgmentId
) {
    public static JudgmentLikeResponse from(JudgmentLike judgmentLike) {
        return new JudgmentLikeResponse(judgmentLike.getLikeId(), judgmentLike.getJudgmentId());
    }
}
