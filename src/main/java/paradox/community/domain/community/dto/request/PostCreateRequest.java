package paradox.community.domain.community.dto.request;

import paradox.community.domain.community.model.PostStatus;

public record PostCreateRequest(
        Long characterId,
        String title,
        String body,
        PostStatus status,
        Boolean isBlind
) {}
