package paradox.community.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteListResponse {
    private Long judgmentId;
    private Long totalCount;
    private Long heavenCount;
    private Long hellCount;
    private List<VoteResponse> votes;
}
