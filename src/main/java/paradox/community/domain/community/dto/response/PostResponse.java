package paradox.community.domain.community.dto.response;
import paradox.community.domain.community.model.Post;

import paradox.community.domain.community.model.PostStatus;

import java.time.LocalDateTime;

public record PostResponse (
    Long postId,
    String userId,
    Long characterId,
    String title,
    String body,
    PostStatus status,
    Boolean isBlind,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long viewsCount,
    Long likesCount
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getUserId(),
                post.getCharacterId(),
                post.getTitle(),
                post.getBody(),
                post.getStatus(),
                post.getIsBlind(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViewsCount(),
                post.getLikesCount()
        );
    }
}
