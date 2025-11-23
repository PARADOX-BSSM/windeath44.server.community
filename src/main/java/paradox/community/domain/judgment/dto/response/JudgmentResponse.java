package paradox.community.domain.judgment.dto.response;

import paradox.community.domain.judgment.model.JudgmentStatus;
import paradox.community.domain.judgment.model.Judgment;
import paradox.community.domain.judgment.model.JudgmentInstance;

import java.time.LocalDateTime;

public record JudgmentResponse(
        Long judgmentId,
        String title,
        Long animeId,
        String characterName,
        String imageUrl,
        JudgmentInstance instance,
        JudgmentStatus status,
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
                judgment.getAnimeId(),
                judgment.getCharacterName(),
                judgment.getImageUrl(),
                judgment.getInstance(),
                judgment.getStatus(),
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
