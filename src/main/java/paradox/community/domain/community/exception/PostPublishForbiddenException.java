package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostPublishForbiddenException extends GlobalException {
    public PostPublishForbiddenException() {
        super(ErrorCode.POST_PUBLISH_FORBIDDEN);
    }

    private static class Holder {
        private static final PostPublishForbiddenException INSTANCE = new PostPublishForbiddenException();
    }

    public static PostPublishForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
