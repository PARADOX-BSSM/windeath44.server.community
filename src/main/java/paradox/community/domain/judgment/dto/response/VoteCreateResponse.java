package paradox.community.domain.judgment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreateResponse {
    private Long voteId;
    private Long judgmentId;
    private Long userId;
    private Boolean isHeaven;
    private LocalDateTime voteAt;
    private Long heavenCount;
    private Long hellCount;
}
