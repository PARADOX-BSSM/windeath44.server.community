package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record JudgmentCommentResponse(
        Long commentId,
        String userId,
        String name,
        String profile,
        Long judgmentId,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likesCount,
        Boolean isLiked
) {}
