package paradox.community.domain.community.dto.response;

import paradox.community.domain.community.model.PostLike;

public record PostLikeResponse(
        Long postId,
        String userId
) {
    public static PostLikeResponse from(PostLike postLike) {
        return new PostLikeResponse(
                postLike.getPostId(),
                postLike.getUserId()
        );
    }
}
