package paradox.community.domain.judgment.dto.response;

public record JudgmentLikeResponse(
        Long likeId,
        Long judgmentId,
        String userId
) {}
