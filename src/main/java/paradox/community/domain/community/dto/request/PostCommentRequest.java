package paradox.community.domain.community.dto.request;

import java.time.LocalDateTime;

public record PostCommentRequest(
    Long parentCommentId,
    String body
) {}
