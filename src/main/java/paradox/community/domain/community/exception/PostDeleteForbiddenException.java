package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostDeleteForbiddenException extends GlobalException {
    public PostDeleteForbiddenException() {
        super(ErrorCode.POST_DELETE_FORBIDDEN);
    }

    private static class Holder {
        private static final PostDeleteForbiddenException INSTANCE = new PostDeleteForbiddenException();
    }

    public static PostDeleteForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
