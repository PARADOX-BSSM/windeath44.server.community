package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record JudgmentCommentResponse(
        Long commentId,
        String userId,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likeCount,
        Boolean isLiked
) {}
