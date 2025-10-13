package paradox.community.dto.postcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentCreateRequest {
    private String body;
    private Long parentCommentId; // null이면 일반 댓글
}
