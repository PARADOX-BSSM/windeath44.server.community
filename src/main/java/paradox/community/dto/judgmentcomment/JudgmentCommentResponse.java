package paradox.community.dto.judgmentcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paradox.community.enumclass.JudgmentStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentCommentResponse {
    private Long commentId;
    private String userId;
    private Long parentCommentId;
    private String body;
    private String createdAt;
    private String updatedAt;
    private Long likeCount;
    private Boolean isLiked;
}
