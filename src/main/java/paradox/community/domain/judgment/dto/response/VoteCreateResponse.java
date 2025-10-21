package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record VoteCreateResponse(
        Long voteId,
        String userId,
        Boolean isHeaven,
        LocalDateTime votedAt,
        Long heavenCount,
        Long hellCount
) {}
