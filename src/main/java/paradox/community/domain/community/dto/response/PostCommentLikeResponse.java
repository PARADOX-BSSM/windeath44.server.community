package paradox.community.domain.community.dto.response;

import paradox.community.domain.community.model.PostCommentLike;

public record PostCommentLikeResponse(
        Long likeId,
        Long commentId,
        String userId
) {
    public static PostCommentLikeResponse from(PostCommentLike postCommentLike) {
        return new PostCommentLikeResponse(postCommentLike.getLikeId(), postCommentLike.getPostCommentId(), postCommentLike.getUserId());
    }
}
