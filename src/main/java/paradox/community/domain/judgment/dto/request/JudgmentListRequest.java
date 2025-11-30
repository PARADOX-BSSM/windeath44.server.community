package paradox.community.domain.judgment.dto.request;

import paradox.community.domain.judgment.model.JudgmentInstance;
import paradox.community.domain.judgment.model.JudgmentStatus;

public record JudgmentListRequest(
        Long characterId,
        JudgmentStatus status,
        JudgmentInstance instance,
        Integer page,
        Integer size
) {
    public JudgmentListRequest {
        if (page != null && page < 0) page = 0;
        if (size != null && size <= 0) size = null;
    }
}
