package paradox.community.domain.judgment.dto.response;

public record VoteResponse(
        Long voteId,
        String userId,
        Boolean isHeaven,
        LocalDateTime votedAt
) {}
