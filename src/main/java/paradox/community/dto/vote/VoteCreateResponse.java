package paradox.community.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreateResponse {
    private Long voteId;
    private Long judgmentId;
    private Long userId;
    private Boolean isHeaven;
    private String voteAt;
    private Long heavenCount;
    private Long hellCount;
}
