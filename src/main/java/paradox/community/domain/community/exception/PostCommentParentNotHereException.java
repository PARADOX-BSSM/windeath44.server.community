package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostCommentParentNotHereException extends GlobalException {
    public PostCommentParentNotHereException() {
        super(ErrorCode.POST_COMMENT_PARENT_NOT_HERE);
    }

    private static class Holder {
        private static final PostCommentParentNotHereException INSTANCE = new PostCommentParentNotHereException();
    }
    public static PostCommentParentNotHereException getInstance() {
        return Holder.INSTANCE;
    }
}
