package paradox.community.dto.judgmentcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentCommentListResponse {
    private List<JudgmentCommentResponse> comments;
}
