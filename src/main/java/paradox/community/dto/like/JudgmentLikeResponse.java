package paradox.community.dto.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentLikeResponse {
    private Long likeId;
    private Long judgmentId;
    private String userId;
}
