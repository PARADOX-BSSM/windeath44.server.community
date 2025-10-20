package paradox.community.domain.judgment.dto.response;

public record VoteCreateResponse(
        Long voteId,
        String userId,
        Boolean isHeaven
) {}
