package paradox.community.domain.judgment.dto.request;

import paradox.community.domain.judgment.model.JudgmentInstance;
import paradox.community.domain.judgment.model.JudgmentStatus;

public record JudgmentListRequest(
        JudgmentStatus status,
        JudgmentInstance instance
) {}
