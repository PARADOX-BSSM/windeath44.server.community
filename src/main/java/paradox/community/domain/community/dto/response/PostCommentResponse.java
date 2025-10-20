package paradox.community.domain.community.dto.response;

import java.time.LocalDateTime;

public record PostCommentResponse(
        Long commentId,
        String userId,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likeCount,
        Boolean isLiked
) {}
