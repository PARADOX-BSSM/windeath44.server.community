package paradox.community.domain.judgment.dto.response;

public record VoteResponse(
        Long judgmentId,
        Long heavenCount,
        Long hellCount,
        Long totalVotes,
        Double heavenRatio,
        Double hellRatio
) {}
