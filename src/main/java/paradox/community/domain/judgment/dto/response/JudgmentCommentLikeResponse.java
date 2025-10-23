package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.JudgmentCommentLike;

public record JudgmentCommentLikeResponse(
        Long likeId,
        Long judgmentCommentId
) {
    public static JudgmentCommentLikeResponse from(JudgmentCommentLike judgmentCommentLike) {
        return new JudgmentCommentLikeResponse(judgmentCommentLike.getLikeId(), judgmentCommentLike.getJudgmentCommentId());
    }
}
