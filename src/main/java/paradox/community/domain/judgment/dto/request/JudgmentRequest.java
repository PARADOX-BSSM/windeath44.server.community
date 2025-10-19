package paradox.community.domain.judgment.dto.request;

import paradox.community.domain.judgment.model.JudgmentStatus;
import java.time.LocalDateTime;

public record JudgmentRequest(
        Long characterId,
        String title,
        JudgmentStatus instance,
        LocalDateTime startAt,
        LocalDateTime endAt
) {}
