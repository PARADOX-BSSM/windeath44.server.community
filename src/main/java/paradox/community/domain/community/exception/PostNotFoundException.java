package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostNotFoundException extends GlobalException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }

    private static class Holder {
        private static final PostNotFoundException INSTANCE = new PostNotFoundException();
    }

    public static PostNotFoundException getInstance() { return Holder.INSTANCE; }
}
