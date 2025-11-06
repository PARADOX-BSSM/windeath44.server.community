package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostUpdateForbiddenException extends GlobalException {
    public PostUpdateForbiddenException() {
        super(ErrorCode.POST_UPDATE_FORBIDDEN);
    }

    private static class Holder {
        private static final PostUpdateForbiddenException INSTANCE = new PostUpdateForbiddenException();
    }

    public static PostUpdateForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
