package paradox.community.dto.postcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentResponse {
    private Long commentId;
    private String userId;
    private Long parentCommentId;
    private String body;
    private String createdAt;
}
