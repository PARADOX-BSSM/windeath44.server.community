package paradox.community.domain.community.exception;

import paradox.community.global.error.exception.ErrorCode;
import paradox.community.global.error.exception.GlobalException;

public class PostCommentParentNotFoundException extends GlobalException {
    public PostCommentParentNotFoundException() {
        super(ErrorCode.POST_COMMENT_PARENT_NOT_FOUND);
    }

    private static class Holder {
        private static final PostCommentParentNotFoundException INSTANCE = new PostCommentParentNotFoundException();
    }

    public static PostCommentParentNotFoundException getInstance() {
        return Holder.INSTANCE;
    }
}
