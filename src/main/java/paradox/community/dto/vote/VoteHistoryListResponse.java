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
public class VoteHistoryListResponse {
    private String userId;
    private Long totalCount;
    private List<VoteHistoryResponse> votes;
}
