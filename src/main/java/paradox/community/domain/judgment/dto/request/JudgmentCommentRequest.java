package paradox.community.domain.judgment.dto.request;

public record JudgmentCommentRequest(
        Long parentCommentId,
        String body
) {}
