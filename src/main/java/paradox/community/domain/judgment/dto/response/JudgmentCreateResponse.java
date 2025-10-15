package paradox.community.domain.judgment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paradox.community.domain.judgment.model.JudgmentStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentCreateResponse {
    private Long judgmentId;
    private Long characterId;
    private String title;
    private JudgmentStatus instance;
    private Boolean isEnd;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long heavenCount;
    private Long hellCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
