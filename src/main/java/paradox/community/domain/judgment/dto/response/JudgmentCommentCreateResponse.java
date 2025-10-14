package paradox.community.domain.judgment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentCommentCreateResponse extends JudgmentCommentResponse {
    private Long judgmentId;

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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JudgmentLikeResponse {
        private Long likeId;
        private Long judgmentId;
        private String userId;
    }
}
