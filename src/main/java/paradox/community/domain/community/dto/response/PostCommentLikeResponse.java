package paradox.community.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentLikeResponse {
    private Long likeId;
    private Long commentId;
    private Long postId;
    private String userId;
}
