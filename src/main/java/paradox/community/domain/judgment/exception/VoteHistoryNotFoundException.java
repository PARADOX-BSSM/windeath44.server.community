package paradox.community.domain.judgment.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class VoteHistoryNotFoundException extends GlobalException {
    private VoteHistoryNotFoundException() {
        super(ErrorCode.VOTE_HISTORY_NOT_FOUND);
    }

    private static class Holder {
        private static final VoteHistoryNotFoundException VOTE_HISTORY_NOT_FOUND = new VoteHistoryNotFoundException();
    }

    public static VoteHistoryNotFoundException getInstance() {
        return Holder.VOTE_HISTORY_NOT_FOUND;
    }
}
