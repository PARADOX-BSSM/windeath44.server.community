package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record VoteHistoryResponse(
        Long voteId,
        Long judgmentId,
        Boolean isHeaven,
        LocalDateTime votedAt
) {}
