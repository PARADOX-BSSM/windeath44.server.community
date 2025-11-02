package paradox.community.domain.judgment.dto.request;

import paradox.community.domain.judgment.model.JudgmentInstance;

public record JudgmentListRequest(
        Long characterId,
        Boolean isEnd,
        JudgmentInstance instance
) {}
