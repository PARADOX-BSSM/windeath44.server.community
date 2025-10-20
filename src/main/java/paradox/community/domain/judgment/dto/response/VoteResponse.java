package paradox.community.domain.judgment.dto.response;

import java.time.LocalDateTime;

public record VoteResponse(
        Long judgmentId,
        Long heavenCount,
        Long hellCount,
        Long totalVotes,
        Double heavenRatio,
        Double hellRatio
) {}
