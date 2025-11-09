package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostComment2DepthOverForbiddenException extends GlobalException {
    public PostComment2DepthOverForbiddenException() {
        super(ErrorCode.POST_COMMENT_2DEPTH_OVER_FORBIDDEN);
    }

    private static class Holder {
        private static final PostComment2DepthOverForbiddenException INSTANCE = new PostComment2DepthOverForbiddenException();
    }

    public static PostComment2DepthOverForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
