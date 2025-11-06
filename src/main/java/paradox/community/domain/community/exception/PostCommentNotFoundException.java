package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostCommentNotFoundException extends GlobalException {
    public PostCommentNotFoundException() {
        super(ErrorCode.POST_COMMENT_NOT_FOUND);
    }

    private static class Holder {
        private static final PostCommentNotFoundException INSTANCE = new PostCommentNotFoundException();
    }
    public static PostCommentNotFoundException getInstance() {
        return Holder.INSTANCE;
    }
}
