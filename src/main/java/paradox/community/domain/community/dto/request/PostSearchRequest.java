package paradox.community.domain.community.dto.request;

import paradox.community.domain.community.model.PostStatus;

public record PostSearchRequest(
        String title,
        Boolean isBlind,
        Long characterId,
        PostStatus status,
        String mode
) {
        public PostSearchRequest {
                if (title != null && title.isBlank()) title = null;
        }
}
