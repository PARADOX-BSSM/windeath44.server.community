package paradox.community.domain.community.dto.response;

import paradox.community.domain.community.model.PostStatus;

import java.time.LocalDateTime;

public record PostResponse(
        Long postId,
        String userId,
        Long characterId,
        String title,
        String body,
        PostStatus status,
        Boolean isBlind,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long viewCount,
        Long likeCount,
        Boolean isLiked
) {}
