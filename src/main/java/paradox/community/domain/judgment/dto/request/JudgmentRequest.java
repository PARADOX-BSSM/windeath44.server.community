package paradox.community.domain.judgment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paradox.community.domain.judgment.enumclass.JudgmentStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentRequest {
    private Long characterId;
    private String title;
    private JudgmentStatus instance;
    private String startAt;
    private String endAt;
}
