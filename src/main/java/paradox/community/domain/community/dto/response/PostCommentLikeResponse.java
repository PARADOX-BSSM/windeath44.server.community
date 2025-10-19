package paradox.community.domain.community.dto.response;

public record PostCommentLikeResponse(
        Long likeId,
        Long commentId,
        Long postId,
        String userId,
        Long likeCount,
        Boolean isLiked
) {}
