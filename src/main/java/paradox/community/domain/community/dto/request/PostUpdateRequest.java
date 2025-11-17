package paradox.community.domain.community.dto.request;

import paradox.community.domain.community.model.PostStatus;

public record PostUpdateRequest(
        String title,
        String body,
        PostStatus status,
        Boolean isBlind
){
        public PostUpdateRequest {
                if (title != null && title.isBlank()) throw new IllegalArgumentException("title cannot be blank");
        }
}
