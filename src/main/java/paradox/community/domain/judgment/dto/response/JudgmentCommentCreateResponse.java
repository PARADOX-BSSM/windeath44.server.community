package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record JudgmentCommentCreateResponse(
        Long commentId,
        String userId,
        Long judgmentId,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likeCount,
        Boolean isLiked
) {}
