package paradox.community.domain.judgment.dto.request;

import java.time.LocalDateTime;

public record JudgmentUpdateRequest(
        String title,
        Boolean isEnd,
        LocalDateTime startAt,
        LocalDateTime endAt
) {}
