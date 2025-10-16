package paradox.community.domain.community.dto.request;

import java.time.LocalDateTime;

public record PostCommentRequest(
    Long commentId,
    String userId,
    Long parentCommentId,
    String body,
    LocalDateTime createdAt
) {}
