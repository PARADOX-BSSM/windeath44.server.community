package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.Judgment;
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
) {
    public static JudgmentCreateResponse from(Judgment judgment) {
        return new JudgmentCreateResponse(
                judgment.getJudgmentId(),
                judgment.getCharacterId(),
                judgment.getTitle(),
                judgment.getStatus(),
                judgment.getIsEnd(),
                judgment.getStartAt(),
                judgment.getEndAt(),
                judgment.getHeavenCount(),
                judgment.getHellCount(),
                judgment.getCreatedAt(),
                judgment.getUpdatedAt()
        );
    }
}
