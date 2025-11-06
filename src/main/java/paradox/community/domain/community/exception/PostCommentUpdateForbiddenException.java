package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostCommentUpdateForbiddenException extends GlobalException {
    public PostCommentUpdateForbiddenException() {
        super(ErrorCode.POST_COMMENT_UPDATE_FORBIDDEN);
    }

    private static class Holder {
        private static final PostCommentUpdateForbiddenException INSTANCE = new PostCommentUpdateForbiddenException();
    }
    public static PostCommentUpdateForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
