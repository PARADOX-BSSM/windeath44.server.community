package paradox.community.domain.community.dto.response;

import java.time.LocalDateTime;

public record PostCommentResponse(
        Long commentId,
        Long postId,
        String userId,
        String name,
        String profile,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likesCount
) {}
