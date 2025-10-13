package paradox.community.dto.judgment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgmentUpdateRequest {
    private String title;
    private Boolean isEnd;
    private String startAt;
    private String endAt;
}
