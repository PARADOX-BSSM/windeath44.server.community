package paradox.community.domain.community.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import paradox.community.domain.community.model.PostComment;

public record PostCommentResponse(
        Long commentId,
        Long postId,
        String userId,
        Long parentCommentId,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long likesCount,
        List<PostCommentResponse> children
)
{
        public static PostCommentResponse from(PostComment comment, Long likesCount) {
                return new PostCommentResponse(
                                comment.getCommentId(),
                                comment.getPostId(),
                                comment.getUserId(),
                                comment.getParentCommentId(),
                                comment.getBody(),
                                comment.getCreatedAt(),
                                comment.getUpdatedAt(),
                                likesCount == null ? 0L : likesCount,
                                List.of()
                );
        }

}
