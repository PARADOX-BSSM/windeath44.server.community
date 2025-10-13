package paradox.community.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    private Long voteId;
    private String userId;
    private Boolean isHeaven;
    private String votedAt;
}
