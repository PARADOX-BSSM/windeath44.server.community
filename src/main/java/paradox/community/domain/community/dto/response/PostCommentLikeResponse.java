package paradox.community.domain.community.dto.response;

public record PostCommentLikeResponse(
        Long commentId,
        Long postId,
        String userId
) {}
