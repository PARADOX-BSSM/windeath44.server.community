package paradox.community.domain.judgment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record _() {
}
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public static class JudgmentCommentLikeResponse {
    private Long likeId;
    private Long commentId;
    private Long judgmentId;
    private String userId;
}