package paradox.community.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentCreateResponse {
    private Long commendId;
    private Long postId;
    private String userId;
    private Long parentCommentId;
    private String body;
    private String createdAt;
    private String updatedAt;
}
