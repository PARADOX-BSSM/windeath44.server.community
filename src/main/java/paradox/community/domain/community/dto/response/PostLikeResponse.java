package paradox.community.domain.community.dto.response;

public record PostLikeResponse(
        Long likeId,
        Long postId,
        String userId,
        Long likeCount,
        Boolean isLiked
) {}
