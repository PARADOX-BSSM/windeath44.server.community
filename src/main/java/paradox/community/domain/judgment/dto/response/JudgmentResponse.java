package paradox.community.domain.judgment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentResponse {
    private Long judgmentId;
    private String title;
    private Long characterId;
    private String createdAt;
    private String updatedAt;
}
