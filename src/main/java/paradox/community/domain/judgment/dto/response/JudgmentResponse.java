package paradox.community.domain.judgment.dto.response;

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
        Boolean isLiked,
        Boolean isHeaven,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
