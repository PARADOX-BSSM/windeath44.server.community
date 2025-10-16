package paradox.community.domain.community.dto.request;

import paradox.community.domain.community.dto.response.PostResponse;
import paradox.community.domain.community.model.Post;
import paradox.community.domain.community.model.PostStatus;

public record PostRequest(
        Long characterId,
        String title,
        String body,
        PostStatus status,
        Boolean isBlind
) {
    public static PostResponse from(Post post) {
        return new PostResponse(post.getPostId(), post.getUserId(), post.getCharacterId(), post.getTitle(), post.getBody(), post.getStatus(), post.isBlind(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
