package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentStatus;

import java.time.LocalDateTime;

public record JudgmentResponse(
        Long judgmentId,
        String title,
        Long characterId,
        String characterName,
        Long animeId,
        String animeName,
        JudgmentStatus instance,
        Boolean isEnd,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Long heavenCount,
        Long hellCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likesCount
) {
    public static JudgmentResponse from(Judgment judgment) {
        return new JudgmentResponse(
                judgment.getJudgmentId(),
                judgment.getTitle(),
                judgment.getCharacterId(),
                judgment.getCharacterName(),
                judgment.getAnimeId(),
                judgment.getAnimeName(),
                judgment.getInstance(),
                judgment.getIsEnd(),
                judgment.getStartAt(),
                judgment.getEndAt(),
                judgment.getHeavenCount(),
                judgment.getHellCount(),
                judgment.getCreatedAt(),
                judgment.getUpdatedAt(),
                judgment.getLikesCount()
                );
    }
}
