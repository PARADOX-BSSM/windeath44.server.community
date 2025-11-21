package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record JudgmentCommentResponse(
        Long commentId,
        String userId,
        Long judgmentId,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likesCount,
        Boolean isLiked,
        List<JudgmentCommentResponse> children
) {}
