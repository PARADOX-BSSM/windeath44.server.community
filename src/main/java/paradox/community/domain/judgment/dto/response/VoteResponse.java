package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record VoteResponse(
        Long voteId,
        String userId,
        Boolean isHeaven,
        LocalDateTime votedAt
) {}
