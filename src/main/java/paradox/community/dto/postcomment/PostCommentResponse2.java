package paradox.community.dto.postcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentResponse2 {
    private Long commendId;
    private Long postId;
    private String userId;
    private Long parentCommentId;
    private String body;
    private String createdAt;
    private String updatedAt;
}
