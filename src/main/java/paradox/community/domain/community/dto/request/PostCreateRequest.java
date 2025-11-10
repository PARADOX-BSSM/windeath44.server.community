package paradox.community.domain.community.dto.request;

import paradox.community.domain.community.model.PostStatus;

public record PostCreateRequest(
        Long characterId,
        String title,
        String body,
        PostStatus status,
        Boolean isBlind
) {
        public PostCreateRequest {
                if (title == null || title.isBlank()) throw new IllegalArgumentException("title cannot be null or blank");
                if (status == null) throw new IllegalArgumentException("status cannot be null");
                if (isBlind == null) isBlind = Boolean.FALSE;
        }
}
