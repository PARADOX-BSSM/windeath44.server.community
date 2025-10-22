package paradox.community.domain.judgment.dto.request;

import paradox.community.domain.judgment.model.JudgmentStatus;

public record JudgmentListRequest(
        Long characterId,
        Boolean isEnd,
        JudgmentStatus instance
) {}
