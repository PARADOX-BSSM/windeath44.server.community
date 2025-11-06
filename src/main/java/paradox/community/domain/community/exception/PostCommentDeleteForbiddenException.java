package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostCommentDeleteForbiddenException extends GlobalException {
    public PostCommentDeleteForbiddenException() {
        super(ErrorCode.POST_COMMENT_DELETE_FORBIDDEN);
    }

    private static class Holder {
        private static final PostCommentDeleteForbiddenException INSTANCE = new PostCommentDeleteForbiddenException();
    }
    public static PostCommentDeleteForbiddenException getInstance() {
        return Holder.INSTANCE;
    }
}
