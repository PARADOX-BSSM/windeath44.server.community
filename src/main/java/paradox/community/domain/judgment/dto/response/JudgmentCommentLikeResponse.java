package paradox.community.domain.judgment.dto.response;

public record JudgmentCommentLikeResponse(
        Long likeId,
        Long commentId,
        Long judgmentId,
        String userId,
        Long likeCount,
        Boolean isLiked
) {}
