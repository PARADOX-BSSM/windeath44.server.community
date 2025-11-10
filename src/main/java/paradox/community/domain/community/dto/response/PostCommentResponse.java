package paradox.community.domain.community.dto.response;

import java.time.LocalDateTime;
import paradox.community.domain.community.model.PostComment;

public record PostCommentResponse(
        Long commentId,
        Long postId,
        String userId,
        String name,
        String profile,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likesCount
)
{
        public static PostCommentResponse from(PostComment comment, Long likesCount) {
                return new PostCommentResponse(
                                comment.getCommentId(),
                                comment.getPostId(),
                                comment.getUserId(),
                                comment.getUserName(),
                                comment.getProfile(),
                                comment.getParentCommentId(),
                                comment.getBody(),
                                comment.getCreatedAt(),
                                comment.getUpdatedAt(),
                                likesCount == null ? 0L : likesCount
                );
        }

}
