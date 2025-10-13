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
public class JudgmentRequest2 {
    private String title;
    private Boolean isEnd;
    private String startAt;
    private String endAt;
}
