package paradox.community.domain.community.dto.request;

import paradox.community.domain.community.model.PostStatus;

public record PostSearchRequest(
        String title,
        Boolean isBlind,
        Long characterId,
        PostStatus status,
        String mode,
        Integer page,
        Integer size
) {
        public PostSearchRequest {
                if (title != null && title.isBlank()) title = null;
                if (page != null && page < 0) page = 0;
                if (size != null && size <= 0) size = null;
        }
}
