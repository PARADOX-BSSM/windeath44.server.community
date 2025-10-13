package paradox.community.dto.judgmentcomment;

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
}
