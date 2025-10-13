package paradox.community.dto.judgment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paradox.community.enumclass.JudgmentStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentResponse {
    private Long judgmentId;
    private Long characterId;
    private String title;
    private JudgmentStatus instance;
    private Boolean isEnd;
    private String startAt;
    private String endAt;
    private Long heavenCount;
    private Long hellCount;
    private String createdAt;
    private String updatedAt;
}
