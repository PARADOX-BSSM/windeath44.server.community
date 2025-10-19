package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.JudgmentStatus;
import java.time.LocalDateTime;

public record JudgmentCreateResponse(
        Long judgmentId,
        Long characterId,
        String title,
        JudgmentStatus instance,
        Boolean isEnd,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Long heavenCount,
        Long hellCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
