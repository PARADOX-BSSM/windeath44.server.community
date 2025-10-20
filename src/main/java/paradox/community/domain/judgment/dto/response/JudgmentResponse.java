package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentStatus;

import java.time.LocalDateTime;

public record JudgmentResponse(
        Long judgmentId,
        String title,
        Long characterId,
        JudgmentStatus instance,
        Boolean isEnd,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Long heavenCount,
        Long hellCount,
        Long likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static JudgmentResponse from(Judgment judgment) {
        return new JudgmentResponse(
                judgment.getJudgmentId(),
                judgment.getTitle(),
                judgment.getCharacterId(),
                judgment.getStatus(),
                judgment.getIsEnd(),
                judgment.getStartAt(),
                judgment.getEndAt(),
                judgment.getHeavenCount(),
                judgment.getHellCount(),
                judgment.getLikesCount(),
                judgment.getCreatedAt(),
                judgment.getUpdatedAt()
                );
    }
}
