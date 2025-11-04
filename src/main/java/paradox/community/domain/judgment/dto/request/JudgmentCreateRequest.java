package paradox.community.domain.judgment.dto.request;

import paradox.community.domain.judgment.model.JudgmentInstance;

import java.time.LocalDateTime;

public record JudgmentCreateRequest(
        Long characterId,
        String title,
        JudgmentInstance instance,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}