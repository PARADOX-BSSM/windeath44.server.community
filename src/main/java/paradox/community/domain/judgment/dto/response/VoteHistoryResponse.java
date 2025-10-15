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
public class VoteHistoryResponse {
    private Long voteId;
    private Long judgmentId;
    private Boolean isHeaven;
    private LocalDateTime votedAt;
}
